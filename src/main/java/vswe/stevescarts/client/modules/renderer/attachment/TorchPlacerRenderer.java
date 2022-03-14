package vswe.stevescarts.client.modules.renderer.attachment;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;

import vswe.stevescarts.client.modules.model.attachment.TorchPlacerModel;
import vswe.stevescarts.client.modules.renderer.ModuleRenderer;
import vswe.stevescarts.modules.attachment.TorchPlacerModule;

public class TorchPlacerRenderer extends ModuleRenderer<TorchPlacerModule> {
	private final TorchPlacerModel model;

	public TorchPlacerRenderer(Identifier texture) {
		this.model = new TorchPlacerModel(texture);
	}

	@Override
	public void render(TorchPlacerModule module, float entityYaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int entityLight) {
		this.renderSide(module, tickDelta, matrices, vertexConsumers, entityLight, false);
		this.renderSide(module, tickDelta, matrices, vertexConsumers, entityLight, true);
	}

	public void renderSide(TorchPlacerModule module, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int entityLight, boolean opposite) {
		this.model.animateModel(module, 0, 0, tickDelta);
		if (opposite) this.model.getRoot().yaw = 3.1415927F; // a bit of a hack, but it works
		this.model.render(matrices, vertexConsumers, entityLight, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
		if (opposite) this.model.getRoot().yaw = 0.0f;
		for (int i = -1; i <= 1; ++i) {
			ItemStack stack = module.getItem(i + 1);
			if (stack.isEmpty()) {
				continue;
			}
			matrices.push();
			matrices.translate(i * 0.25f, -0.125f, -0.72f);
			matrices.multiply(Vec3f.POSITIVE_X.getRadialQuaternion(3.1415927f));
			if (opposite) {
				matrices.translate(0, 0, -1.2);
			}
			this.getItemRenderer().renderItem(null, stack, ModelTransformation.Mode.FIXED, false, matrices, vertexConsumers, module.getMinecart().world, entityLight, OverlayTexture.DEFAULT_UV, module.getId());
			matrices.pop();
		}
	}
}
