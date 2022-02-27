package vswe.stevescarts.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.resource.ResourceType;
import vswe.stevescarts.block.StevesCartsBlocks;
import vswe.stevescarts.client.entity.ModularMinecartRenderer;
import vswe.stevescarts.client.modules.renderer.ModuleRenderDispatcher;
import vswe.stevescarts.entity.StevesCartsEntities;
import vswe.stevescarts.entity.network.SpawnPacket;
import vswe.stevescarts.entity.network.UpdatePacket;
import vswe.stevescarts.item.StevesCartsItems;
import vswe.stevescarts.screen.StevesCartsScreenHandlers;

@Environment(EnvType.CLIENT)
public class StevesCartsClient implements ClientModInitializer {
	private static ModuleRenderDispatcher moduleRenderDispatcher;

	public static ModuleRenderDispatcher getModuleRenderDispatcher() {
		return moduleRenderDispatcher;
	}

	@Override
	public void onInitializeClient() {
		moduleRenderDispatcher = new ModuleRenderDispatcher(MinecraftClient.getInstance().textRenderer, MinecraftClient.getInstance().getItemRenderer());
		ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(moduleRenderDispatcher);
		EntityRendererRegistry.register(StevesCartsEntities.MODULAR_MINECART_ENTITY, ModularMinecartRenderer::new);
		SpawnPacket.init();
		UpdatePacket.init();
		BlockRenderLayerMap.INSTANCE.putBlock(StevesCartsBlocks.ADVANCED_DETECTOR_RAIL, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(StevesCartsBlocks.JUNCTION_RAIL, RenderLayer.getCutout());
		BuiltinItemRendererRegistry.INSTANCE.register(StevesCartsItems.MODULAR_CART, ModularMinecartRenderer::renderAsItem);
		StevesCartsScreenHandlers.initClient();
	}
}
