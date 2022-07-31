package vswe.stevescarts.screen;

import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import io.github.cottonmc.cotton.gui.widget.WPlayerInvPanel;
import io.github.cottonmc.cotton.gui.widget.WWidget;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import vswe.stevescarts.block.entity.CartAssemblerBlockEntity;
import vswe.stevescarts.screen.widget.WFixedPanel;

import net.minecraft.block.entity.FurnaceBlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandlerContext;

public class CartAssemblerHandler extends SyncedGuiDescription {
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
		WItemSlot hullSlot = WItemSlot.outputOf(this.blockInventory, CartAssemblerBlockEntity.HULL_SLOT);
		rootPanel.add(hullSlot, 12, 18);
		WItemSlot outputSlot = WItemSlot.outputOf(this.blockInventory, CartAssemblerBlockEntity.OUTPUT_SLOT);
		rootPanel.add(outputSlot, 330, 182);
		WItemSlot fuelSlot = WItemSlot.outputOf(this.blockInventory, CartAssemblerBlockEntity.FUEL_SLOT);
		fuelSlot.setFilter(FurnaceBlockEntity::canUseAsFuel);
		rootPanel.add(fuelSlot, 384, 182);
	}

	public void addCentered(WWidget widget, int y) {
		WPlainPanel root = (WPlainPanel) this.getRootPanel();
		root.add(widget, (root.getWidth() - widget.getWidth()) / 2 - root.getInsets().left(), y);
	}
}
