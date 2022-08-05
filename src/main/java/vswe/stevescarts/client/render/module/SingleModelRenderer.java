package vswe.stevescarts.client.render.module;

import vswe.stevescarts.client.render.module.model.ModuleModel;
import vswe.stevescarts.module.CartModule;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class SingleModelRenderer extends ModuleRenderer<CartModule> {
	private final Identifier texture;
	private final ModuleModel model;

	public SingleModelRenderer(ModuleModel model) {
		this.texture = model.getTexture();
		this.model = model;
	}

	@Override
	public void render(CartModule module, float entityYaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int entityLight) {
		VertexConsumer consumer = vertexConsumers.getBuffer(this.model.getLayer(this.texture));
		if (module.getEntity() != null) {
			this.model.animateModel(module, 0, 0, tickDelta);
		}
		this.model.render(matrices, consumer, entityLight, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
	}
}
