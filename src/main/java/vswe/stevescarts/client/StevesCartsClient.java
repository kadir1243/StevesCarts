package vswe.stevescarts.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.block.StevesCartsBlocks;
import vswe.stevescarts.client.entity.ModularMinecartRenderer;
import vswe.stevescarts.client.modules.ModuleRenderDispatcher;
import vswe.stevescarts.entity.ModularMinecartEntity;
import vswe.stevescarts.modules.MinecartModuleType;

public class StevesCartsClient implements ClientModInitializer {
	private static ModuleRenderDispatcher moduleRenderDispatcher;

	@Override
	public void onInitializeClient() {
		moduleRenderDispatcher = new ModuleRenderDispatcher(MinecraftClient.getInstance().textRenderer, MinecraftClient.getInstance().getItemRenderer());
		EntityRendererRegistry.register(StevesCarts.MODULAR_MINECART_ENTITY, ModularMinecartRenderer::new);
//		moduleRenderDispatcher.register(MinecartModuleType.WOODEN_HULL, );
		ModularMinecartEntity.SpawnPacket.init();
		BlockRenderLayerMap.INSTANCE.putBlock(StevesCartsBlocks.ADVANCED_DETECTOR_RAIL, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(StevesCartsBlocks.ADVANCED_DETECTOR_RAIL, RenderLayer.getCutout());
	}

	public static ModuleRenderDispatcher getModuleRenderDispatcher() {
		return moduleRenderDispatcher;
	}
}
