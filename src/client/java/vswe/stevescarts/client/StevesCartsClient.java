package vswe.stevescarts.client;

import vswe.stevescarts.block.StevesCartsBlocks;

import net.minecraft.client.render.RenderLayer;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;

public class StevesCartsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutoutMipped(), StevesCartsBlocks.ADVANCED_DETECTOR_RAIL, StevesCartsBlocks.JUNCTION_RAIL);
	}
}
