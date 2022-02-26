package vswe.stevescarts.client.modules.renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.profiler.Profiler;
import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.client.modules.model.addon.LeverModel;
import vswe.stevescarts.client.modules.model.attachment.SeatModel;
import vswe.stevescarts.client.modules.model.engine.CoalEngineInsideModel;
import vswe.stevescarts.client.modules.model.storage.ExtractingChestsModel;
import vswe.stevescarts.client.modules.model.storage.FrontChestModel;
import vswe.stevescarts.client.modules.model.storage.TopChestModel;
import vswe.stevescarts.client.modules.renderer.engine.CoalEngineRenderer;
import vswe.stevescarts.client.modules.renderer.hull.HullRenderer;
import vswe.stevescarts.client.modules.renderer.hull.MechanicalPigRenderer;
import vswe.stevescarts.client.modules.renderer.storage.FrontTankRenderer;
import vswe.stevescarts.client.modules.renderer.storage.OpenTankRenderer;
import vswe.stevescarts.client.modules.renderer.storage.SideChestsRenderer;
import vswe.stevescarts.client.modules.renderer.storage.SideTanksRenderer;
import vswe.stevescarts.client.modules.renderer.storage.TopTankRenderer;
import vswe.stevescarts.modules.MinecartModule;
import vswe.stevescarts.modules.MinecartModuleType;
import vswe.stevescarts.modules.StevesCartsModuleTypes;

import java.util.HashMap;
import java.util.Map;

import static vswe.stevescarts.StevesCarts.id;

@Environment(EnvType.CLIENT)
public class ModuleRenderDispatcher implements SimpleSynchronousResourceReloadListener {
	public final Map<MinecartModuleType<?>, ModuleRenderer<? extends MinecartModule>> renderers = new HashMap<>();
	private final TextRenderer textRenderer;
	private final ItemRenderer itemRenderer;

	public ModuleRenderDispatcher(TextRenderer textRenderer, ItemRenderer itemRenderer) {
		this.textRenderer = textRenderer;
		this.itemRenderer = itemRenderer;
	}

	/**
	 * Renders the specified module.
	 *
	 * @param module          The module to render.
	 * @param entityYaw       The yaw of the entity.
	 * @param tickDelta       The time delta between ticks.
	 * @param matrices        The matrix stack.
	 * @param vertexConsumers The vertex consumer provider.
	 * @param entityLight     The light level of the entity.
	 * @param profile         Whether to profile the rendering.
	 * @param <T>             The module type.
	 */
	public <T extends MinecartModule> void render(T module, float entityYaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int entityLight, boolean profile) {
		if (module.getType().hasRenderer()) {
			Profiler profiler = MinecraftClient.getInstance().getProfiler();
			if (profile) profiler.push("module." + module.getType().toString());

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

			if (profile) profiler.pop();
		}
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

	public void reload() {
		this.renderers.clear();
		register(StevesCartsModuleTypes.WOODEN_HULL, new HullRenderer<>(id("textures/modules/hull/wooden_hull.png"), id("textures/modules/hull/wooden_hull_top.png")));
		register(StevesCartsModuleTypes.STANDARD_HULL, new HullRenderer<>(id("textures/modules/hull/standard_hull.png"), id("textures/modules/hull/standard_hull_top.png")));
		register(StevesCartsModuleTypes.REINFORCED_HULL, new HullRenderer<>(id("textures/modules/hull/reinforced_hull.png"), id("textures/modules/hull/reinforced_hull_top.png")));
		register(StevesCartsModuleTypes.MECHANICAL_PIG, new MechanicalPigRenderer(id("textures/modules/hull/mechanical_pig.png"), id("textures/modules/hull/mechanical_pig_top.png"), new Identifier("textures/entity/pig/pig.png"), id("textures/modules/hull/pig_tail.png")));
		register(StevesCartsModuleTypes.GALGADORIAN_HULL, new HullRenderer<>(id("textures/modules/hull/galgadorian_hull.png"), id("textures/modules/hull/galgadorian_hull_top.png")));
		register(StevesCartsModuleTypes.CREATIVE_HULL, new HullRenderer<>(id("textures/modules/hull/creative_hull.png"), id("textures/modules/hull/creative_hull_top.png")));

		register(StevesCartsModuleTypes.FRONT_CHEST, new GenericRenderer(id("textures/modules/storage/front_chest.png"), FrontChestModel::new));
		register(StevesCartsModuleTypes.TOP_CHEST, new GenericRenderer(id("textures/modules/storage/top_chest.png"), TopChestModel::new));
		register(StevesCartsModuleTypes.SIDE_CHESTS, new SideChestsRenderer(id("textures/modules/storage/side_chests.png")));
		register(StevesCartsModuleTypes.FRONT_TANK, new FrontTankRenderer(id("textures/modules/storage/front_tank.png")));
		register(StevesCartsModuleTypes.TOP_TANK, new TopTankRenderer(id("textures/modules/storage/top_tank.png")));
		register(StevesCartsModuleTypes.SIDE_TANKS, new SideTanksRenderer(id("textures/modules/storage/side_tanks.png")));
		register(StevesCartsModuleTypes.OPEN_TANK, new OpenTankRenderer(id("textures/modules/storage/top_tank.png")));
		register(StevesCartsModuleTypes.EXTRACTING_CHESTS, new GenericRenderer(id("textures/modules/storage/extracting_chests.png"), ExtractingChestsModel::new));

		register(StevesCartsModuleTypes.SEAT, new GenericRenderer(id("textures/modules/attachment/seat.png"), SeatModel::new));

		register(StevesCartsModuleTypes.COAL_ENGINE, new CoalEngineRenderer(id("textures/modules/engine/engine_frame.png"), CoalEngineInsideModel.FIRE_TEXTURES, id("textures/modules/engine/engine_back.png")));
		register(StevesCartsModuleTypes.TINY_COAL_ENGINE, new CoalEngineRenderer(id("textures/modules/engine/engine_frame.png"), CoalEngineInsideModel.FIRE_TEXTURES, id("textures/modules/engine/engine_back.png")));

		register(StevesCartsModuleTypes.BRAKE, new GenericRenderer(id("textures/modules/addon/red_lever.png"), LeverModel::new));

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
