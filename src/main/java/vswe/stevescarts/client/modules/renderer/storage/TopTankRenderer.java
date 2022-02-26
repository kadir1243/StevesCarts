package vswe.stevescarts.client.modules.renderer.storage;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import vswe.stevescarts.client.modules.model.storage.TopTankModel;
import vswe.stevescarts.client.modules.renderer.FluidRenderUtil;
import vswe.stevescarts.client.modules.renderer.ModuleRenderer;
import vswe.stevescarts.modules.storage.TankModule;

public class TopTankRenderer extends ModuleRenderer<TankModule> {
	private final TopTankModel model;

	public TopTankRenderer(Identifier texture) {
		this.model = new TopTankModel(texture);
	}

	@Override
	public void render(TankModule module, float entityYaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int entityLight) {
		matrices.push();
		matrices.scale(1.001F, 1.001F, 1.001F); // Fixes z-fighting
		this.model.render(matrices, vertexConsumers, entityLight, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
		matrices.pop();
		FluidRenderUtil.renderFluidCuboid(matrices, module.getTank(), -7.0f, -11.5f, -5.0f, 14.0f, 5.0f, 10.0f);
	}
}
