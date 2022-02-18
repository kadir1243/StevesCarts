package vswe.stevescarts.client.modules;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3d;
import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.client.modules.hull.HullRenderer;
import vswe.stevescarts.entity.ModularMinecartEntity;
import vswe.stevescarts.modules.MinecartModule;
import vswe.stevescarts.modules.MinecartModuleType;
import vswe.stevescarts.modules.StevesCartsModuleTypes;

import java.util.HashMap;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class ModuleRenderDispatcher {
	private final TextRenderer textRenderer;
	private final ItemRenderer itemRenderer;
	public final Map<MinecartModuleType<?>, ModuleRenderer<? extends MinecartModule>> renderers = new HashMap<>();

	public ModuleRenderDispatcher(TextRenderer textRenderer, ItemRenderer itemRenderer) {
		this.textRenderer = textRenderer;
		this.itemRenderer = itemRenderer;
	}

	public <T extends MinecartModule> void render(T module, float entityYaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int entityLight) {
		//noinspection unchecked
		ModuleRenderer<T> renderer = (ModuleRenderer<T>) renderers.get(module.getType());
		if (renderer == null) {
			throw new NullPointerException("No renderer for module " + module.getType().toString());
		}
		Vec3d offset = module.getPositionOffset();
		matrices.push();
		matrices.translate(offset.x, offset.y, offset.z);
		renderer.render(module, entityYaw, tickDelta, matrices, vertexConsumers, entityLight);
		matrices.pop();
	}

	public ItemRenderer getItemRenderer() {
		return itemRenderer;
	}

	public TextRenderer getTextRenderer() {
		return textRenderer;
	}

	public void register(MinecartModuleType<?> type, ModuleRenderer<? extends MinecartModule> renderer) {
		renderers.put(type, renderer);
	}

	public void init() {
		register(StevesCartsModuleTypes.WOODEN_HULL, new HullRenderer<>(StevesCarts.id("textures/modules/hull/wooden_hull.png")));
		register(StevesCartsModuleTypes.STANDARD_HULL, new HullRenderer<>(StevesCarts.id("textures/modules/hull/standard_hull.png")));
		register(StevesCartsModuleTypes.REINFORCED_HULL, new HullRenderer<>(StevesCarts.id("textures/modules/hull/reinforced_hull.png")));
		register(StevesCartsModuleTypes.MECHANICAL_PIG, new HullRenderer<>(StevesCarts.id("textures/modules/hull/mechanical_pig.png")));
		register(StevesCartsModuleTypes.GALGADORIAN_HULL, new HullRenderer<>(StevesCarts.id("textures/modules/hull/galgadorian_hull.png")));
		register(StevesCartsModuleTypes.CREATIVE_HULL, new HullRenderer<>(StevesCarts.id("textures/modules/hull/creative_hull.png")));
	}
}
