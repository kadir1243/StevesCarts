package vswe.stevescarts.upgrade;

import net.minecraft.util.registry.Registry;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;

import vswe.stevescarts.StevesCarts;

public class UpgradeType {
	public static final Registry<UpgradeType> REGISTRY = FabricRegistryBuilder.createSimple(UpgradeType.class, StevesCarts.id("upgrade_type")).buildAndRegister();
}
