package vswe.stevescarts.modules;

import vswe.stevescarts.entity.ModularMinecartEntity;

import java.util.function.BiFunction;

public final class MinecartModuleType<T extends MinecartModule> {
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
}
