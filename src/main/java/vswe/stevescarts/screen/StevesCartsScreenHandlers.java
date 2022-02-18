package vswe.stevescarts.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.client.screen.CartAssemblerScreen;

public class StevesCartsScreenHandlers {
	public static final ScreenHandlerType<CartAssemblerHandler> CART_ASSEMBLER = ScreenHandlerRegistry.registerSimple(StevesCarts.id("cart_assembler"), (syncId, playerInventory) -> new CartAssemblerHandler(syncId, playerInventory, ScreenHandlerContext.EMPTY));
	public static final Identifier PACKET_ASSEMBLE_CLICK = StevesCarts.id("assemble_click");
	public static final Identifier PACKET_OPEN_CART = StevesCarts.id("open_cart");

	public static void init() {
		ServerPlayNetworking.registerGlobalReceiver(PACKET_ASSEMBLE_CLICK, (server, player, handler, buf, responseSender) -> server.execute(() -> CartAssemblerHandler.handleAssembleClick(player)));
	}

	@Environment(EnvType.CLIENT)
	public static void initClient() {
		ScreenRegistry.register(StevesCartsScreenHandlers.CART_ASSEMBLER, CartAssemblerScreen::new);
		ClientPlayNetworking.registerGlobalReceiver(PACKET_OPEN_CART, (client, handler, buf, responseSender) -> {
			int syncId = buf.readByte();
			int cartId = buf.readInt();
			client.execute(() -> CartHandler.handleOpen(client, syncId, cartId));
		});
	}
}
