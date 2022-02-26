package vswe.stevescarts.modules;

import reborncore.common.fluid.FluidValue;
import vswe.stevescarts.modules.addon.BrakeModule;
import vswe.stevescarts.modules.addon.InvisibilityModule;
import vswe.stevescarts.modules.attachment.FireworkDisplayModule;
import vswe.stevescarts.modules.attachment.SeatModule;
import vswe.stevescarts.modules.engine.CoalEngineModule;
import vswe.stevescarts.modules.hull.HullModule;
import vswe.stevescarts.modules.storage.ChestModule;
import vswe.stevescarts.modules.storage.InternalStorageModule;
import vswe.stevescarts.modules.storage.InternalTankModule;
import vswe.stevescarts.modules.storage.TankModule;
import vswe.stevescarts.modules.tags.ModuleTags;

public class StevesCartsModuleTypes {
	// Hulls
	public static final MinecartModuleType<HullModule> WOODEN_HULL = MinecartModuleType.<HullModule>builder().hull().id("wooden_hull").factory(HullModule::new).modularCapacity(50).engineMaxCount(1).addonMaxCount(0).complexityMax(15).buildAndRegister();
	public static final MinecartModuleType<HullModule> STANDARD_HULL = MinecartModuleType.<HullModule>builder().hull().id("standard_hull").factory(HullModule::new).modularCapacity(200).engineMaxCount(3).addonMaxCount(5).complexityMax(50).buildAndRegister();
	public static final MinecartModuleType<HullModule> REINFORCED_HULL = MinecartModuleType.<HullModule>builder().hull().id("reinforced_hull").factory(HullModule::new).modularCapacity(500).engineMaxCount(5).addonMaxCount(12).complexityMax(150).buildAndRegister();
	public static final MinecartModuleType<HullModule> MECHANICAL_PIG = MinecartModuleType.<HullModule>builder().hull().id("mechanical_pig").factory(HullModule::new).sides(ModuleSide.FRONT, ModuleSide.BACK).modularCapacity(150).engineMaxCount(2).addonMaxCount(4).complexityMax(150).buildAndRegister();
	public static final MinecartModuleType<HullModule> GALGADORIAN_HULL = MinecartModuleType.<HullModule>builder().hull().id("galgadorian_hull").factory(HullModule::new).modularCapacity(1000).engineMaxCount(5).addonMaxCount(12).complexityMax(150).buildAndRegister();
	public static final MinecartModuleType<HullModule> CREATIVE_HULL = MinecartModuleType.<HullModule>builder().hull().id("creative_hull").factory(HullModule::new).modularCapacity(10000).engineMaxCount(5).addonMaxCount(12).complexityMax(1500).buildAndRegister();

	// Storage
	public static final MinecartModuleType<ChestModule> FRONT_CHEST = MinecartModuleType.<ChestModule>builder().id("front_chest").category(ModuleCategory.STORAGE).factory((minecart, type) -> new ChestModule(minecart, type, 4, 3)).sides(ModuleSide.FRONT).hasRenderer().moduleCost(5).buildAndRegister();
	public static final MinecartModuleType<ChestModule> TOP_CHEST = MinecartModuleType.<ChestModule>builder().id("top_chest").category(ModuleCategory.STORAGE).factory((minecart, type) -> new ChestModule(minecart, type, 6, 3)).sides(ModuleSide.TOP).hasRenderer().moduleCost(6).buildAndRegister();
	public static final MinecartModuleType<ChestModule> SIDE_CHESTS = MinecartModuleType.<ChestModule>builder().id("side_chests").category(ModuleCategory.STORAGE).factory((minecart, type) -> new ChestModule(minecart, type, 5, 3)).sides(ModuleSide.LEFT, ModuleSide.RIGHT).hasRenderer().moduleCost(3).buildAndRegister();
	public static final MinecartModuleType<TankModule> FRONT_TANK = MinecartModuleType.<TankModule>builder().id("front_tank").category(ModuleCategory.STORAGE).factory((entity, type) -> new TankModule(entity, type, FluidValue.BUCKET.multiply(8))).sides(ModuleSide.FRONT).hasRenderer().moduleCost(15).buildAndRegister();
	public static final MinecartModuleType<TankModule> TOP_TANK = MinecartModuleType.<TankModule>builder().id("top_tank").category(ModuleCategory.STORAGE).factory((entity, type) -> new TankModule(entity, type, FluidValue.BUCKET.multiply(14))).sides(ModuleSide.TOP).hasRenderer().moduleCost(22).buildAndRegister();
	public static final MinecartModuleType<TankModule> SIDE_TANKS = MinecartModuleType.<TankModule>builder().id("side_tanks").category(ModuleCategory.STORAGE).factory((entity, type) -> new TankModule(entity, type, FluidValue.BUCKET.multiply(8))).sides(ModuleSide.LEFT, ModuleSide.RIGHT).hasRenderer().moduleCost(10).buildAndRegister();
	public static final MinecartModuleType<TankModule> OPEN_TANK = MinecartModuleType.<TankModule>builder().id("open_tank").category(ModuleCategory.STORAGE).factory((entity, type) -> new TankModule(entity, type, FluidValue.BUCKET.multiply(7))).sides(ModuleSide.TOP).hasRenderer().moduleCost(31).buildAndRegister();
	public static final MinecartModuleType<InternalStorageModule> INTERNAL_STORAGE = MinecartModuleType.<InternalStorageModule>builder().id("internal_storage").category(ModuleCategory.STORAGE).factory(InternalStorageModule::new).allowDuplicates().moduleCost(25).buildAndRegister();
	public static final MinecartModuleType<InternalTankModule> INTERNAL_TANK = MinecartModuleType.<InternalTankModule>builder().id("internal_tank").category(ModuleCategory.STORAGE).factory(InternalTankModule::new).allowDuplicates().moduleCost(37).buildAndRegister();

	// Attachments
	public static final MinecartModuleType<SeatModule> SEAT = MinecartModuleType.<SeatModule>builder().id("seat").category(ModuleCategory.ATTACHMENT).factory(SeatModule::new).sides(ModuleSide.TOP, ModuleSide.CENTER).hasRenderer().noRenderTop().moduleCost(3).buildAndRegister();
	public static final MinecartModuleType<FireworkDisplayModule> FIREWORK_DISPLAY = MinecartModuleType.<FireworkDisplayModule>builder().id("firework_display").category(ModuleCategory.ATTACHMENT).factory(FireworkDisplayModule::new).moduleCost(45).buildAndRegister();

	// Engines
	public static final MinecartModuleType<CoalEngineModule> COAL_ENGINE = MinecartModuleType.<CoalEngineModule>builder().id("coal_engine").category(ModuleCategory.ENGINE).factory((minecart, type) -> new CoalEngineModule(minecart, type, 3, 2.25f)).hasRenderer().incompatible(ModuleTags.INCOMPATIBLE_WITH_COAL_ENGINE).moduleCost(15).buildAndRegister();
	public static final MinecartModuleType<CoalEngineModule> TINY_COAL_ENGINE = MinecartModuleType.<CoalEngineModule>builder().id("tiny_coal_engine").category(ModuleCategory.ENGINE).factory((minecart, type) -> new CoalEngineModule(minecart, type, 1, 0.5f)).hasRenderer().incompatible(ModuleTags.INCOMPATIBLE_WITH_TINY_COAL_ENGINE).moduleCost(2).buildAndRegister();

	// Addons
	public static final MinecartModuleType<BrakeModule> BRAKE = MinecartModuleType.<BrakeModule>builder().id("brake").category(ModuleCategory.ADDON).factory(BrakeModule::new).sides(ModuleSide.RIGHT).require(StevesCartsModuleTypes.SEAT).hasRenderer().noRenderTop().moduleCost(12).buildAndRegister();
	public static final MinecartModuleType<InvisibilityModule> INVISIBILITY_CORE = MinecartModuleType.<InvisibilityModule>builder().id("invisibility_core").category(ModuleCategory.ADDON).factory(InvisibilityModule::new).moduleCost(21).buildAndRegister();

	// Tools TODO

	public static void init() {
		ModuleTags.init();
	}
}
