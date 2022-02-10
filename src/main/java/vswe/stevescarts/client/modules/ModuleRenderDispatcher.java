package vswe.stevescarts.client.modules;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3d;
import vswe.stevescarts.modules.MinecartModule;
import vswe.stevescarts.modules.MinecartModuleType;

import java.util.HashMap;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class ModuleRenderDispatcher {
	private final TextRenderer textRenderer;
	private final ItemRenderer itemRenderer;
	public final Map<MinecartModuleType<?>, ModuleRenderer<? extends MinecartModule>> RENDERERS = new HashMap<>();

	public ModuleRenderDispatcher(TextRenderer textRenderer, ItemRenderer itemRenderer) {
		this.textRenderer = textRenderer;
		this.itemRenderer = itemRenderer;
	}

	public <T extends MinecartModule> void render(T module, float entityYaw, float entityPitch, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int entityLight) {
		//noinspection unchecked
		ModuleRenderer<T> renderer = (ModuleRenderer<T>) RENDERERS.get(module.getType());
		if (renderer == null) {
			throw new NullPointerException("No renderer for module " + module.getType().toString());
		}
		Vec3d offset = module.getPositionOffset();
		matrices.push();
		matrices.translate(offset.x, offset.y, offset.z);
		renderer.render(module, entityYaw, entityPitch, matrices, vertexConsumers, entityLight);
		matrices.pop();
	}

	public ItemRenderer getItemRenderer() {
		return itemRenderer;
	}

	public TextRenderer getTextRenderer() {
		return textRenderer;
	}
}
