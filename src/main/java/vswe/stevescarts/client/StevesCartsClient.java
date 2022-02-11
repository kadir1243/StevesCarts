package vswe.stevescarts.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.block.StevesCartsBlocks;
import vswe.stevescarts.client.entity.ModularMinecartRenderer;
import vswe.stevescarts.client.modules.ModuleRenderDispatcher;
import vswe.stevescarts.entity.network.SpawnPacket;
import vswe.stevescarts.entity.network.UpdatePacket;
import vswe.stevescarts.item.StevesCartsItems;

import static vswe.stevescarts.block.StevesCartsBlocks.*;

public class StevesCartsClient implements ClientModInitializer {
	private static ModuleRenderDispatcher moduleRenderDispatcher;

	@Override
	public void onInitializeClient() {
		moduleRenderDispatcher = new ModuleRenderDispatcher(MinecraftClient.getInstance().textRenderer, MinecraftClient.getInstance().getItemRenderer());
		EntityRendererRegistry.register(StevesCarts.MODULAR_MINECART_ENTITY, ModularMinecartRenderer::new);
		SpawnPacket.init();
		UpdatePacket.init();
		BlockRenderLayerMap.INSTANCE.putBlock(StevesCartsBlocks.ADVANCED_DETECTOR_RAIL, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(StevesCartsBlocks.JUNCTION_RAIL, RenderLayer.getCutout());
		BuiltinItemRendererRegistry.INSTANCE.register(StevesCartsItems.MODULAR_CART, ModularMinecartRenderer::renderAsItem);
	}

	public static ModuleRenderDispatcher getModuleRenderDispatcher() {
		return moduleRenderDispatcher;
	}
}
