package vswe.stevescarts.module;

import java.util.HashMap;

import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.mixin.TagManagerLoaderAccessor;

import net.minecraft.tag.TagKey;
import net.minecraft.text.Text;

public class ModuleTags {
	public static final TagKey<ModuleType<?>> CHESTS = of("chests");
	public static final TagKey<ModuleType<?>> HULLS = of("hulls");
	public static final TagKey<ModuleType<?>> INCOMPATIBLE_WITH_ADVANCED_THERMAL_ENGINE = of("incompatible_with_advanced_thermal_engine");
	public static final TagKey<ModuleType<?>> INCOMPATIBLE_WITH_COAL_ENGINE = of("incompatible_with_coal_engine");
	public static final TagKey<ModuleType<?>> INCOMPATIBLE_WITH_THERMAL_ENGINE = of("incompatible_with_thermal_engine");
	public static final TagKey<ModuleType<?>> INCOMPATIBLE_WITH_TINY_COAL_ENGINE = of("incompatible_with_tiny_coal_engine");
	public static final TagKey<ModuleType<?>> TANKS = of("tanks");

	static void init() {
		var map = new HashMap<>(TagManagerLoaderAccessor.getDirectories());
		map.put(ModuleType.REGISTRY.getKey(), "tags/stevescarts/module_types");
		TagManagerLoaderAccessor.setDirectories(map);
	}

	private static TagKey<ModuleType<?>> of(String id) {
		return TagKey.of(ModuleType.REGISTRY.getKey(), StevesCarts.id(id));
	}

	public static Text toText(TagKey<ModuleType<?>> tag) {
		return Text.translatable("tag." + tag.id().getNamespace() + "." + tag.id().getPath());
	}
}
