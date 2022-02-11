package vswe.stevescarts.modules;

import com.mojang.serialization.Lifecycle;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.registry.SimpleRegistry;
import org.jetbrains.annotations.Nullable;
import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.entity.ModularMinecartEntity;
import vswe.stevescarts.modules.hull.HullModule;

import java.util.function.BiFunction;

public final class MinecartModuleType<T extends MinecartModule> {
	public static final MinecartModuleType<HullModule> WOODEN_HULL = register("wooden_hull", HullModule::new);
	public static final Registry<MinecartModuleType<? extends MinecartModule>> REGISTRY = FabricRegistryBuilder.<MinecartModuleType<? extends MinecartModule>, SimpleRegistry<MinecartModuleType<? extends MinecartModule>>>
			from(new SimpleRegistry<>(RegistryKey.ofRegistry(StevesCarts.id("module_type")), Lifecycle.stable())).buildAndRegister();
	private final BiFunction<ModularMinecartEntity, MinecartModuleType<T>, T> factory;

	public MinecartModuleType(BiFunction<ModularMinecartEntity, MinecartModuleType<T>, T> factory) {
		this.factory = factory;
	}

	public T createModule(ModularMinecartEntity cart) {
		return factory.apply(cart, this);
	}

	@Override
	public String toString() {
		return REGISTRY.getId(this).toString();
	}

	private static <T extends MinecartModule> MinecartModuleType<T> register(String id, BiFunction<ModularMinecartEntity, MinecartModuleType<T>, T> factory) {
		return Registry.register(REGISTRY, StevesCarts.id(id), new MinecartModuleType<>(factory));
	}

	public static NbtCompound toNbt(MinecartModule module) {
		NbtCompound nbt = new NbtCompound();
		nbt.putString("type", module.getType().toString());
		module.writeNbt(nbt);
		return nbt;
	}

	public static MinecartModule fromNbt(NbtCompound nbt, @Nullable ModularMinecartEntity cart) {
		MinecartModuleType<?> type = REGISTRY.get(new Identifier(nbt.getString("type")));
		if (type == null) {
			throw new IllegalArgumentException("No module type in NBT");
		}
		return type.createModule(cart);
	}
}
