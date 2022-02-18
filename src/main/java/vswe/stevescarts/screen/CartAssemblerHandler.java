package vswe.stevescarts.screen;

import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import io.github.cottonmc.cotton.gui.widget.WPlayerInvPanel;
import io.github.cottonmc.cotton.gui.widget.WWidget;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.World;
import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.block.StevesCartsBlocks;
import vswe.stevescarts.block.entity.CartAssemblerBlockEntity;
import vswe.stevescarts.item.ModularMinecartItem;
import vswe.stevescarts.item.StevesCartsItems;
import vswe.stevescarts.item.modules.ModuleItem;
import vswe.stevescarts.modules.MinecartModule;
import vswe.stevescarts.modules.MinecartModuleType;
import vswe.stevescarts.modules.ModuleCategory;
import vswe.stevescarts.modules.ModuleStorage;
import vswe.stevescarts.screen.widget.WAssembleButton;
import vswe.stevescarts.screen.widget.WCart;
import vswe.stevescarts.screen.widget.WFixedPanel;
import vswe.stevescarts.screen.widget.WModuleSlot;

import java.util.ArrayList;
import java.util.Collections;
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
		WCart cart = new WCart(() -> null, 187, 120);
		this.addCentered(cart, 4);
		WItemSlot hullSlot = WItemSlot.outputOf(this.blockInventory, CartAssemblerBlockEntity.HULL_SLOT);
		hullSlot.setFilter(MinecartModuleType::isHull);
		hullSlot.addChangeListener(((slot, inventory, index, stack) -> {
			assembleButton.setEnabled(MinecartModuleType.isHull(stack));
		}));
		rootPanel.add(hullSlot, 12, 18);
		WModuleSlot engineSlots = new WModuleSlot(this.blockInventory, CartAssemblerBlockEntity.ENGINE_SLOT_START, 5, 1, ModuleCategory.ENGINE);
		engineSlots.setFilter(MinecartModuleType::isEngine);
		rootPanel.add(engineSlots, 7, 59);
		// TODO: set filters
		WModuleSlot toolSlot = new WModuleSlot(this.blockInventory, CartAssemblerBlockEntity.TOOL_SLOT, 1, 1, ModuleCategory.TOOL);
		rootPanel.add(toolSlot, 7, 89);
		WModuleSlot attachmentSlots = new WModuleSlot(this.blockInventory, CartAssemblerBlockEntity.ATTACHMENT_SLOT_START, 6, 1, ModuleCategory.ATTACHMENT);
		rootPanel.add(attachmentSlots, 7, 119);
		WModuleSlot storageSlots = new WModuleSlot(this.blockInventory, CartAssemblerBlockEntity.STORAGE_SLOT_START, 4, 1, ModuleCategory.STORAGE);
		rootPanel.add(storageSlots, 7, 149);
		WModuleSlot addonsSlots = new WModuleSlot(this.blockInventory, CartAssemblerBlockEntity.ADDON_SLOT_START, 6, 2, ModuleCategory.ADDON);
		rootPanel.add(addonsSlots, 7, 179);
		this.outputSlot = WItemSlot.outputOf(this.blockInventory, CartAssemblerBlockEntity.OUTPUT_SLOT);
		this.outputSlot.setFilter((stack) -> stack.isOf(StevesCartsItems.MODULAR_CART));
		rootPanel.add(this.outputSlot, 330, 182);
		WItemSlot fuelSlot = WItemSlot.outputOf(this.blockInventory, CartAssemblerBlockEntity.FUEL_SLOT);
		rootPanel.add(fuelSlot, 376, 182);
		assembleButton.setOnClick(() -> ClientPlayNetworking.send(StevesCartsScreenHandlers.PACKET_ASSEMBLE_CLICK, PacketByteBufs.empty()));
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
