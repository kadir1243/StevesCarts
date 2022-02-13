package vswe.stevescarts.screen;

import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WPlayerInvPanel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.world.World;
import vswe.stevescarts.block.StevesCartsBlocks;

public class CartAssemblerHandler extends SyncedGuiDescription {
	private final ScreenHandlerContext context;

	public CartAssemblerHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
		super(StevesCartsScreenHandlers.CART_ASSEMBLER, syncId, playerInventory, context.get(World::getBlockEntity).map(Inventory.class::cast).orElse(null), null);
		playerInventory.onOpen(playerInventory.player);
		this.context = context;
		WFixedPanel rootPanel = new WFixedPanel();
		rootPanel.setLocation(80, 11);
		rootPanel.setSize(390, 160);
		WPlayerInvPanel playerInventoryPanel = this.createPlayerInventoryPanel(false);
		int invWidth = playerInventoryPanel.getWidth();
		rootPanel.add(playerInventoryPanel, (rootPanel.getWidth() - invWidth), rootPanel.getHeight() - playerInventoryPanel.getHeight());
	}

	@Override
	public boolean canUse(PlayerEntity player) {
		return ScreenHandler.canUse(this.context, player, StevesCartsBlocks.CART_ASSEMBLER);
	}
}
