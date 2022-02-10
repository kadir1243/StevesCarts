package vswe.stevescarts.modules;

import com.mojang.serialization.Lifecycle;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.registry.SimpleRegistry;
import vswe.stevescarts.StevesCarts;

public class ModuleManager {
	public static final Registry<MinecartModuleType<? extends MinecartModule>> REGISTRY = FabricRegistryBuilder.<MinecartModuleType<? extends MinecartModule>, SimpleRegistry<MinecartModuleType<? extends MinecartModule>>>
			from(new SimpleRegistry<>(RegistryKey.ofRegistry(StevesCarts.id("module_type")), Lifecycle.stable())).buildAndRegister();

}
