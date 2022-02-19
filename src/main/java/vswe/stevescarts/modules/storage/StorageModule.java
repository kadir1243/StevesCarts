package vswe.stevescarts.modules.storage;

import vswe.stevescarts.entity.ModularMinecartEntity;
import vswe.stevescarts.modules.Configurable;
import vswe.stevescarts.modules.MinecartModule;
import vswe.stevescarts.modules.MinecartModuleType;

public abstract class StorageModule extends MinecartModule implements Configurable {
	protected StorageModule(ModularMinecartEntity minecart, MinecartModuleType<?> type) {
		super(minecart, type);
	}
}
