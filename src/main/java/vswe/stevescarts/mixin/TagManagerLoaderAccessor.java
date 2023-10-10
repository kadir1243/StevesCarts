package vswe.stevescarts.mixin;

import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.TagManagerLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

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
