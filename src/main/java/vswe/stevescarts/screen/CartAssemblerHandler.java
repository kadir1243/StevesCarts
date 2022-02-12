package vswe.stevescarts.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;
import vswe.stevescarts.block.StevesCartsBlocks;

public class CartAssemblerHandler extends ScreenHandler {
	private final PlayerInventory playerInventory;
	private final ScreenHandlerContext context;

	public CartAssemblerHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
		super(StevesCartsScreenHandlers.CART_ASSEMBLER, syncId);
		playerInventory.onOpen(playerInventory.player);
		this.playerInventory = playerInventory;
		this.context = context;
		// TODO
		for (int i = 0; i < 3; ++i) {
			for (int k = 0; k < 9; ++k) {
				this.addSlot(new Slot(playerInventory, k + i * 9 + 9, offsetX() + k * 18, i * 18 + offsetY()));
			}
		}
		for (int j = 0; j < 9; ++j) {
			this.addSlot(new Slot(playerInventory, j, offsetX() + j * 18, 58 + offsetY()));
		}
	}

	@Override
	public boolean canUse(PlayerEntity player) {
		return ScreenHandler.canUse(this.context, player, StevesCartsBlocks.CART_ASSEMBLER);
	}

	protected int offsetX() {
		return 196;
	}

	protected int offsetY() {
		return 194;
	}
}
