package vswe.stevescarts.client.modules.renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import vswe.stevescarts.client.modules.model.hull.HullModel;
import vswe.stevescarts.client.modules.model.hull.HullTopModel;
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
			this.topModel.render(matrices, topConsumer, entityLight, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
		}
	}
}
