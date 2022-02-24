package vswe.stevescarts.modules.engine;

import vswe.stevescarts.entity.ModularMinecartEntity;
import vswe.stevescarts.modules.Configurable;
import vswe.stevescarts.modules.MinecartModule;
import vswe.stevescarts.modules.MinecartModuleType;

public abstract class EngineModule extends MinecartModule implements Configurable {
	protected boolean propelling = false;

	protected EngineModule(ModularMinecartEntity minecart, MinecartModuleType<?> type) {
		super(minecart, type);
	}

	@Override
	public abstract boolean canPropel();

	@Override
	public abstract void onPropel();

	public boolean isPropelling() {
		return propelling;
	}

	public void setPropelling(boolean propelling) {
		this.propelling = propelling;
	}
}
