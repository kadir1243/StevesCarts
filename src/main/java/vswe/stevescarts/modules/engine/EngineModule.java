package vswe.stevescarts.modules.engine;

import vswe.stevescarts.entity.ModularMinecartEntity;
import vswe.stevescarts.modules.Configurable;
import vswe.stevescarts.modules.MinecartModule;
import vswe.stevescarts.modules.MinecartModuleType;

public abstract class EngineModule extends MinecartModule implements Configurable {
	protected EngineModule(ModularMinecartEntity minecart, MinecartModuleType<?> type) {
		super(minecart, type);
	}

	@Override
	public abstract boolean shouldPropel();

	@Override
	public abstract void onPropel();
}
