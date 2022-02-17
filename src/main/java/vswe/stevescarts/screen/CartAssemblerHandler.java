package vswe.stevescarts.screen;

import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import io.github.cottonmc.cotton.gui.widget.WPlayerInvPanel;
import io.github.cottonmc.cotton.gui.widget.WWidget;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.World;
import vswe.stevescarts.block.StevesCartsBlocks;
import vswe.stevescarts.block.entity.CartAssemblerBlockEntity;
import vswe.stevescarts.item.StevesCartsItems;
import vswe.stevescarts.modules.MinecartModuleType;
import vswe.stevescarts.modules.ModuleCategory;
import vswe.stevescarts.screen.widget.WAssembleButton;
import vswe.stevescarts.screen.widget.WCart;
import vswe.stevescarts.screen.widget.WFixedPanel;
import vswe.stevescarts.screen.widget.WModuleSlot;

import java.util.Collections;

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
		rootPanel.add(assembleButton, 330, 142, 79, 9);
		WCart cart = new WCart(() -> null, 187, 120);
		this.addCentered(cart, 4);
		WItemSlot hullSlot = WItemSlot.outputOf(this.blockInventory, 0);
		hullSlot.setFilter(MinecartModuleType::isHull);
		hullSlot.addChangeListener(((slot, inventory, index, stack) -> {
			assembleButton.setEnabled(MinecartModuleType.isHull(stack));
		}));
		rootPanel.add(hullSlot, 12, 18);
		WModuleSlot engineSlots = new WModuleSlot(this.blockInventory, 1, 5, 1, ModuleCategory.ENGINE);
		engineSlots.setFilter(MinecartModuleType::isEngine);
		rootPanel.add(engineSlots, 7, 59);
		// TODO: set filters
		WModuleSlot toolSlot = new WModuleSlot(this.blockInventory, 6, 1, 1, ModuleCategory.TOOL);
		rootPanel.add(toolSlot, 7, 89);
		WModuleSlot attachmentSlots = new WModuleSlot(this.blockInventory, 7, 6, 1, ModuleCategory.ATTACHMENT);
		rootPanel.add(attachmentSlots, 7, 119);
		WModuleSlot storageSlots = new WModuleSlot(this.blockInventory, 13, 4, 1, ModuleCategory.STORAGE);
		rootPanel.add(storageSlots, 7, 149);
		WModuleSlot addonsSlots = new WModuleSlot(this.blockInventory, 17, 6, 2, ModuleCategory.ADDON);
		rootPanel.add(addonsSlots, 7, 179);
		WItemSlot outputSlot = WItemSlot.outputOf(this.blockInventory, 29);
		outputSlot.setFilter((stack) -> stack.isOf(StevesCartsItems.MODULAR_CART));
		rootPanel.add(outputSlot, 330, 182);
		WItemSlot fuelSlot = WItemSlot.outputOf(this.blockInventory, 30);
		rootPanel.add(fuelSlot, 376, 182);
		rootPanel.validate(this);
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
