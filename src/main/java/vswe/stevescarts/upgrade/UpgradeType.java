package vswe.stevescarts.upgrade;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.util.registry.Registry;
import vswe.stevescarts.StevesCarts;

public class UpgradeType {
	public static final Registry<UpgradeType> REGISTRY = FabricRegistryBuilder.createSimple(UpgradeType.class, StevesCarts.id("upgrade_type")).buildAndRegister();
}
