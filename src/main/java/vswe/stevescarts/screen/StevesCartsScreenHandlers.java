package vswe.stevescarts.screen;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import vswe.stevescarts.StevesCarts;

public class StevesCartsScreenHandlers {
	public static final ScreenHandlerType<CartAssemblerHandler> CART_ASSEMBLER = ScreenHandlerRegistry.registerSimple(StevesCarts.id("cart_assembler"), (syncId, playerInventory) -> new CartAssemblerHandler(syncId, playerInventory, ScreenHandlerContext.EMPTY));
	public static final Identifier PACKET_ASSEMBLE_CLICK = StevesCarts.id("assemble_click");

	public static void init() {
		ServerPlayNetworking.registerGlobalReceiver(PACKET_ASSEMBLE_CLICK, (server, player, handler, buf, responseSender) -> server.execute(() -> CartAssemblerHandler.handleAssembleClick(player)));
	}
}
