package vswe.stevescarts.client.modules.renderer.storage;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import vswe.stevescarts.client.modules.model.storage.FrontTankModel;
import vswe.stevescarts.client.modules.model.storage.SideTanksModel;
import vswe.stevescarts.client.modules.renderer.FluidRenderUtil;
import vswe.stevescarts.client.modules.renderer.ModuleRenderer;
import vswe.stevescarts.modules.storage.tank.TankModule;

public class SideTanksRenderer extends ModuleRenderer<TankModule> {
	private final SideTanksModel model;

	public SideTanksRenderer(Identifier texture) {
		this.model = new SideTanksModel(texture);
	}

	@Override
	public void render(TankModule module, float entityYaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int entityLight) {
		this.model.render(matrices, vertexConsumers, entityLight, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
		FluidRenderUtil.renderFluidCuboid(matrices, module.getTank(), -2.0f, -0.5f, -11.0f, 10.0f, 4.0f, 4.0f);
		FluidRenderUtil.renderFluidCuboid(matrices, module.getTank(), -2.0f, -0.5f, 11.0f, 10.0f, 4.0f, 4.0f);
	}
}
