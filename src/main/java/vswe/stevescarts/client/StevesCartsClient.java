package vswe.stevescarts.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.block.StevesCartsBlocks;
import vswe.stevescarts.client.entity.ModularMinecartRenderer;
import vswe.stevescarts.client.modules.ModuleRenderDispatcher;
import vswe.stevescarts.entity.ModularMinecartEntity;
import vswe.stevescarts.item.StevesCartsItems;
import vswe.stevescarts.modules.MinecartModuleType;

public class StevesCartsClient implements ClientModInitializer {
	private static ModuleRenderDispatcher moduleRenderDispatcher;

	@Override
	public void onInitializeClient() {
		moduleRenderDispatcher = new ModuleRenderDispatcher(MinecraftClient.getInstance().textRenderer, MinecraftClient.getInstance().getItemRenderer());
		EntityRendererRegistry.register(StevesCarts.MODULAR_MINECART_ENTITY, ModularMinecartRenderer::new);
		ModularMinecartEntity.SpawnPacket.init();
		ModularMinecartEntity.UpdatePacket.init();
		BlockRenderLayerMap.INSTANCE.putBlock(StevesCartsBlocks.ADVANCED_DETECTOR_RAIL, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(StevesCartsBlocks.JUNCTION_RAIL, RenderLayer.getCutout());
		BuiltinItemRendererRegistry.INSTANCE.register(StevesCartsItems.MODULAR_CART, ModularMinecartRenderer::renderAsItem);
	}

	public static ModuleRenderDispatcher getModuleRenderDispatcher() {
		return moduleRenderDispatcher;
	}
}
