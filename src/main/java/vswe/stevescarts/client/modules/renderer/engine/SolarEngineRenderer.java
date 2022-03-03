package vswe.stevescarts.client.modules.renderer.engine;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

import vswe.stevescarts.client.modules.model.engine.SolarPanelBaseModel;
import vswe.stevescarts.client.modules.model.engine.TopSolarPanelsModel;
import vswe.stevescarts.client.modules.renderer.ModuleRenderer;
import vswe.stevescarts.modules.engine.SolarEngineModule;

public class SolarEngineRenderer extends ModuleRenderer<SolarEngineModule> {
	private final SolarPanelBaseModel base;
	private final Identifier idleTexture;
	private final TopSolarPanelsModel top;

	public SolarEngineRenderer(Identifier baseTexture, Identifier idleTexture, Identifier activeTexture, int panelCount) {
		this.base = new SolarPanelBaseModel(baseTexture);
		this.idleTexture = idleTexture;
		this.top = new TopSolarPanelsModel(activeTexture, panelCount);
	}

	@Override
	public void render(SolarEngineModule module, float entityYaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int entityLight) {
		this.base.animateModel(module, 0, 0, tickDelta);
		this.top.animateModel(module, 0, 0, tickDelta);
		this.base.render(matrices, vertexConsumers, entityLight, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
		if (module.getSkyLight() > 5 && module.shouldShowActive()) {
			this.top.render(matrices, vertexConsumers, entityLight, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
		} else {
			VertexConsumer vertexConsumer = vertexConsumers.getBuffer(this.top.getLayer(this.idleTexture));
			this.top.render(matrices, vertexConsumer, entityLight, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
		}
	}
}
