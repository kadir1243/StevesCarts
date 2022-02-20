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
import net.minecraft.text.TranslatableText;
import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.block.StevesCartsBlocks;
import vswe.stevescarts.block.entity.CartAssemblerBlockEntity;
import vswe.stevescarts.item.ModularMinecartItem;
import vswe.stevescarts.item.StevesCartsItems;
import vswe.stevescarts.item.modules.ModuleItem;
import vswe.stevescarts.modules.MinecartModule;
import vswe.stevescarts.modules.MinecartModuleType;
import vswe.stevescarts.modules.ModuleCategory;
import vswe.stevescarts.modules.hull.HullModule;
import vswe.stevescarts.screen.widget.WAssembleButton;
import vswe.stevescarts.screen.widget.WCart;
import vswe.stevescarts.screen.widget.WFixedPanel;
import vswe.stevescarts.screen.widget.WModuleSlot;

import java.util.ArrayList;
import java.util.List;

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
		rootPanel.add(assembleButton, 330, 142, 79, 9);
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
		rootPanel.add(fuelSlot, 376, 182);
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
		rootPanel.validate(this);
		ScreenNetworking.of(this, NetworkSide.SERVER).receive(StevesCartsScreenHandlers.PACKET_ASSEMBLE_CLICK, (buf) -> handleAssembleClick((ServerPlayerEntity) playerInventory.player));
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
