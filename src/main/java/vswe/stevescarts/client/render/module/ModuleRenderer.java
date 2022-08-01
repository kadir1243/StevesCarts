package vswe.stevescarts.client.render.module;

import vswe.stevescarts.module.CartModule;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public abstract class ModuleRenderer<T extends CartModule> {
	public ModuleRenderer() {
	}

	public abstract void render(T module, float entityYaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int entityLight);
}
