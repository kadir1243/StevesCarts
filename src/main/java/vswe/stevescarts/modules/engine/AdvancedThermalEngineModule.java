package vswe.stevescarts.modules.engine;

import net.minecraft.fluid.Fluids;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;

import vswe.stevescarts.entity.ModularMinecartEntity;
import vswe.stevescarts.modules.MinecartModuleType;

public class AdvancedThermalEngineModule extends ThermalEngineModule {
	public static final FluidVariant WATER = FluidVariant.of(Fluids.WATER);

	public AdvancedThermalEngineModule(ModularMinecartEntity minecart, MinecartModuleType<?> type) {
		super(minecart, type);
	}

	@Override
	public boolean canPropel() {
		return super.canPropel() && this.checkSimulate(WATER, 15);
	}

	@Override
	public void onPropel() {
		super.onPropel();
		this.consume(WATER, 15);
	}
}
