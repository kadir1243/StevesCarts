package vswe.stevescarts.client;

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import vswe.stevescarts.block.StevesCartsBlocks;
import vswe.stevescarts.item.StevesCartsItems;
import vswe.stevescarts.screen.CartAssemblerHandler;
import vswe.stevescarts.screen.StevesCartsScreenHandlers;

import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;

public class StevesCartsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutoutMipped(), StevesCartsBlocks.ADVANCED_DETECTOR_RAIL, StevesCartsBlocks.JUNCTION_RAIL);
		//noinspection RedundantTypeArguments
		HandledScreens.<CartAssemblerHandler, CottonInventoryScreen<CartAssemblerHandler>>register(StevesCartsScreenHandlers.CART_ASSEMBLER, CottonInventoryScreen::new);

		BuiltinItemRendererRegistry.INSTANCE.register(StevesCartsItems.CART, ((stack, mode, matrices, vertexConsumers, light, overlay) -> {
			// TODO
		}));
	}
}
