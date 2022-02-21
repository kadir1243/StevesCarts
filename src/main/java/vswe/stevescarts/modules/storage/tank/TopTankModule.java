package vswe.stevescarts.modules.storage.tank;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import vswe.stevescarts.entity.ModularMinecartEntity;
import vswe.stevescarts.modules.MinecartModuleType;

public class TopTankModule extends TankModule {
	public TopTankModule(ModularMinecartEntity minecart, MinecartModuleType<?> type) {
		super(minecart, type, FluidConstants.BUCKET * 14);
	}
}
