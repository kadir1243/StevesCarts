package vswe.stevescarts.modules.storage.tank;

import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import vswe.stevescarts.entity.ModularMinecartEntity;
import vswe.stevescarts.modules.MinecartModuleType;
import vswe.stevescarts.modules.storage.StorageModule;

public class TankModule extends StorageModule {
	public TankModule(ModularMinecartEntity minecart, MinecartModuleType<?> type) {
		super(minecart, type);
	}

	@Override
	public void configure(WPlainPanel panel) {
		// TODO
	}
}
