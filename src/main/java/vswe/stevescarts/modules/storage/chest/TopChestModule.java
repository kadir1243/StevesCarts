package vswe.stevescarts.modules.storage.chest;

import vswe.stevescarts.entity.ModularMinecartEntity;
import vswe.stevescarts.modules.MinecartModuleType;

public class TopChestModule extends ChestModule {
	public TopChestModule(ModularMinecartEntity minecart, MinecartModuleType<?> type) {
		super(minecart, type, 6, 3);
	}
}
