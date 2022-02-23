package vswe.stevescarts.modules;

import vswe.stevescarts.modules.hull.HullModule;
import vswe.stevescarts.modules.storage.chest.FrontChestModule;
import vswe.stevescarts.modules.storage.chest.SideChestsModule;
import vswe.stevescarts.modules.storage.chest.TopChestModule;
import vswe.stevescarts.modules.storage.tank.FrontTankModule;
import vswe.stevescarts.modules.storage.tank.SideTanksModule;
import vswe.stevescarts.modules.storage.tank.TankModule;
import vswe.stevescarts.modules.storage.tank.TopTankModule;
import vswe.stevescarts.modules.tags.ModuleTags;

public class StevesCartsModuleTypes {
	public static final MinecartModuleType<HullModule> WOODEN_HULL = MinecartModuleType.<HullModule>builder().hull().id("wooden_hull").factory(HullModule::new).modularCapacity(50).engineMaxCount(1).addonMaxCount(0).complexityMax(15).buildAndRegister();
	public static final MinecartModuleType<HullModule> STANDARD_HULL = MinecartModuleType.<HullModule>builder().hull().id("standard_hull").factory(HullModule::new).modularCapacity(200).engineMaxCount(3).addonMaxCount(5).complexityMax(50).buildAndRegister();
	public static final MinecartModuleType<HullModule> REINFORCED_HULL = MinecartModuleType.<HullModule>builder().hull().id("reinforced_hull").factory(HullModule::new).modularCapacity(500).engineMaxCount(5).addonMaxCount(12).complexityMax(150).buildAndRegister();
	public static final MinecartModuleType<HullModule> MECHANICAL_PIG = MinecartModuleType.<HullModule>builder().hull().id("mechanical_pig").factory(HullModule::new).sides(ModuleSide.FRONT, ModuleSide.BACK).modularCapacity(150).engineMaxCount(2).addonMaxCount(4).complexityMax(150).buildAndRegister();
	public static final MinecartModuleType<HullModule> GALGADORIAN_HULL = MinecartModuleType.<HullModule>builder().hull().id("galgadorian_hull").factory(HullModule::new).modularCapacity(1000).engineMaxCount(5).addonMaxCount(12).complexityMax(150).buildAndRegister();
	public static final MinecartModuleType<HullModule> CREATIVE_HULL = MinecartModuleType.<HullModule>builder().hull().id("creative_hull").factory(HullModule::new).modularCapacity(10000).engineMaxCount(5).addonMaxCount(12).complexityMax(1500).buildAndRegister();

	public static final MinecartModuleType<FrontChestModule> FRONT_CHEST = MinecartModuleType.<FrontChestModule>builder().id("front_chest").category(ModuleCategory.STORAGE).factory(FrontChestModule::new).sides(ModuleSide.FRONT).hasRenderer().moduleCost(5).buildAndRegister();
	public static final MinecartModuleType<TopChestModule> TOP_CHEST = MinecartModuleType.<TopChestModule>builder().id("top_chest").category(ModuleCategory.STORAGE).factory(TopChestModule::new).sides(ModuleSide.TOP).hasRenderer().moduleCost(6).buildAndRegister();
	public static final MinecartModuleType<SideChestsModule> SIDE_CHESTS = MinecartModuleType.<SideChestsModule>builder().id("side_chests").category(ModuleCategory.STORAGE).factory(SideChestsModule::new).sides(ModuleSide.LEFT, ModuleSide.RIGHT).hasRenderer().moduleCost(3).buildAndRegister();
	public static final MinecartModuleType<FrontTankModule> FRONT_TANK = MinecartModuleType.<FrontTankModule>builder().id("front_tank").category(ModuleCategory.STORAGE).factory(FrontTankModule::new).sides(ModuleSide.FRONT).hasRenderer().moduleCost(15).buildAndRegister();
	public static final MinecartModuleType<TopTankModule> TOP_TANK = MinecartModuleType.<TopTankModule>builder().id("top_tank").category(ModuleCategory.STORAGE).factory(TopTankModule::new).sides(ModuleSide.TOP).hasRenderer().moduleCost(22).buildAndRegister();
	public static final MinecartModuleType<SideTanksModule> SIDE_TANKS = MinecartModuleType.<SideTanksModule>builder().id("side_tanks").category(ModuleCategory.STORAGE).factory(SideTanksModule::new).sides(ModuleSide.LEFT, ModuleSide.RIGHT).hasRenderer().moduleCost(10).buildAndRegister();

	public static void init() {
		ModuleTags.init();
	}
}
