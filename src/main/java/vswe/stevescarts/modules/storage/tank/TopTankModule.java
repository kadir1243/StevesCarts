package vswe.stevescarts.modules.storage.tank;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import reborncore.common.fluid.FluidValue;
import vswe.stevescarts.entity.ModularMinecartEntity;
import vswe.stevescarts.modules.MinecartModuleType;

public class TopTankModule extends TankModule {
	public TopTankModule(ModularMinecartEntity minecart, MinecartModuleType<?> type) {
		super(minecart, type, FluidValue.BUCKET.multiply(14));
	}
}
