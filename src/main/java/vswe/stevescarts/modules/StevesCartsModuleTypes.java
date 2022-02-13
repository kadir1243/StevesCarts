package vswe.stevescarts.modules;

import vswe.stevescarts.modules.hull.HullModule;

public class StevesCartsModuleTypes {
	public static final MinecartModuleType<HullModule> WOODEN_HULL = MinecartModuleType.<HullModule>builder().hull().id("wooden_hull").factory(HullModule::new).moduleCost(0).modularCapacity(50).engineMaxCount(1).addonMaxCount(0).complexityMax(15).buildAndRegister();

	public static void init() {
	}
}
