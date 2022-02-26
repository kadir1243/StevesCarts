package vswe.stevescarts.modules.storage;

import reborncore.common.fluid.FluidValue;
import vswe.stevescarts.entity.ModularMinecartEntity;
import vswe.stevescarts.modules.AntiConfigurable;
import vswe.stevescarts.modules.MinecartModuleType;

public class InternalTankModule extends TankModule implements AntiConfigurable {
	public InternalTankModule(ModularMinecartEntity minecart, MinecartModuleType<?> type) {
		super(minecart, type, FluidValue.BUCKET.multiply(4));
	}
}
