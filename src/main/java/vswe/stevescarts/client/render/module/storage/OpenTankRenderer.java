package vswe.stevescarts.client.render.module.storage;

import vswe.stevescarts.client.render.module.ModuleRenderer;
import vswe.stevescarts.client.render.module.model.storage.OpenTankModel;
import vswe.stevescarts.module.storage.TankModule;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class OpenTankRenderer extends ModuleRenderer<TankModule> {
	private final OpenTankModel model;

	public OpenTankRenderer(Identifier texture) {
		this.model = new OpenTankModel(texture);
	}

	@Override
	public void render(TankModule module, float entityYaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int entityLight) {
		matrices.push();
		matrices.scale(1.001F, 1.0011F, 1.001F);
		this.model.render(matrices, vertexConsumers, entityLight, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
		matrices.pop();
		FluidRenderUtil.renderFluidCuboid(matrices, module.getTank(), -7.0f, -11.5f, -5.0f, 14.0f, 5.0f, 10.0f);
	}
}
