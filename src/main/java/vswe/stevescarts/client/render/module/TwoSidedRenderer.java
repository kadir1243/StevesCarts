package vswe.stevescarts.client.render.module;

import java.util.function.Function;

import vswe.stevescarts.client.render.module.model.ModuleModel;
import vswe.stevescarts.module.CartModule;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class TwoSidedRenderer<T extends CartModule> extends ModuleRenderer<T> {
	private final ModuleModel side;
	private final ModuleModel oppositeSide;

	public TwoSidedRenderer(Identifier texture, Function<Identifier, ModuleModel> modelFactory) {
		this.side = modelFactory.apply(texture);
		this.oppositeSide = modelFactory.apply(texture);
		this.oppositeSide.getRoot().yaw = 3.14159267f;
	}

	@Override
	public void render(T module, float entityYaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int entityLight) {
		this.side.animateModel(module, 0, 0, tickDelta);
		this.oppositeSide.animateModel(module, 0, 0, tickDelta);
		this.side.render(matrices, vertexConsumers, entityLight, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
		this.oppositeSide.render(matrices, vertexConsumers, entityLight, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
	}
}
