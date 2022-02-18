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
import vswe.stevescarts.client.modules.model.ModuleModel;
import vswe.stevescarts.modules.hull.HullModule;

@Environment(EnvType.CLIENT)
public class HullRenderer<T extends HullModule> extends ModuleRenderer<T> {
	private final Identifier texture;
	private final HullModel model;

	public HullRenderer(Identifier texture) {
		this.texture = texture;
		this.model = new HullModel(texture);
	}

	@Override
	public void render(T module, float entityYaw, float entityPitch, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int entityLight) {
		VertexConsumer consumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCull(this.texture));
		this.model.render(matrices, consumer, entityLight, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
	}
}
