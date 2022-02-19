package vswe.stevescarts.modules.storage.chest;

import vswe.stevescarts.entity.ModularMinecartEntity;
import vswe.stevescarts.modules.MinecartModuleType;

public class SideChestsModule extends ChestModule {
	public SideChestsModule(ModularMinecartEntity minecart, MinecartModuleType<?> type) {
		super(minecart, type, 5, 3);
	}
}
