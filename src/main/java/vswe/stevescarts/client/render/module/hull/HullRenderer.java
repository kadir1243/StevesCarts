package vswe.stevescarts.client.render.module.hull;

import java.util.Objects;

import vswe.stevescarts.client.render.module.ModuleRenderer;
import vswe.stevescarts.client.render.module.model.hull.HullModel;
import vswe.stevescarts.client.render.module.model.hull.HullTopModel;
import vswe.stevescarts.module.hull.HullModule;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class HullRenderer<T extends HullModule> extends ModuleRenderer<T> {
	private final HullModel model;
	private final HullTopModel topModel;

	public HullRenderer(Identifier texture, Identifier topTexture) {
		this.model = new HullModel(texture);
		this.topModel = new HullTopModel(topTexture);
	}

	@Override
	public void render(T module, float entityYaw, float entityPitch, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int entityLight) {
		this.model.render(matrices, vertexConsumers, entityLight, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
		if (Objects.nonNull(module.getEntity()) && module.getEntity().shouldRenderTop()) {
			this.topModel.render(matrices, vertexConsumers, entityLight, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
		}
	}
}
