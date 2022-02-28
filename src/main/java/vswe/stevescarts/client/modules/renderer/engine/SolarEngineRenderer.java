package vswe.stevescarts.client.modules.renderer.engine;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import vswe.stevescarts.client.modules.model.engine.SolarPanelBaseModel;
import vswe.stevescarts.client.modules.renderer.ModuleRenderer;
import vswe.stevescarts.modules.engine.SolarEngineModule;

public class SolarEngineRenderer extends ModuleRenderer<SolarEngineModule> {
	private final SolarPanelBaseModel base;

	public SolarEngineRenderer(Identifier baseTexture) {
		this.base = new SolarPanelBaseModel(baseTexture);
	}

	@Override
	public void render(SolarEngineModule module, float entityYaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int entityLight) {
		this.base.animateModel(module, 0, 0, tickDelta);
		this.base.render(matrices, vertexConsumers, entityLight, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
	}
}
