package vswe.stevescarts.client.modules.hull;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import vswe.stevescarts.client.modules.ModuleRenderer;
import vswe.stevescarts.client.modules.hull.model.HullModel;
import vswe.stevescarts.client.modules.hull.model.HullTopModel;
import vswe.stevescarts.client.modules.model.ModuleModel;
import vswe.stevescarts.modules.hull.HullModule;

import java.util.Objects;

@Environment(EnvType.CLIENT)
public class HullRenderer<T extends HullModule> extends ModuleRenderer<T> {
	private final Identifier texture;
	private final HullModel model;
	private final HullTopModel topModel;
	private final Identifier topTexture;

	public HullRenderer(Identifier texture, Identifier topTexture) {
		this.texture = texture;
		this.topTexture = topTexture;
		this.model = new HullModel(texture);
		this.topModel = new HullTopModel(topTexture);
	}

	@Override
	public void render(T module, float entityYaw, float entityPitch, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int entityLight) {
		VertexConsumer consumer = vertexConsumers.getBuffer(this.model.getLayer(this.texture));
		this.model.render(matrices, consumer, entityLight, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
		if (Objects.requireNonNull(module.getMinecart()).shouldRenderTop()) {
			VertexConsumer topConsumer = vertexConsumers.getBuffer(this.topModel.getLayer(this.topTexture));
			matrices.push();
			this.topModel.render(matrices, topConsumer, entityLight, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
			matrices.pop();
		}
	}
}
