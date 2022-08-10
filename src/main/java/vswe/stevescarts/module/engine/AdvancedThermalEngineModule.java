package vswe.stevescarts.module.engine;

import vswe.stevescarts.entity.CartEntity;
import vswe.stevescarts.module.ModuleType;

import net.minecraft.fluid.Fluids;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;

public class AdvancedThermalEngineModule extends ThermalEngineModule {
	public static final FluidVariant WATER = FluidVariant.of(Fluids.WATER);

	public AdvancedThermalEngineModule(CartEntity minecart, ModuleType<?> type) {
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
