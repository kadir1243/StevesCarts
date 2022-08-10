package vswe.stevescarts.mixin;

import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.tag.TagManagerLoader;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

@Mixin(TagManagerLoader.class)
public interface TagManagerLoaderAccessor {
	@Accessor("DIRECTORIES")
	static Map<RegistryKey<? extends Registry<?>>, String> getDirectories() {
		throw new UnsupportedOperationException();
	}

	@Mutable
	@Accessor("DIRECTORIES")
	static void setDirectories(Map<RegistryKey<? extends Registry<?>>, String> map) {
		throw new UnsupportedOperationException();
	}
}
