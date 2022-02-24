package vswe.stevescarts.client.modules.renderer.engine;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import vswe.stevescarts.client.modules.model.engine.CoalEngineFrameModel;
import vswe.stevescarts.client.modules.model.engine.CoalEngineInsideModel;
import vswe.stevescarts.client.modules.renderer.ModuleRenderer;
import vswe.stevescarts.modules.engine.CoalEngineModule;

public class CoalEngineRenderer extends ModuleRenderer<CoalEngineModule> {
	private final CoalEngineFrameModel frameModel;
	private final CoalEngineInsideModel insideModel;

	public CoalEngineRenderer(Identifier frameTexture, Identifier[] fireTextures, Identifier otherTexture) {
		this.frameModel = new CoalEngineFrameModel(frameTexture);
		this.insideModel = new CoalEngineInsideModel(fireTextures, otherTexture);
	}

	@Override
	public void render(CoalEngineModule module, float entityYaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int entityLight) {
		this.frameModel.render(matrices, vertexConsumers, entityLight, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
		this.insideModel.render(module.getFireIndex(), matrices, vertexConsumers, entityLight, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
	}
}
