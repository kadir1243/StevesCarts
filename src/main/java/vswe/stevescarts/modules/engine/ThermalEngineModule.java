package vswe.stevescarts.modules.engine;

import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import net.minecraft.entity.player.PlayerEntity;
import vswe.stevescarts.entity.ModularMinecartEntity;
import vswe.stevescarts.modules.MinecartModuleType;
import vswe.stevescarts.screen.ModularCartHandler;

public class ThermalEngineModule extends EngineModule {
	public ThermalEngineModule(ModularMinecartEntity minecart, MinecartModuleType<?> type) {
		super(minecart, type);
	}

	@Override
	public void configure(WPlainPanel panel, ModularCartHandler handler, PlayerEntity player) {

	}

	@Override
	public boolean canPropel() {
		return false;
	}

	@Override
	public void onPropel() {

	}

	@Override
	protected String getDiscriminator() {
		return "thermal";
	}
}
