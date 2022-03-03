package vswe.stevescarts.client.modules.renderer.hull;

import java.util.Objects;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

import vswe.stevescarts.client.modules.model.hull.HullTopModel;
import vswe.stevescarts.modules.hull.HullModule;

public class PumpkinChariotRenderer extends HullRenderer<HullModule> {
	private final HullTopModel altHullTop;

	public PumpkinChariotRenderer(Identifier texture, Identifier topTexture, Identifier altTopTexture) {
		super(texture, topTexture);
		this.altHullTop = new HullTopModel(altTopTexture);
	}

	@Override
	public void render(HullModule module, float entityYaw, float entityPitch, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int entityLight) {
		this.model.render(matrices, vertexConsumers, entityLight, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
		boolean daytime = MinecraftClient.getInstance().world.getTime() % 24000L < 12000L;
		if (Objects.nonNull(module.getMinecart()) && module.getMinecart().shouldRenderTop()) {
			HullTopModel modelToRender = daytime ? this.altHullTop : this.topModel;
			modelToRender.render(matrices, vertexConsumers, entityLight, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
		}
	}
}
