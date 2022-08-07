package vswe.stevescarts.screen;

import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.block.entity.CartAssemblerBlockEntity;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;

public class StevesCartsScreenHandlers {
	public static final ScreenHandlerType<CartAssemblerHandler> CART_ASSEMBLER = Registry.register(Registry.SCREEN_HANDLER, StevesCarts.id("cart_assembler"), new ExtendedScreenHandlerType<>((syncId, playerInventory, buf) -> new CartAssemblerHandler(syncId, playerInventory, ScreenHandlerContext.EMPTY, getBlockEntity(buf.readBlockPos()))));
	public static final ExtendedScreenHandlerType<CartHandler> CART = Registry.register(Registry.SCREEN_HANDLER, StevesCarts.id("cart"), new ExtendedScreenHandlerType<>(CartHandler::new));

	public static void init() {
	}

	private static CartAssemblerBlockEntity getBlockEntity(BlockPos pos) {
		return (CartAssemblerBlockEntity) MinecraftClient.getInstance().world.getBlockEntity(pos);
	}
}
