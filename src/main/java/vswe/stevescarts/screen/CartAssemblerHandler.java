package vswe.stevescarts.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;

public class CartAssemblerHandler extends ScreenHandler {
	private final PlayerInventory playerInventory;
	private final ScreenHandlerContext context;

	public CartAssemblerHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
		super(StevesCartsScreenHandlers.CART_ASSEMBLER, syncId);
		this.playerInventory = playerInventory;
		this.context = context;
	}

	@Override
	public boolean canUse(PlayerEntity player) {
		return true;
	}
}
