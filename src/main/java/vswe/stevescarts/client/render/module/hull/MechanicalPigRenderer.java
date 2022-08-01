package vswe.stevescarts.client.render.module.hull;

import vswe.stevescarts.client.render.module.model.hull.PigHeadModel;
import vswe.stevescarts.client.render.module.model.hull.PigTailModel;
import vswe.stevescarts.module.hull.HullModule;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class MechanicalPigRenderer extends HullRenderer<HullModule> {
	private final PigHeadModel headModel;
	private final PigTailModel tailModel;

	public MechanicalPigRenderer(Identifier texture, Identifier topTexture, Identifier headTexture, Identifier tailTexture) {
		super(texture, topTexture);
		this.headModel = new PigHeadModel(headTexture);
		this.tailModel = new PigTailModel(tailTexture);
	}

	@Override
	public void render(HullModule module, float entityYaw, float entityPitch, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int entityLight) {
		super.render(module, entityYaw, entityPitch, matrices, vertexConsumers, entityLight);
		this.headModel.render(matrices, vertexConsumers, entityLight, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
		this.tailModel.render(matrices, vertexConsumers, entityLight, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
	}
}
