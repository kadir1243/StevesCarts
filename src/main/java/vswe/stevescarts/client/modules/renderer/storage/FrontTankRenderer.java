package vswe.stevescarts.client.modules.renderer.storage;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import vswe.stevescarts.client.modules.model.storage.FrontTankModel;
import vswe.stevescarts.client.modules.renderer.FluidRenderUtil;
import vswe.stevescarts.client.modules.renderer.ModuleRenderer;
import vswe.stevescarts.modules.storage.tank.TankModule;

public class FrontTankRenderer extends ModuleRenderer<TankModule> {
	private final FrontTankModel model;

	public FrontTankRenderer(Identifier texture) {
		this.model = new FrontTankModel(texture);
	}

	@Override
	public void render(TankModule module, float entityYaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int entityLight) {
		matrices.push();
		matrices.scale(1.001F, 1.001F, 1.001F); // Fixes z-fighting
		this.model.render(matrices, vertexConsumers, entityLight, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
		matrices.pop();
		FluidRenderUtil.renderFluidCuboid(matrices, module.getTank(), -17.0f, -3.0f, -5.0f, 6.0f, 6.0f, 12.0f);
	}
}
