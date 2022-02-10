package vswe.stevescarts.modules;

import net.minecraft.util.registry.Registry;
import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.entity.ModularMinecartEntity;
import vswe.stevescarts.modules.hull.HullModule;

import java.util.function.BiFunction;

public final class MinecartModuleType<T extends MinecartModule> {
	public static final MinecartModuleType<HullModule> WOODEN_HULL = register("wooden_hull", HullModule::new);
	private final BiFunction<ModularMinecartEntity, MinecartModuleType<T>, T> factory;

	public MinecartModuleType(BiFunction<ModularMinecartEntity, MinecartModuleType<T>, T> factory) {
		this.factory = factory;
	}

	public T createModule(ModularMinecartEntity cart) {
		return factory.apply(cart, this);
	}

	@Override
	public String toString() {
		return ModuleManager.REGISTRY.getId(this).toString();
	}

	private static <T extends MinecartModule> MinecartModuleType<T> register(String id, BiFunction<ModularMinecartEntity, MinecartModuleType<T>, T> factory) {
		return Registry.register(ModuleManager.REGISTRY, StevesCarts.id(id), new MinecartModuleType<>(factory));
	}
}
