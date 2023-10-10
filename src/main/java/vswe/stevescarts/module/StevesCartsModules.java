package vswe.stevescarts.module;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import reborncore.common.fluid.FluidValue;
import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.entity.CartEntity;
import vswe.stevescarts.module.addon.BrakeModule;
import vswe.stevescarts.module.addon.InvisibilityModule;
import vswe.stevescarts.module.attachment.*;
import vswe.stevescarts.module.engine.AdvancedThermalEngineModule;
import vswe.stevescarts.module.engine.CoalEngineModule;
import vswe.stevescarts.module.engine.SolarEngineModule;
import vswe.stevescarts.module.engine.ThermalEngineModule;
import vswe.stevescarts.module.hull.HullData;
import vswe.stevescarts.module.hull.HullModule;
import vswe.stevescarts.module.hull.HullModuleType;
import vswe.stevescarts.module.storage.ChestModule;
import vswe.stevescarts.module.storage.ExtractingChestsModule;
import vswe.stevescarts.module.storage.TankModule;

import java.util.EnumSet;
import java.util.function.BiFunction;

public class StevesCartsModules {
	public static final HullModuleType<HullModule> WOODEN_HULL = registerHull("wooden_hull", HullModule::new, new HullData(50, 1, 0, 15));
	public static final HullModuleType<HullModule> STANDARD_HULL = registerHull("standard_hull", HullModule::new, new HullData(200, 3, 6, 50));
	public static final HullModuleType<HullModule> REINFORCED_HULL = registerHull("reinforced_hull", HullModule::new, new HullData(500, 5, 12, 150));
	public static final HullModuleType<HullModule> MECHANICAL_PIG = registerHull("mechanical_pig", HullModule::new, EnumSet.of(ModuleSide.FRONT), new HullData(150, 4, 2, 50));
	public static final HullModuleType<HullModule> CREATIVE_HULL = registerHull("creative_hull", HullModule::new, new HullData(10000, 5, 12, 150));
	public static final HullModuleType<HullModule> GALGADORIAN_HULL = registerHull("galgadorian_hull", HullModule::new, new HullData(1000, 5, 12, 150));

	public static final ModuleType<ChestModule> FRONT_CHEST = registerRegularChest("front_chest", (entity, type) -> new ChestModule(entity, type, 4, 3), EnumSet.of(ModuleSide.FRONT), 5);
	public static final ModuleType<ChestModule> TOP_CHEST = registerRegularChest("top_chest", (entity, type) -> new ChestModule(entity, type, 6, 3), EnumSet.of(ModuleSide.TOP), 5);
	public static final ModuleType<ChestModule> SIDE_CHESTS = registerRegularChest("side_chests", (entity, type) -> new ChestModule(entity, type, 5, 3), EnumSet.of(ModuleSide.LEFT, ModuleSide.RIGHT), 3);
	public static final ModuleType<ChestModule> EXTRACTING_CHESTS = registerRegularChest("extracting_chests", ExtractingChestsModule::new, EnumSet.of(ModuleSide.LEFT, ModuleSide.RIGHT, ModuleSide.CENTER), 75);
	public static final ModuleType<TankModule> TOP_TANK = registerRegularTank("top_tank", 14, EnumSet.of(ModuleSide.TOP), 22, false);
	public static final ModuleType<TankModule> FRONT_TANK = registerRegularTank("front_tank", 8, EnumSet.of(ModuleSide.FRONT), 15, false);
	public static final ModuleType<TankModule> SIDE_TANKS = registerRegularTank("side_tanks", 8, EnumSet.of(ModuleSide.LEFT, ModuleSide.RIGHT), 10, false);
	public static final ModuleType<TankModule> OPEN_TANK = registerRegularTank("open_tank", 14, EnumSet.of(ModuleSide.TOP), 22, false);
	public static final ModuleType<TankModule> ADVANCED_TANK = registerRegularTank("advanced_tank", 32, EnumSet.of(ModuleSide.CENTER, ModuleSide.TOP), 54, true);

	public static final ModuleType<CoalEngineModule> TINY_COAL_ENGINE = registerCoalEngine("tiny_coal_engine", 1, 0.5f, 2, ModuleTags.INCOMPATIBLE_WITH_TINY_COAL_ENGINE);
	public static final ModuleType<CoalEngineModule> COAL_ENGINE = registerCoalEngine("coal_engine", 3, 2.25f, 15, ModuleTags.INCOMPATIBLE_WITH_COAL_ENGINE);
	public static final ModuleType<SolarEngineModule> SOLAR_ENGINE = registerSolarEngine("solar_engine", 12, 100000L);
	public static final ModuleType<SolarEngineModule> ADVANCED_SOLAR_ENGINE = registerSolarEngine("advanced_solar_engine", 20, 200000L);
	public static final ModuleType<ThermalEngineModule> THERMAL_ENGINE = registerThermalEngine("thermal_engine", ThermalEngineModule::new, 28, 1, ModuleTags.INCOMPATIBLE_WITH_THERMAL_ENGINE);
	public static final ModuleType<ThermalEngineModule> ADVANCED_THERMAL_ENGINE = registerThermalEngine("advanced_thermal_engine", AdvancedThermalEngineModule::new, 58, 2, ModuleTags.INCOMPATIBLE_WITH_ADVANCED_THERMAL_ENGINE);

	public static final ModuleType<SeatModule> SEAT = register("seat", new ModuleType<>(SeatModule::new, StevesCarts.id("seat"), 3, EnumSet.of(ModuleSide.CENTER, ModuleSide.TOP), ModuleGroup.ATTACHMENT, true, false, true, null, null));
	public static final ModuleType<FireworkDisplayModule> FIREWORK_DISPLAY = register("firework_display", new ModuleType<>(FireworkDisplayModule::new, StevesCarts.id("firework_display"), 45, EnumSet.noneOf(ModuleSide.class), ModuleGroup.ATTACHMENT, false, false, false, null, null));
	public static final ModuleType<TorchPlacerModule> TORCH_PLACER = register("torch_placer", new ModuleType<>(TorchPlacerModule::new, StevesCarts.id("torch_placer"), 14, EnumSet.of(ModuleSide.LEFT, ModuleSide.RIGHT), ModuleGroup.ATTACHMENT, true, false, false, null, null));
	public static final ModuleType<RailerModule> RAILER = register("railer", new ModuleType<>((minecart, type) -> new RailerModule(minecart, type, 1), StevesCarts.id("railer"), 3, EnumSet.of(ModuleSide.TOP), ModuleGroup.ATTACHMENT, true, false, false, null, null));
	public static final ModuleType<RailerModule> LARGE_RAILER = register("large_railer", new ModuleType<>((minecart, type) -> new RailerModule(minecart, type, 2), StevesCarts.id("large_railer"), 9, EnumSet.of(ModuleSide.TOP), ModuleGroup.ATTACHMENT, true, false, false, null, null));
	public static final ModuleType<BridgeBuilderModule> BRIDGE_BUILDER = register("bridge_builder", new ModuleType<>(BridgeBuilderModule::new, StevesCarts.id("bridge_builder"), 12, EnumSet.of(ModuleSide.FRONT), ModuleGroup.ATTACHMENT, true, false, false, null, null));
	public static final ModuleType<HydratorModule> HYDRATOR = register("hydrator", new ModuleType<>(HydratorModule::new, StevesCarts.id("hydrator"), 6, EnumSet.noneOf(ModuleSide.class), ModuleGroup.ATTACHMENT, false, false, false, null, Util.make(new Object2IntOpenHashMap<>(), m -> m.put(ModuleTags.TANKS, 1))));

	public static final ModuleType<BrakeModule> BRAKE = register("brake", new ModuleType<>(BrakeModule::new, StevesCarts.id("brake"), 12, EnumSet.of(ModuleSide.RIGHT), ModuleGroup.ADDON, true, false, false, null, null));
	public static final ModuleType<InvisibilityModule> INVISIBILITY_CORE = register("invisibility_core", new ModuleType<>(InvisibilityModule::new, StevesCarts.id("invisibility_core"), 21, EnumSet.noneOf(ModuleSide.class), ModuleGroup.ADDON, false, false, false, null, null));

	public static void init() {
		ModuleTags.init();
	}

	private static <T extends CartModule> ModuleType<T> register(String name, ModuleType<T> module) {
		Identifier id = StevesCarts.id(name);
		return Registry.register(ModuleType.REGISTRY, id, module);
	}

	private static ModuleType<ThermalEngineModule> registerThermalEngine(String name, BiFunction<CartEntity, ModuleType<ThermalEngineModule>, ThermalEngineModule> factory, int cost, int tanks, TagKey<ModuleType<?>> incompat) {
		Object2IntMap<TagKey<ModuleType<?>>> reqs = new Object2IntOpenHashMap<>();
		reqs.put(ModuleTags.TANKS, tanks);
		return register(name, new ModuleType<>(factory, StevesCarts.id(name), cost, EnumSet.noneOf(ModuleSide.class), ModuleGroup.ENGINE, false, false, false, incompat, reqs));
	}

	private static ModuleType<CoalEngineModule> registerCoalEngine(String name, int fuelSlots, float multiplier, int cost, TagKey<ModuleType<?>> incompatibilities) {
		Identifier id = StevesCarts.id(name);
		return Registry.register(ModuleType.REGISTRY, id, new ModuleType<>(((cartEntity, moduleType) -> new CoalEngineModule(cartEntity, moduleType, fuelSlots, multiplier)), id, cost, EnumSet.of(ModuleSide.BACK), ModuleGroup.ENGINE, true, false, false, incompatibilities, null));
	}

	private static ModuleType<SolarEngineModule> registerSolarEngine(String name, int cost, long maxPower) {
		Identifier id = StevesCarts.id(name);
		return Registry.register(ModuleType.REGISTRY, id, new ModuleType<>(((cartEntity, moduleType) -> new SolarEngineModule(cartEntity, moduleType, maxPower)), id, cost, EnumSet.of(ModuleSide.CENTER, ModuleSide.TOP), ModuleGroup.ENGINE, true, false, true, null, null));
	}

	private static <T extends HullModule> HullModuleType<T> registerHull(String name, BiFunction<CartEntity, ModuleType<T>, T> factory, EnumSet<ModuleSide> sides, HullData hullData) {
		Identifier id = StevesCarts.id(name);
		return Registry.register(ModuleType.REGISTRY, id, new HullModuleType<>(factory, id, sides, hullData));
	}

	private static <T extends HullModule> HullModuleType<T> registerHull(String name, BiFunction<CartEntity, ModuleType<T>, T> factory, HullData hullData) {
		return registerHull(name, factory, EnumSet.noneOf(ModuleSide.class), hullData);
	}

	private static <T extends ChestModule> ModuleType<T> registerRegularChest(String name, BiFunction<CartEntity, ModuleType<T>, T> factory, EnumSet<ModuleSide> sides, int cost) {
		Identifier id = StevesCarts.id(name);
		return Registry.register(ModuleType.REGISTRY, id, new ModuleType<>(factory, id, cost, sides, ModuleGroup.STORAGE, true, false, false, null, null));
	}

	private static ModuleType<TankModule> registerRegularTank(String name, int buckets, EnumSet<ModuleSide> sides, int moduleCost, boolean noHullTop) {
		Identifier id = StevesCarts.id(name);
		return Registry.register(ModuleType.REGISTRY, id, new ModuleType<>((entity, type) -> new TankModule(entity, type, FluidValue.BUCKET.multiply(buckets)), id, moduleCost, sides, ModuleGroup.STORAGE, true, false, noHullTop, null, null));
	}
}
