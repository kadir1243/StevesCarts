package vswe.stevescarts.client.render.module;

import java.util.HashMap;
import java.util.Map;

import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.module.CartModule;
import vswe.stevescarts.module.ModuleType;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;

@Environment(EnvType.CLIENT)
public class ModuleRenderDispatcher implements SimpleSynchronousResourceReloadListener {
	public final Map<ModuleType<?>, ModuleRenderer<? extends CartModule>> renderers = new HashMap<>();
	private final TextRenderer textRenderer;
	private final ItemRenderer itemRenderer;

	public ModuleRenderDispatcher(TextRenderer textRenderer, ItemRenderer itemRenderer) {
		this.textRenderer = textRenderer;
		this.itemRenderer = itemRenderer;
	}

	public <T extends CartModule> void render(T module, float entityYaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int entityLight) {
		if (module.getType().hasRenderer()) {
			Profiler profiler = MinecraftClient.getInstance().getProfiler();
			profiler.push("module." + module.getType().toString());

			//noinspection unchecked
			ModuleRenderer<T> renderer = (ModuleRenderer<T>) renderers.get(module.getType());
			if (renderer == null) {
				throw new NullPointerException("No renderer registered for module " + module.getType().toString());
			}

			matrices.push();
			renderer.render(module, entityYaw, tickDelta, matrices, vertexConsumers, entityLight);
			matrices.pop();

			profiler.pop();
		}
	}

	public ItemRenderer getItemRenderer() {
		return itemRenderer;
	}

	public TextRenderer getTextRenderer() {
		return textRenderer;
	}

	public void register(ModuleType<?> type, ModuleRenderer<? extends CartModule> renderer) {
		renderers.put(type, renderer);
	}

	public void reload() {
		this.renderers.clear();

		// TODO

		StevesCarts.LOGGER.info("Registered " + renderers.size() + " module renderers");
	}

	@Override
	public Identifier getFabricId() {
		return StevesCarts.id("module_renderers");
	}

	@Override
	public void reload(ResourceManager manager) {
		this.reload();
	}
}
