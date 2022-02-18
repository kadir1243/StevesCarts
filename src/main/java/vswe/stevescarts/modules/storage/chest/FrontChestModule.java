package vswe.stevescarts.modules.storage.chest;

import vswe.stevescarts.entity.ModularMinecartEntity;
import vswe.stevescarts.modules.MinecartModuleType;

public class FrontChestModule extends ChestModule {
	public FrontChestModule(ModularMinecartEntity minecart, MinecartModuleType<?> type) {
		super(minecart, type, 4, 3);
	}
}
