package vswe.stevescarts.module;

import java.util.EnumSet;
import java.util.function.BiFunction;

import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.entity.CartEntity;
import vswe.stevescarts.module.hull.HullData;
import vswe.stevescarts.module.hull.HullModule;
import vswe.stevescarts.module.hull.HullModuleType;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class StevesCartsModules {
	public static final HullModuleType<HullModule> WOODEN_HULL = registerHull("wooden_hull", HullModule::new, new HullData(50, 1, 0, 15));
	public static final HullModuleType<HullModule> STANDARD_HULL = registerHull("standard_hull", HullModule::new, new HullData(200, 3, 6, 50));
	public static final HullModuleType<HullModule> REINFORCED_HULL = registerHull("reinforced_hull", HullModule::new, new HullData(500, 5, 12, 150));
	public static final HullModuleType<HullModule> MECHANICAL_PIG = registerHull("mechanical_pig", HullModule::new, EnumSet.of(ModuleSide.FRONT), new HullData(150, 4, 2, 50));
	public static final HullModuleType<HullModule> CREATIVE_HULL = registerHull("creative_hull", HullModule::new, new HullData(10000, 5, 12, 150));
	public static final HullModuleType<HullModule> GALGADORIAN_HULL = registerHull("galgadorian_hull", HullModule::new, new HullData(1000, 5, 12, 150));

	public static void init() {
	}

	private static <T extends HullModule> HullModuleType<T> registerHull(String name, BiFunction<CartEntity, ModuleType<T>, T> factory, EnumSet<ModuleSide> sides, HullData hullData) {
		Identifier id = StevesCarts.id(name);
		return Registry.register(ModuleType.REGISTRY, id, new HullModuleType<>(factory, id, sides, hullData));
	}

	private static <T extends HullModule> HullModuleType<T> registerHull(String name, BiFunction<CartEntity, ModuleType<T>, T> factory, HullData hullData) {
		return registerHull(name, factory, EnumSet.noneOf(ModuleSide.class), hullData);
	}
}
