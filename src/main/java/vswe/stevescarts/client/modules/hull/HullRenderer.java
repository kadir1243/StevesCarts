package vswe.stevescarts.client.modules.hull;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import vswe.stevescarts.client.modules.ModuleRenderer;
import vswe.stevescarts.modules.hull.HullModule;

public abstract class HullRenderer<T extends HullModule> extends ModuleRenderer<T> {
	@Override
	public void render(T module, float entityYaw, float entityPitch, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int entityLight) {

	}
}
