package vswe.stevescarts.client.render.module.attachment;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Util;
import net.minecraft.util.math.RotationAxis;
import vswe.stevescarts.client.render.module.ModuleRenderer;
import vswe.stevescarts.module.attachment.RailerModule;

import java.util.Map;

public class RailerRenderer extends ModuleRenderer<RailerModule> {
	private static BiMap<Item, Block> BLOCK_ITEMS;

	public RailerRenderer() {
		BLOCK_ITEMS = Util.make(HashBiMap.create(), (map) -> {
			for (Map.Entry<Block, Item> entry : Item.BLOCK_ITEMS.entrySet()) {
				map.put(entry.getValue(), entry.getKey());
			}
		});
	}

	@Override
	public void render(RailerModule module, float entityYaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int entityLight) {
		ItemStack first = module.getFirst();
		if (first.isEmpty()) {
			return;
		}
		BlockState state = BLOCK_ITEMS.get(first.getItem()).getDefaultState();
		matrices.push();
		matrices.translate(-0.5, -0.3, -0.5);
		matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(3.1415927f));
		matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(1.5707964f));
		MinecraftClient.getInstance().getBlockRenderManager().renderBlockAsEntity(state, matrices, vertexConsumers, entityLight, OverlayTexture.DEFAULT_UV);
		matrices.pop();
	}
}
