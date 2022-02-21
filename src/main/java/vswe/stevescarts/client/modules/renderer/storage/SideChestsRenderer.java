package vswe.stevescarts.client.modules.renderer.storage;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import vswe.stevescarts.client.modules.model.storage.SideChestsModel;
import vswe.stevescarts.client.modules.renderer.ModuleRenderer;
import vswe.stevescarts.modules.storage.chest.SideChestsModule;

public class SideChestsRenderer extends ModuleRenderer<SideChestsModule> {
	private final SideChestsModel side;
	private final SideChestsModel oppositeSide;

	public SideChestsRenderer(Identifier texture) {
		this.side = new SideChestsModel(texture);
		this.oppositeSide = new SideChestsModel(texture);
		this.oppositeSide.getRoot().setAngles(0, 3.1415927f, 0);
	}

	@Override
	public void render(SideChestsModule module, float entityYaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int entityLight) {
		this.side.animateModel(module, 0, 0, tickDelta);
		this.side.render(matrices, vertexConsumers, entityLight, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
		this.oppositeSide.animateModel(module, 0, 0, tickDelta);
		this.oppositeSide.render(matrices, vertexConsumers, entityLight, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
	}
}
