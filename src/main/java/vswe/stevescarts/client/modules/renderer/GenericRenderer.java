package vswe.stevescarts.client.modules.renderer;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import vswe.stevescarts.client.modules.model.ModuleModel;
import vswe.stevescarts.modules.MinecartModule;

import java.util.function.Function;

public class GenericRenderer extends ModuleRenderer<MinecartModule> {
	private final Identifier texture;
	private final ModuleModel model;

	public GenericRenderer(Identifier texture, Function<Identifier, ModuleModel> modelFactory) {
		this.texture = texture;
		this.model = modelFactory.apply(texture);
	}

	@Override
	public void render(MinecartModule module, float entityYaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int entityLight) {
		VertexConsumer consumer = vertexConsumers.getBuffer(this.model.getLayer(this.texture));
		this.model.render(matrices, consumer, entityLight, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
	}
}
