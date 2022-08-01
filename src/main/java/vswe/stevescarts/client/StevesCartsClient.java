package vswe.stevescarts.client;

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import vswe.stevescarts.block.StevesCartsBlocks;
import vswe.stevescarts.client.render.entity.CartEntityRenderer;
import vswe.stevescarts.client.render.module.ModuleRenderDispatcher;
import vswe.stevescarts.entity.StevesCartsEntities;
import vswe.stevescarts.entity.network.CartSpawnS2CPacket;
import vswe.stevescarts.item.StevesCartsItems;
import vswe.stevescarts.screen.CartAssemblerHandler;
import vswe.stevescarts.screen.StevesCartsScreenHandlers;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.resource.ResourceType;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;

public class StevesCartsClient implements ClientModInitializer {
	private static ModuleRenderDispatcher moduleRenderDispatcher;

	public static ModuleRenderDispatcher getModuleRenderDispatcher() {
		return moduleRenderDispatcher;
	}

	@Override
	public void onInitializeClient() {
		moduleRenderDispatcher = new ModuleRenderDispatcher(MinecraftClient.getInstance().textRenderer, MinecraftClient.getInstance().getItemRenderer());
		ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(moduleRenderDispatcher);

		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutoutMipped(), StevesCartsBlocks.ADVANCED_DETECTOR_RAIL, StevesCartsBlocks.JUNCTION_RAIL);

		//noinspection RedundantTypeArguments
		HandledScreens.<CartAssemblerHandler, CottonInventoryScreen<CartAssemblerHandler>>register(StevesCartsScreenHandlers.CART_ASSEMBLER, CottonInventoryScreen::new);

		BuiltinItemRendererRegistry.INSTANCE.register(StevesCartsItems.CART, ((stack, mode, matrices, vertexConsumers, light, overlay) -> {
			// TODO
		}));

		EntityRendererRegistry.register(StevesCartsEntities.CART, CartEntityRenderer::new);

		CartSpawnS2CPacket.init();
	}
}
