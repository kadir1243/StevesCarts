package vswe.stevescarts.modules.engine;

import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import vswe.stevescarts.entity.ModularMinecartEntity;
import vswe.stevescarts.modules.MinecartModuleType;
import vswe.stevescarts.screen.ModularCartHandler;

public class ThermalEngineModule extends EngineModule {
	public static final FluidVariant LAVA = FluidVariant.of(Fluids.LAVA);
	public static final FluidVariant WATER = FluidVariant.of(Fluids.WATER);
	protected final boolean requiresCoolant;

	public ThermalEngineModule(ModularMinecartEntity minecart, MinecartModuleType<?> type, boolean requiresCoolant) {
		super(minecart, type);
		this.requiresCoolant = requiresCoolant;
	}

	@Override
	public void configure(WPlainPanel panel, ModularCartHandler handler, PlayerEntity player) {
	}

	@Override
	public boolean canPropel() {
		return this.minecart.getFluidStorage().simulateExtract(LAVA, 15, null) > 0;
	}

	@Override
	public void onPropel() {
		this.minecart.getFluidStorage().extract(LAVA, 15, null);
	}

	@Override
	protected String getDiscriminator() {
		return "thermal";
	}
}