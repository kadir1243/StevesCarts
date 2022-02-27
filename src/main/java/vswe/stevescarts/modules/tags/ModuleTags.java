package vswe.stevescarts.modules.tags;

import net.fabricmc.fabric.api.tag.TagFactory;
import net.minecraft.tag.Tag;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.modules.MinecartModuleType;

public class ModuleTags {
	public static final TagFactory<MinecartModuleType<?>> MODULE_TYPE = TagFactory.of(MinecartModuleType.REGISTRY_KEY, "tags/stevescarts/module_types");

	public static final Tag.Identified<MinecartModuleType<?>> HULLS = MODULE_TYPE.create(StevesCarts.id("hulls"));
	public static final Tag.Identified<MinecartModuleType<?>> CHESTS = MODULE_TYPE.create(StevesCarts.id("chests"));
	public static final Tag.Identified<MinecartModuleType<?>> TANKS = MODULE_TYPE.create(StevesCarts.id("tanks"));
	public static final Tag.Identified<MinecartModuleType<?>> INCOMPATIBLE_WITH_COAL_ENGINE = MODULE_TYPE.create(StevesCarts.id("incompatible_with_coal_engine"));
	public static final Tag.Identified<MinecartModuleType<?>> INCOMPATIBLE_WITH_TINY_COAL_ENGINE = MODULE_TYPE.create(StevesCarts.id("incompatible_with_tiny_coal_engine"));
	public static final Tag.Identified<MinecartModuleType<?>> INCOMPATIBLE_WITH_THERMAL_ENGINE = MODULE_TYPE.create(StevesCarts.id("incompatible_with_thermal_engine"));
	public static final Tag.Identified<MinecartModuleType<?>> INCOMPATIBLE_WITH_ADVANCED_THERMAL_ENGINE = MODULE_TYPE.create(StevesCarts.id("incompatible_with_advanced_thermal_engine"));

	public static void init() {
	}

	public static Text toText(Tag.Identified<?> tag) {
		return new TranslatableText("tag." + tag.getId().getNamespace() + "." + tag.getId().getPath());
	}
}
