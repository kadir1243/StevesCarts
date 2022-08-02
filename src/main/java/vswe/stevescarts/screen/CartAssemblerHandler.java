package vswe.stevescarts.screen;

import java.util.ArrayList;
import java.util.List;

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
import vswe.stevescarts.module.ModuleType;
import vswe.stevescarts.module.hull.HullModule;
import vswe.stevescarts.module.hull.HullModuleType;
import vswe.stevescarts.screen.widget.WAssembleButton;
import vswe.stevescarts.screen.widget.WFixedPanel;
import vswe.stevescarts.screen.widget.WModuleSlot;

import net.minecraft.block.entity.FurnaceBlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class CartAssemblerHandler extends SyncedGuiDescription {
	private static final Identifier PACKET_ASSEMBLE_CLICK = StevesCarts.id("assemble_click");
	private final ScreenHandlerContext context;

	// TODO
	public CartAssemblerHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
		super(StevesCartsScreenHandlers.CART_ASSEMBLER, syncId, playerInventory, getBlockInventory(context, CartAssemblerBlockEntity.SIZE), getBlockPropertyDelegate(context));
		this.context = context;
		WFixedPanel rootPanel = new WFixedPanel();
		rootPanel.setSize(436, 220);
		rootPanel.setInsets(Insets.ROOT_PANEL);
		setRootPanel(rootPanel);
		WPlayerInvPanel playerInventoryPanel = this.createPlayerInventoryPanel(false);
		this.addCentered(playerInventoryPanel, rootPanel.getHeight() - playerInventoryPanel.getHeight());
		WAssembleButton assembleButton = new WAssembleButton(Text.translatable("screen.stevescarts.cart_assembler.assemble"));
		rootPanel.add(assembleButton, 328, 147, 79, 9);
		WItemSlot hullSlot = WItemSlot.outputOf(this.blockInventory, CartAssemblerBlockEntity.HULL_SLOT);
		hullSlot.setFilter(stack -> ModuleType.checkGroup(stack, ModuleGroup.HULL));
		rootPanel.add(hullSlot, 12, 18);
		WModuleSlot engineSlots = new WModuleSlot(this.blockInventory, ModuleGroup.ENGINE, CartAssemblerBlockEntity.ENGINE_SLOT_START, 5, 1);
		engineSlots.setFilter(stack -> ModuleType.checkGroup(stack, ModuleGroup.ENGINE));
		rootPanel.add(engineSlots, 7, 59);
		WModuleSlot toolSlot = new WModuleSlot(this.blockInventory, ModuleGroup.TOOL, CartAssemblerBlockEntity.TOOL_SLOT, 1, 1);
		rootPanel.add(toolSlot, 7, 89);
		toolSlot.setFilter(stack -> ModuleType.checkGroup(stack, ModuleGroup.TOOL));
		WModuleSlot attachmentSlots = new WModuleSlot(this.blockInventory, ModuleGroup.ATTACHMENT, CartAssemblerBlockEntity.ATTACHMENT_SLOT_START, 6, 1);
		rootPanel.add(attachmentSlots, 7, 119);
		attachmentSlots.setFilter(stack -> ModuleType.checkGroup(stack, ModuleGroup.ATTACHMENT));
		WModuleSlot storageSlots = new WModuleSlot(this.blockInventory, ModuleGroup.STORAGE, CartAssemblerBlockEntity.STORAGE_SLOT_START, 4, 1);
		rootPanel.add(storageSlots, 7, 149);
		storageSlots.setFilter(stack -> ModuleType.checkGroup(stack, ModuleGroup.STORAGE));
		WModuleSlot addonsSlots = new WModuleSlot(this.blockInventory, ModuleGroup.ADDON, CartAssemblerBlockEntity.ADDON_SLOT_START, 6, 2);
		rootPanel.add(addonsSlots, 7, 179);
		addonsSlots.setFilter(stack -> ModuleType.checkGroup(stack, ModuleGroup.ADDON));
		WItemSlot outputSlot = WItemSlot.outputOf(this.blockInventory, CartAssemblerBlockEntity.OUTPUT_SLOT);
		rootPanel.add(outputSlot, 330, 182);
		WItemSlot fuelSlot = WItemSlot.outputOf(this.blockInventory, CartAssemblerBlockEntity.FUEL_SLOT);
		fuelSlot.setFilter(FurnaceBlockEntity::canUseAsFuel);
		rootPanel.add(fuelSlot, 384, 182);

		assembleButton.setOnClick(() -> ScreenNetworking.of(this, NetworkSide.CLIENT).send(PACKET_ASSEMBLE_CLICK, (buf) -> {
		}));
		ScreenNetworking.of(this, NetworkSide.SERVER).receive(PACKET_ASSEMBLE_CLICK, (buf) -> handleAssembleClick((ServerPlayerEntity) playerInventory.player));

		hullSlot.addChangeListener(((slot, inventory, index, stack) -> {
			HullModuleType<? extends HullModule> e = ModuleType.checkGroup(stack, ModuleGroup.HULL) ? (HullModuleType<? extends HullModule>) ((ModuleItem) stack.getItem()).getType() : null;
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
