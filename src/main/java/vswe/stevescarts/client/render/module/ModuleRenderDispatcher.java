package vswe.stevescarts.client.render.module;

import java.util.HashMap;
import java.util.Map;

import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.client.render.module.hull.HullRenderer;
import vswe.stevescarts.client.render.module.hull.MechanicalPigRenderer;
import vswe.stevescarts.module.CartModule;
import vswe.stevescarts.module.ModuleStorage;
import vswe.stevescarts.module.ModuleType;
import vswe.stevescarts.module.StevesCartsModules;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;

import static vswe.stevescarts.StevesCarts.id;

@Environment(EnvType.CLIENT)
public class ModuleRenderDispatcher implements SimpleSynchronousResourceReloadListener {
	public final Map<ModuleType<?>, ModuleRenderer<? extends CartModule>> renderers = new HashMap<>();
	private final TextRenderer textRenderer;
	private final ItemRenderer itemRenderer;

	public ModuleRenderDispatcher(TextRenderer textRenderer, ItemRenderer itemRenderer) {
		this.textRenderer = textRenderer;
		this.itemRenderer = itemRenderer;
	}

	public void renderItem(ItemStack stack, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
		for (CartModule module : ModuleStorage.read(stack)) {
			ModuleRenderer<CartModule> renderer = (ModuleRenderer<CartModule>) renderers.get(module.getType());
			if (renderer != null) {
				renderer.render(module, 0, MinecraftClient.getInstance().getTickDelta(), matrices, vertexConsumers, light);
			}
		}
	}

	public <T extends CartModule> void render(T module, float entityYaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int entityLight) {
		if (module.getType().hasRenderer()) {
			Profiler profiler = MinecraftClient.getInstance().getProfiler();
			profiler.push("module." + module.getType());

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

		register(StevesCartsModules.WOODEN_HULL, new HullRenderer<>(id("textures/modules/hull/wooden_hull.png"), id("textures/modules/hull/wooden_hull_top.png")));
		register(StevesCartsModules.STANDARD_HULL, new HullRenderer<>(id("textures/modules/hull/standard_hull.png"), id("textures/modules/hull/standard_hull_top.png")));
		register(StevesCartsModules.REINFORCED_HULL, new HullRenderer<>(id("textures/modules/hull/reinforced_hull.png"), id("textures/modules/hull/reinforced_hull_top.png")));
		register(StevesCartsModules.MECHANICAL_PIG, new MechanicalPigRenderer(id("textures/modules/hull/mechanical_pig.png"), id("textures/modules/hull/mechanical_pig_top.png"), new Identifier("textures/entity/pig/pig.png"), id("textures/modules/hull/pig_tail.png")));
		register(StevesCartsModules.GALGADORIAN_HULL, new HullRenderer<>(id("textures/modules/hull/galgadorian_hull.png"), id("textures/modules/hull/galgadorian_hull_top.png")));
		register(StevesCartsModules.CREATIVE_HULL, new HullRenderer<>(id("textures/modules/hull/creative_hull.png"), id("textures/modules/hull/creative_hull_top.png")));

		StevesCarts.LOGGER.info("Registered " + renderers.size() + " module renderers");
	}

	@Override
	public Identifier getFabricId() {
		return id("module_renderers");
	}

	@Override
	public void reload(ResourceManager manager) {
		this.reload();
	}
}
