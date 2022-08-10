package vswe.stevescarts.screen;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.networking.NetworkSide;
import io.github.cottonmc.cotton.gui.networking.ScreenNetworking;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import io.github.cottonmc.cotton.gui.widget.WPlayerInvPanel;
import io.github.cottonmc.cotton.gui.widget.WWidget;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.block.entity.CartAssemblerBlockEntity;
import vswe.stevescarts.item.CartItem;
import vswe.stevescarts.module.CartModule;
import vswe.stevescarts.module.ModuleGroup;
import vswe.stevescarts.module.ModuleItem;
import vswe.stevescarts.module.ModuleSide;
import vswe.stevescarts.module.ModuleTags;
import vswe.stevescarts.module.ModuleType;
import vswe.stevescarts.module.hull.HullModule;
import vswe.stevescarts.module.hull.HullModuleType;
import vswe.stevescarts.screen.widget.WAssembleButton;
import vswe.stevescarts.screen.widget.WCart;
import vswe.stevescarts.screen.widget.WFixedPanel;
import vswe.stevescarts.screen.widget.WInformation;
import vswe.stevescarts.screen.widget.WModuleSlot;

import net.minecraft.block.entity.FurnaceBlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class CartAssemblerHandler extends SyncedGuiDescription {
	private static final Identifier PACKET_ASSEMBLE_CLICK = StevesCarts.id("assemble_click");
	private final CartAssemblerBlockEntity blockEntity;

	// TODO
	public CartAssemblerHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context, CartAssemblerBlockEntity blockEntity) {
		super(StevesCartsScreenHandlers.CART_ASSEMBLER, syncId, playerInventory, getBlockInventory(context, CartAssemblerBlockEntity.SIZE), getBlockPropertyDelegate(context));
		this.blockEntity = blockEntity;
		WFixedPanel rootPanel = new WFixedPanel();
		rootPanel.setSize(436, 220);
		rootPanel.setInsets(Insets.ROOT_PANEL);
		setRootPanel(rootPanel);
		WPlayerInvPanel playerInventoryPanel = this.createPlayerInventoryPanel(false);
		this.addCentered(playerInventoryPanel, rootPanel.getHeight() - playerInventoryPanel.getHeight());
		WAssembleButton assembleButton = new WAssembleButton(Text.translatable("screen.stevescarts.cart_assembler.assemble"));
		rootPanel.add(assembleButton, 328, 147, 79, 9);
		WItemSlot hullSlot = WItemSlot.outputOf(this.blockInventory, CartAssemblerBlockEntity.HULL_SLOT);
		hullSlot.setFilter(stack -> ModuleType.belongsToGroup(stack, ModuleGroup.HULL));
		rootPanel.add(hullSlot, 12, 18);
		WModuleSlot engineSlots = new WModuleSlot(this.blockInventory, ModuleGroup.ENGINE, CartAssemblerBlockEntity.ENGINE_SLOT_START, 5, 1);
		engineSlots.setFilter(stack -> ModuleType.belongsToGroup(stack, ModuleGroup.ENGINE));
		rootPanel.add(engineSlots, 7, 59);
		WModuleSlot toolSlot = new WModuleSlot(this.blockInventory, ModuleGroup.TOOL, CartAssemblerBlockEntity.TOOL_SLOT, 1, 1);
		rootPanel.add(toolSlot, 7, 89);
		toolSlot.setFilter(stack -> ModuleType.belongsToGroup(stack, ModuleGroup.TOOL));
		WModuleSlot attachmentSlots = new WModuleSlot(this.blockInventory, ModuleGroup.ATTACHMENT, CartAssemblerBlockEntity.ATTACHMENT_SLOT_START, 6, 1);
		rootPanel.add(attachmentSlots, 7, 119);
		attachmentSlots.setFilter(stack -> ModuleType.belongsToGroup(stack, ModuleGroup.ATTACHMENT));
		WModuleSlot storageSlots = new WModuleSlot(this.blockInventory, ModuleGroup.STORAGE, CartAssemblerBlockEntity.STORAGE_SLOT_START, 4, 1);
		rootPanel.add(storageSlots, 7, 149);
		storageSlots.setFilter(stack -> ModuleType.belongsToGroup(stack, ModuleGroup.STORAGE));
		WModuleSlot addonsSlots = new WModuleSlot(this.blockInventory, ModuleGroup.ADDON, CartAssemblerBlockEntity.ADDON_SLOT_START, 6, 2);
		rootPanel.add(addonsSlots, 7, 179);
		addonsSlots.setFilter(stack -> ModuleType.belongsToGroup(stack, ModuleGroup.ADDON));
		WItemSlot outputSlot = WItemSlot.outputOf(this.blockInventory, CartAssemblerBlockEntity.OUTPUT_SLOT);
		rootPanel.add(outputSlot, 330, 182);
		WItemSlot fuelSlot = WItemSlot.outputOf(this.blockInventory, CartAssemblerBlockEntity.FUEL_SLOT);
		fuelSlot.setFilter(FurnaceBlockEntity::canUseAsFuel);
		rootPanel.add(fuelSlot, 384, 182);
		WInformation info = new WInformation();
		rootPanel.add(info, 315, 17);
		WCart cart = new WCart(() -> {
			List<ModuleType<?>> types = new ArrayList<>();
			if (assembleButton.isEnabled()) {
				for (int i = 0; i <= CartAssemblerBlockEntity.ADDON_SLOT_END; i++) {
					ItemStack stack = this.blockInventory.getStack(i);
					if (!stack.isEmpty() && ModuleType.isModule(stack)) {
						types.add(((ModuleItem) stack.getItem()).getType());
					}
				}
			}
			return types;
		}, 187, 120, (e) -> blockEntity.getRoll(), (e) -> blockEntity.getYaw(), this.blockEntity::getWorld);
		this.addCentered(cart, 4);

		assembleButton.setOnClick(() -> ScreenNetworking.of(this, NetworkSide.CLIENT).send(PACKET_ASSEMBLE_CLICK, (buf) -> {
		}));
		ScreenNetworking.of(this, NetworkSide.SERVER).receive(PACKET_ASSEMBLE_CLICK, (buf) -> handleAssembleClick((ServerPlayerEntity) playerInventory.player));

		hullSlot.addChangeListener(((slot, inventory, index, stack) -> {
			HullModuleType<? extends HullModule> e = ModuleType.belongsToGroup(stack, ModuleGroup.HULL) ? (HullModuleType<? extends HullModule>) ((ModuleItem) stack.getItem()).getType() : null;
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
		outputSlot.addChangeListener((slot, inventory, index, stack) -> cart.refresh());
		fuelSlot.setInsertingAllowed(false); // TODO
		outputSlot.setInsertingAllowed(false);

		WItemSlot.ChangeListener validator = ((slot, inventory, index, stack) -> {
			boolean invalid = false;
			List<ModuleType<?>> types = new ArrayList<>();
			HullModuleType<?> hull = null;

			// Check for a hull's presence
			for (int i = 0; i <= CartAssemblerBlockEntity.ADDON_SLOT_END; i++) {
				ItemStack stack2 = this.blockInventory.getStack(i);
				if (i == CartAssemblerBlockEntity.HULL_SLOT) {
					if (!ModuleType.belongsToGroup(stack2, ModuleGroup.HULL)) {
						invalid = true;
						info.successStatus();
						info.setInfoText(Text.translatable("screen.stevescarts.cart_assembler.getting_started"));
						break;
					}
				}
				if (!stack2.isEmpty() && ModuleType.isModule(stack2)) {
					types.add(((ModuleItem) stack2.getItem()).getType());
					if (ModuleType.belongsToGroup(stack2, ModuleGroup.HULL)) {
						hull = (HullModuleType<?>) ((ModuleItem) stack2.getItem()).getType();
					}
				}
			}

			if (hull == null) {
				info.setStatusText(Text.empty());
				cart.refresh();
				return;
			}

			int complexityMax = hull.getHullData().maxComplexity();
			int modCap = hull.getHullData().modularCapacity();
			int cost = types.stream().mapToInt(ModuleType::getModuleCost).sum();

			MutableText totalText = Text.empty().copy();
			totalText.append(Text.translatable("screen.stevescarts.cart_assembler.total_cost", cost)).append("\n");
			totalText.append(Text.translatable("screen.stevescarts.cart_assembler.hull_capacity", modCap)).append("\n");
			totalText.append(Text.translatable("screen.stevescarts.cart_assembler.complexity_cap", complexityMax)).append("\n");
			totalText.append(Text.translatable("screen.stevescarts.cart_assembler.time", "00:00:00"));

			info.setInfoText(totalText);

			// Disallow duplicate modules
			if (!invalid) {
				Set<ModuleType<?>> duplicates;
				Set<ModuleType<?>> uniques = new HashSet<>();
				duplicates = types.stream()
						.filter(ee -> !uniques.add(ee))
						.collect(Collectors.toSet());

				if (!duplicates.isEmpty()) {
					for (ModuleType<?> type : duplicates) {
						if (type.allowsDuplicates()) {
							continue;
						}
						invalid = true;
						info.setErrText(Text.translatable("screen.stevescarts.cart_assembler.duplicate_module", type.getTranslationText()));
						break;
					}
				}
			}

			// Disallow modules that share a side
			if (!invalid) {
				EnumMap<ModuleSide, List<ModuleType<?>>> sideMap = new EnumMap<>(ModuleSide.class);
				for (ModuleType<?> type : types) {
					for (ModuleSide side : type.getSides()) {
						sideMap.computeIfAbsent(side, (s) -> new ArrayList<>()).add(type);
					}
				}
				for (Map.Entry<ModuleSide, List<ModuleType<?>>> entry : sideMap.entrySet()) {
					if (entry.getValue().size() > 1) {
						invalid = true;
						info.setErrText(Text.translatable("screen.stevescarts.cart_assembler.duplicate_side", entry.getValue().get(0).getTranslationText(), entry.getValue().get(1).getTranslationText(), entry.getKey().asText()));
						break;
					}
				}
			}

			// Disallow too complex modules
			if (!invalid) {
				for (ModuleType<?> type : types) {
					if (type.getModuleCost() > complexityMax) {
						invalid = true;
						info.setErrText(Text.translatable("screen.stevescarts.cart_assembler.too_complex_module", type.getTranslationText(), hull.getTranslationText()));
						break;
					}
				}
			}

			// Disallow too complex modules 2 electric boogaloo
			if (!invalid) {
				if (cost > modCap) {
					invalid = true;
					info.setErrText(Text.translatable("screen.stevescarts.cart_assembler.excess_capacity", hull.getTranslationText()));
				}
			}

			// Disallow incompatibilities
			if (!invalid) {
				for (ModuleType<?> type : types) {
					ModuleType<?> incompat = type.isIncompatible(types);
					if (incompat == null) {
						continue;
					}
					invalid = true;
					info.setErrText(Text.translatable("screen.stevescarts.cart_assembler.incompatible", type.getTranslationText(), incompat.getTranslationText()));
				}
			}

			// Disallow missing tag requirements
			if (!invalid) {
				for (ModuleType<?> type : types) {
					var entry = type.matchesTagRequirements(types);
					if (entry == null) {
						continue;
					}
					invalid = true;
					info.setErrText(Text.translatable("screen.stevescarts.cart_assembler.missing_tag_requirement", type.getTranslationText(), entry.getIntValue(), ModuleTags.toText(entry.getKey())));
				}
			}

			if (!invalid) {
				info.successStatus();
			}

			assembleButton.setEnabled(!invalid);
			cart.refresh();
		});
		hullSlot.addChangeListener(validator);
		engineSlots.addChangeListener(validator);
		toolSlot.addChangeListener(validator);
		attachmentSlots.addChangeListener(validator);
		storageSlots.addChangeListener(validator);
		addonsSlots.addChangeListener(validator);

		rootPanel.validate(this);
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
		List<CartModule> modules = new ArrayList<>();
		for (int i = 0; i < handler.blockInventory.size(); i++) {
			ItemStack stack = handler.blockInventory.getStack(i);
			if (stack.isEmpty()) {
				continue;
			}
			modules.add(((ModuleItem) stack.getItem()).getType().create(null));
			stack.decrement(1);
		}
		ItemStack stack = CartItem.createStack(modules);
		handler.blockInventory.setStack(CartAssemblerBlockEntity.OUTPUT_SLOT, stack);
	}

	public void addCentered(WWidget widget, int y) {
		WPlainPanel root = (WPlainPanel) this.getRootPanel();
		root.add(widget, (root.getWidth() - widget.getWidth()) / 2 - root.getInsets().left(), y);
	}
}
