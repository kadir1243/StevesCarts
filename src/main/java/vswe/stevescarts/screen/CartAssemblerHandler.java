package vswe.stevescarts.screen;

import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.networking.NetworkSide;
import io.github.cottonmc.cotton.gui.networking.ScreenNetworking;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import io.github.cottonmc.cotton.gui.widget.WPlayerInvPanel;
import io.github.cottonmc.cotton.gui.widget.WWidget;
import io.github.cottonmc.cotton.gui.widget.data.Insets;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
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
import vswe.stevescarts.modules.ModuleSide;
import vswe.stevescarts.modules.hull.HullModule;
import vswe.stevescarts.modules.tags.ModuleTags;
import vswe.stevescarts.screen.widget.WAssembleButton;
import vswe.stevescarts.screen.widget.WCart;
import vswe.stevescarts.screen.widget.WFixedPanel;
import vswe.stevescarts.screen.widget.WInformation;
import vswe.stevescarts.screen.widget.WModuleSlot;

public class CartAssemblerHandler extends SyncedGuiDescription {
	private final ScreenHandlerContext context;

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
		rootPanel.add(assembleButton, 328, 147, 79, 9);
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
		WItemSlot outputSlot = WItemSlot.outputOf(this.blockInventory, CartAssemblerBlockEntity.OUTPUT_SLOT);
		outputSlot.setFilter((stack) -> stack.isOf(StevesCartsItems.MODULAR_CART));
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
		rootPanel.add(outputSlot, 330, 182);
		WItemSlot fuelSlot = WItemSlot.outputOf(this.blockInventory, CartAssemblerBlockEntity.FUEL_SLOT);
		rootPanel.add(fuelSlot, 384, 182);
		assembleButton.setOnClick(() -> ScreenNetworking.of(this, NetworkSide.CLIENT).send(StevesCartsScreenHandlers.PACKET_ASSEMBLE_CLICK, (buf) -> {
		}));
		hullSlot.addChangeListener(((slot, inventory, index, stack) -> {
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
		outputSlot.addChangeListener((slot, inventory, index, stack) -> cart.markDirty());
		WItemSlot.ChangeListener validator = ((slot, inventory, index, stack) -> {
			boolean invalid = false;
			List<MinecartModuleType<?>> types = new ArrayList<>();
			MinecartModuleType<?> hull = null;

			// Check for a hull's presence
			for (int i = 0; i <= CartAssemblerBlockEntity.ADDON_SLOT_END; i++) {
				ItemStack stack2 = this.blockInventory.getStack(i);
				if (i == CartAssemblerBlockEntity.HULL_SLOT) {
					if (!MinecartModuleType.isHull(stack2)) {
						invalid = true;
						info.successStatus();
						info.setInfoText(new TranslatableText("screen.stevescarts.cart_assembler.getting_started"));
						break;
					}
				}
				if (!stack2.isEmpty() && MinecartModuleType.isModule(stack2)) {
					types.add(((ModuleItem) stack2.getItem()).getType());
					if (MinecartModuleType.isHull(stack2)) {
						hull = ((ModuleItem) stack2.getItem()).getType();
					}
				}
			}

			if (hull == null) {
				info.setStatusText(LiteralText.EMPTY);
				cart.markDirty();
				return;
			}

			int complexityMax = hull.getHullData().complexityMax();
			int modCap = hull.getHullData().modularCapacity();
			int cost = types.stream().mapToInt(MinecartModuleType::getModuleCost).sum();

			MutableText totalText = LiteralText.EMPTY.copy();
			totalText.append(new TranslatableText("screen.stevescarts.cart_assembler.total_cost", cost)).append("\n");
			totalText.append(new TranslatableText("screen.stevescarts.cart_assembler.hull_capacity", modCap)).append("\n");
			totalText.append(new TranslatableText("screen.stevescarts.cart_assembler.complexity_cap", complexityMax)).append("\n");
			totalText.append(new TranslatableText("screen.stevescarts.cart_assembler.time", "00:00:00"));

			info.setInfoText(totalText);

			// Disallow duplicate modules
			if (!invalid) {
				Set<MinecartModuleType<?>> duplicates;
				Set<MinecartModuleType<?>> uniques = new HashSet<>();
				duplicates = types.stream()
						.filter(ee -> !uniques.add(ee))
						.collect(Collectors.toSet());

				if (!duplicates.isEmpty()) {
					for (MinecartModuleType<?> type : duplicates) {
						if (type.allowsDuplicates()) {
							continue;
						}
						invalid = true;
						info.setErrText(new TranslatableText("screen.stevescarts.cart_assembler.duplicate_module", type.getTranslationText()));
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
						info.setErrText(new TranslatableText("screen.stevescarts.cart_assembler.duplicate_side", entry.getValue().get(0).getTranslationText(), entry.getValue().get(1).getTranslationText(), entry.getKey().asText()));
						break;
					}
				}
			}

			// Disallow too complex modules
			if (!invalid) {
				for (MinecartModuleType<?> type : types) {
					if (type.getModuleCost() > complexityMax) {
						invalid = true;
						info.setErrText(new TranslatableText("screen.stevescarts.cart_assembler.too_complex_module", type.getTranslationText(), hull.getTranslationText()));
						break;
					}
				}
			}

			// Disallow too complex modules 2 electric boogaloo
			if (!invalid) {
				if (cost > modCap) {
					invalid = true;
					info.setErrText(new TranslatableText("screen.stevescarts.cart_assembler.excess_capacity", hull.getTranslationText()));
				}
			}

			// Disallow missing explicit requirements
			if (!invalid) {
				for (MinecartModuleType<?> type : types) {
					MinecartModuleType<?> req = type.matchesRequirements(types);
					if (req == null) {
						continue;
					}
					invalid = true;
					info.setErrText(new TranslatableText("screen.stevescarts.cart_assembler.missing_requirement", type.getTranslationText(), req.getTranslationText()));
				}
			}

			// Disallow incompatibilities
			if (!invalid) {
				for (MinecartModuleType<?> type : types) {
					MinecartModuleType<?> incompat = type.checkIncompatibilities(types);
					if (incompat == null) {
						continue;
					}
					invalid = true;
					info.setErrText(new TranslatableText("screen.stevescarts.cart_assembler.incompatible", type.getTranslationText(), incompat.getTranslationText()));
				}
			}

			// Disallow missing tag requirements
			if (!invalid) {
				for (MinecartModuleType<?> type : types) {
					var entry = type.matchesTagRequirements(types);
					if (entry == null) {
						continue;
					}
					invalid = true;
					info.setErrText(new TranslatableText("screen.stevescarts.cart_assembler.missing_tag_requirement", type.getTranslationText(), entry.getIntValue(), ModuleTags.toText(entry.getKey())));
				}
			}

			if (!invalid) {
				info.successStatus();
			}

			assembleButton.setEnabled(!invalid);
			cart.markDirty();
		});
		hullSlot.addChangeListener(validator);
		engineSlots.addChangeListener(validator);
		toolSlot.addChangeListener(validator);
		attachmentSlots.addChangeListener(validator);
		storageSlots.addChangeListener(validator);
		addonsSlots.addChangeListener(validator);
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
