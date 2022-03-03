package vswe.stevescarts.modules.tags;

import net.minecraft.tag.TagKey;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.modules.MinecartModule;
import vswe.stevescarts.modules.MinecartModuleType;

public class ModuleTags {
	public static final TagKey<MinecartModuleType<?>> HULLS = create(StevesCarts.id("hulls"));
	public static final TagKey<MinecartModuleType<?>> CHESTS = create(StevesCarts.id("chests"));
	public static final TagKey<MinecartModuleType<?>> TANKS = create(StevesCarts.id("tanks"));
	public static final TagKey<MinecartModuleType<?>> INCOMPATIBLE_WITH_COAL_ENGINE = create(StevesCarts.id("incompatible_with_coal_engine"));
	public static final TagKey<MinecartModuleType<?>> INCOMPATIBLE_WITH_TINY_COAL_ENGINE = create(StevesCarts.id("incompatible_with_tiny_coal_engine"));
	public static final TagKey<MinecartModuleType<?>> INCOMPATIBLE_WITH_THERMAL_ENGINE = create(StevesCarts.id("incompatible_with_thermal_engine"));
	public static final TagKey<MinecartModuleType<?>> INCOMPATIBLE_WITH_ADVANCED_THERMAL_ENGINE = create(StevesCarts.id("incompatible_with_advanced_thermal_engine"));

	public static void init() {
	}
	
	public static TagKey<MinecartModuleType<? extends MinecartModule>> create(Identifier id) {
		return TagKey.of(MinecartModuleType.REGISTRY_KEY, id);
	}

	public static Text toText(TagKey<?> tag) {
		return new TranslatableText("tag." + tag.id().getNamespace() + "." + tag.id().getPath());
	}
}
