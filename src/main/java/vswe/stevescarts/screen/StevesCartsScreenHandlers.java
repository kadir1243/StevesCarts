package vswe.stevescarts.screen;

import vswe.stevescarts.StevesCarts;

import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.registry.Registry;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;

public class StevesCartsScreenHandlers {
	public static final ScreenHandlerType<CartAssemblerHandler> CART_ASSEMBLER = Registry.register(Registry.SCREEN_HANDLER, StevesCarts.id("cart_assembler"), new ScreenHandlerType<>((syncId, playerInventory) -> new CartAssemblerHandler(syncId, playerInventory, ScreenHandlerContext.EMPTY)));
	public static final ExtendedScreenHandlerType<CartHandler> CART = Registry.register(Registry.SCREEN_HANDLER, StevesCarts.id("cart"), new ExtendedScreenHandlerType<>(CartHandler::new));

	public static void init() {
	}
}
