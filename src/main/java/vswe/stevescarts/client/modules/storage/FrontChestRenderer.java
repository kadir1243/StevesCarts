package vswe.stevescarts.client.modules.storage;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import vswe.stevescarts.client.modules.ModuleRenderer;
import vswe.stevescarts.client.modules.storage.model.FrontChestModel;
import vswe.stevescarts.modules.storage.chest.FrontChestModule;

public class FrontChestRenderer extends ModuleRenderer<FrontChestModule> {
	private final Identifier texture;
	private final FrontChestModel model;

	public FrontChestRenderer(Identifier texture) {
		this.texture = texture;
		this.model = new FrontChestModel(texture);
	}

	@Override
	public void render(FrontChestModule module, float entityYaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int entityLight) {
		VertexConsumer consumer = vertexConsumers.getBuffer(this.model.getLayer(this.texture));
		this.model.render(matrices, consumer, entityLight, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
	}
}
