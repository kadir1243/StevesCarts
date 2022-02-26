package vswe.stevescarts.modules.storage;

import vswe.stevescarts.entity.ModularMinecartEntity;
import vswe.stevescarts.modules.AntiConfigurable;
import vswe.stevescarts.modules.MinecartModuleType;

public class InternalStorageModule extends ChestModule implements AntiConfigurable {
	public InternalStorageModule(ModularMinecartEntity minecart, MinecartModuleType<?> type) {
		super(minecart, type, 3, 3);
	}

	@Override
	public boolean shouldAnimate() {
		return false;
	}
}
