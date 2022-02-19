package vswe.stevescarts.client.modules.renderer;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import vswe.stevescarts.client.modules.model.hull.PigHeadModel;
import vswe.stevescarts.client.modules.model.hull.PigTailModel;
import vswe.stevescarts.modules.hull.HullModule;

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
