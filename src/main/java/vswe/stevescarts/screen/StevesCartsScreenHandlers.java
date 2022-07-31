package vswe.stevescarts.screen;

import vswe.stevescarts.StevesCarts;

import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.registry.Registry;

public class StevesCartsScreenHandlers {
	public static final ScreenHandlerType<CartAssemblerHandler> CART_ASSEMBLER = Registry.register(Registry.SCREEN_HANDLER, StevesCarts.id("cart_assembler"), new ScreenHandlerType<>((syncId, playerInventory) -> new CartAssemblerHandler(syncId, playerInventory, ScreenHandlerContext.EMPTY)));

	public static void init() {
	}
}
