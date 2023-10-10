package vswe.stevescarts.screen;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.math.BlockPos;
import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.block.entity.CartAssemblerBlockEntity;

public class StevesCartsScreenHandlers {
	public static final ScreenHandlerType<CartAssemblerHandler> CART_ASSEMBLER = Registry.register(Registries.SCREEN_HANDLER, StevesCarts.id("cart_assembler"), new ExtendedScreenHandlerType<>((syncId, playerInventory, buf) -> new CartAssemblerHandler(syncId, playerInventory, ScreenHandlerContext.EMPTY, getBlockEntity(buf.readBlockPos()))));
	public static final ExtendedScreenHandlerType<CartHandler> CART = Registry.register(Registries.SCREEN_HANDLER, StevesCarts.id("cart"), new ExtendedScreenHandlerType<>(CartHandler::new));

	public static void init() {
	}

	private static CartAssemblerBlockEntity getBlockEntity(BlockPos pos) {
		return (CartAssemblerBlockEntity) MinecraftClient.getInstance().world.getBlockEntity(pos);
	}
}
