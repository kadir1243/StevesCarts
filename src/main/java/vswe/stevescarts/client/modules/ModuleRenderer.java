package vswe.stevescarts.client.modules;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import vswe.stevescarts.client.StevesCartsClient;
import vswe.stevescarts.modules.MinecartModule;

public abstract class ModuleRenderer<T extends MinecartModule> {
	public ModuleRenderer() {
	}

	public abstract void render(T module, float entityYaw, float entityPitch, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int entityLight);

	public abstract void getTexture(T module);

	public ModuleRenderDispatcher getDispatcher() {
		return StevesCartsClient.getModuleRenderDispatcher();
	}

	public TextRenderer getTextRenderer() {
		return this.getDispatcher().getTextRenderer();
	}

	public ItemRenderer getItemRenderer() {
		return this.getDispatcher().getItemRenderer();
	}
}
