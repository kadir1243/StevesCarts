package vswe.stevescarts.screen;

import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.networking.NetworkSide;
import io.github.cottonmc.cotton.gui.networking.ScreenNetworking;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import io.github.cottonmc.cotton.gui.widget.WPlayerInvPanel;
import io.github.cottonmc.cotton.gui.widget.WWidget;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.apache.commons.lang3.tuple.Pair;
import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.block.StevesCartsBlocks;
import vswe.stevescarts.block.entity.CartAssemblerBlockEntity;
import vswe.stevescarts.item.ModularMinecartItem;
import vswe.stevescarts.item.StevesCartsItems;
import vswe.stevescarts.item.modules.ModuleItem;
import vswe.stevescarts.modules.MinecartModule;
import vswe.stevescarts.modules.MinecartModuleType;
import vswe.stevescarts.modules.ModuleCategory;
import vswe.stevescarts.modules.ModuleSide;
import vswe.stevescarts.modules.hull.HullModule;
import vswe.stevescarts.screen.widget.WAssembleButton;
import vswe.stevescarts.screen.widget.WCart;
import vswe.stevescarts.screen.widget.WFixedPanel;
import vswe.stevescarts.screen.widget.WInformation;
import vswe.stevescarts.screen.widget.WModuleSlot;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CartAssemblerHandler extends SyncedGuiDescription {
	private final ScreenHandlerContext context;
	private final WItemSlot outputSlot;

	public CartAssemblerHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
		super(StevesCartsScreenHandlers.CART_ASSEMBLER, syncId, playerInventory, getBlockInventory(context, CartAssemblerBlockEntity.SIZE), getBlockPropertyDelegate(context));
		playerInventory.onOpen(playerInventory.player);
		this.context = context;
		WFixedPanel rootPanel = new WFixedPanel();
		this.setRootPanel(rootPanel);
		rootPanel.setSize(436, 220);
		rootPanel.setInsets(Insets.ROOT_PANEL);
		WPlayerInvPanel playerInventoryPanel = this.createPlayerInventoryPanel(false);
		this.addCentered(playerInventoryPanel, rootPanel.getHeight() - playerInventoryPanel.getHeight());
		WAssembleButton assembleButton = new WAssembleButton(new TranslatableText("screen.stevescarts.cart_assembler.assemble"));
		rootPanel.add(assembleButton, 328, 167, 79, 9);
		WItemSlot hullSlot = WItemSlot.outputOf(this.blockInventory, CartAssemblerBlockEntity.HULL_SLOT);
		hullSlot.setFilter(MinecartModuleType::isHull);
		rootPanel.add(hullSlot, 12, 18);
		WModuleSlot engineSlots = new WModuleSlot(this.blockInventory, CartAssemblerBlockEntity.ENGINE_SLOT_START, 5, 1, ModuleCategory.ENGINE);
		engineSlots.setFilter(MinecartModuleType::isEngine);
		rootPanel.add(engineSlots, 7, 59);
		WModuleSlot toolSlot = new WModuleSlot(this.blockInventory, CartAssemblerBlockEntity.TOOL_SLOT, 1, 1, ModuleCategory.TOOL);
		rootPanel.add(toolSlot, 7, 89);
		toolSlot.setFilter(MinecartModuleType::isTool);
		WModuleSlot attachmentSlots = new WModuleSlot(this.blockInventory, CartAssemblerBlockEntity.ATTACHMENT_SLOT_START, 6, 1, ModuleCategory.ATTACHMENT);
		rootPanel.add(attachmentSlots, 7, 119);
		attachmentSlots.setFilter(MinecartModuleType::isAttachment);
		WModuleSlot storageSlots = new WModuleSlot(this.blockInventory, CartAssemblerBlockEntity.STORAGE_SLOT_START, 4, 1, ModuleCategory.STORAGE);
		rootPanel.add(storageSlots, 7, 149);
		storageSlots.setFilter(MinecartModuleType::isStorage);
		WModuleSlot addonsSlots = new WModuleSlot(this.blockInventory, CartAssemblerBlockEntity.ADDON_SLOT_START, 6, 2, ModuleCategory.ADDON);
		rootPanel.add(addonsSlots, 7, 179);
		addonsSlots.setFilter(MinecartModuleType::isAddon);
		this.outputSlot = WItemSlot.outputOf(this.blockInventory, CartAssemblerBlockEntity.OUTPUT_SLOT);
		this.outputSlot.setFilter((stack) -> stack.isOf(StevesCartsItems.MODULAR_CART));
		WCart cart = new WCart(() -> {
			List<MinecartModuleType<?>> types = new ArrayList<>();
			if (assembleButton.isEnabled()) {
				for (int i = 0; i <= CartAssemblerBlockEntity.ADDON_SLOT_END; i++) {
					ItemStack stack = this.blockInventory.getStack(i);
					if (!stack.isEmpty() && MinecartModuleType.isModule(stack)) {
						types.add(((ModuleItem) stack.getItem()).getType());
					}
				}
			}
			return types;
		}, 187, 120);
		this.addCentered(cart, 4);
		rootPanel.add(this.outputSlot, 330, 182);
		WItemSlot fuelSlot = WItemSlot.outputOf(this.blockInventory, CartAssemblerBlockEntity.FUEL_SLOT);
		rootPanel.add(fuelSlot, 384, 182);
		assembleButton.setOnClick(() -> ScreenNetworking.of(this, NetworkSide.CLIENT).send(StevesCartsScreenHandlers.PACKET_ASSEMBLE_CLICK, (buf) -> {}));
		hullSlot.addChangeListener(((slot, inventory, index, stack) -> {
			assembleButton.setEnabled(MinecartModuleType.isHull(stack));
			MinecartModuleType<? extends HullModule> e = MinecartModuleType.isHull(stack) ? (MinecartModuleType<? extends HullModule>) ((ModuleItem) stack.getItem()).getType() : null;
			addonsSlots.toggleSlots(e);
			storageSlots.toggleSlots(e);
			attachmentSlots.toggleSlots(e);
			toolSlot.toggleSlots(e);
			engineSlots.toggleSlots(e);
		}));
		WItemSlot.ChangeListener moduleListener = ((slot, inventory, index, stack) -> {
			hullSlot.setModifiable(!((WModuleSlot) slot).hasModule());
		});
		WInformation info = new WInformation();
		rootPanel.add(info, 315, 17);
		addonsSlots.addChangeListener(moduleListener);
		storageSlots.addChangeListener(moduleListener);
		attachmentSlots.addChangeListener(moduleListener);
		toolSlot.addChangeListener(moduleListener);
		engineSlots.addChangeListener(moduleListener);
		WItemSlot.ChangeListener cartListener = ((slot, inventory, index, stack) -> {
			cart.markDirty();
		});
		hullSlot.addChangeListener(cartListener);
		engineSlots.addChangeListener(cartListener);
		toolSlot.addChangeListener(cartListener);
		attachmentSlots.addChangeListener(cartListener);
		storageSlots.addChangeListener(cartListener);
		addonsSlots.addChangeListener(cartListener);
		this.outputSlot.addChangeListener(cartListener);
		WItemSlot.ChangeListener validator = ((slot, inventory, index, stack) -> {
			if (this.getNetworkSide() == NetworkSide.SERVER) { // Only validate on the client. The server doesn't care about the validity of the cart :p
				return;
			}
			List<MinecartModuleType<?>> types = new ArrayList<>();
			for (int i = 0; i <= CartAssemblerBlockEntity.ADDON_SLOT_END; i++) {
				ItemStack stack2 = this.blockInventory.getStack(i);
				if (!stack2.isEmpty() && MinecartModuleType.isModule(stack2)) {
					types.add(((ModuleItem) stack2.getItem()).getType());
				}
			}
			boolean invalid = false;
			if (!types.isEmpty()) {
				// Disallow duplicates if they should be disallowed
				{
					List<MinecartModuleType<?>> duplicates = new ArrayList<>(types);
					duplicates.removeAll(new HashSet<>(types));
					if (!duplicates.isEmpty()) {
						for (MinecartModuleType<?> type : duplicates) {
							if (type.allowsDuplicates()) {
								continue;
							}
							invalid = true;
							info.setText(new TranslatableText("screen.stevescarts.cart_assembler.duplicate_module", type.getTranslationText()));
							break;
						}
					}
				}

				// Disallow modules that share a side
				if (!invalid) {
					EnumMap<ModuleSide, List<MinecartModuleType<?>>> sideMap = new EnumMap<>(ModuleSide.class);
					for (MinecartModuleType<?> type : types) {
						for (ModuleSide side : type.getSides()) {
							sideMap.computeIfAbsent(side, (s) -> new ArrayList<>()).add(type);
						}
					}
					for (Map.Entry<ModuleSide, List<MinecartModuleType<?>>> entry : sideMap.entrySet()) {
						if (!entry.getKey().occupiesSide()) {
							continue;
						}
						if (entry.getValue().size() > 1) {
							invalid = true;
							info.setText(new TranslatableText("screen.stevescarts.cart_assembler.duplicate_side", entry.getKey().asText(), entry.getValue().get(0).getTranslationText(), entry.getValue().get(1).getTranslationText()));
							break;
						}
					}
				}
			}
			assembleButton.setEnabled(!invalid);
		});
		engineSlots.addChangeListener(validator);
		toolSlot.addChangeListener(validator);
		attachmentSlots.addChangeListener(validator);
		storageSlots.addChangeListener(validator);
		addonsSlots.addChangeListener(validator);
		rootPanel.validate(this);
		ScreenNetworking.of(this, NetworkSide.SERVER).receive(StevesCartsScreenHandlers.PACKET_ASSEMBLE_CLICK, (buf) -> handleAssembleClick((ServerPlayerEntity) playerInventory.player));
		ScreenNetworking.of(this, NetworkSide.CLIENT).receive(StevesCartsScreenHandlers.PACKET_INVALID_INFO, (buf) -> {
			int count = buf.readByte();
			Set<Text> texts = new HashSet<>(count);
			for (int i = 0; i < count; i++) {
				texts.add(buf.readText());
			}
		});
	}

	public static void handleAssembleClick(ServerPlayerEntity player) {
		ScreenHandler screenHandler = player.currentScreenHandler;
		if (!(screenHandler instanceof CartAssemblerHandler handler)) {
			StevesCarts.LOGGER.error("Received assemble click packet from non-cart assembler screen handler");
			return;
		}
		if (!handler.blockInventory.getStack(CartAssemblerBlockEntity.OUTPUT_SLOT).isEmpty()) {
			return;
		}
		List<MinecartModule> modules = new ArrayList<>();
		for (int i = 0; i < handler.blockInventory.size(); i++) {
			ItemStack stack = handler.blockInventory.getStack(i);
			if (stack.isEmpty()) {
				continue;
			}
			modules.add(((ModuleItem) stack.getItem()).getType().createModule());
			stack.decrement(1);
		}
		ItemStack stack = ModularMinecartItem.create(modules);
		handler.blockInventory.setStack(CartAssemblerBlockEntity.OUTPUT_SLOT, stack);
	}

	@Override
	public boolean canUse(PlayerEntity player) {
		return ScreenHandler.canUse(this.context, player, StevesCartsBlocks.CART_ASSEMBLER);
	}

	public void addCentered(WWidget widget, int y) {
		WPlainPanel root = (WPlainPanel) this.getRootPanel();
		root.add(widget, (root.getWidth() - widget.getWidth()) / 2 - root.getInsets().left(), y);
	}
}
