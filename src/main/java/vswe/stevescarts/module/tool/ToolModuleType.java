package vswe.stevescarts.module.tool;

import java.util.EnumSet;
import java.util.function.BiFunction;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import vswe.stevescarts.entity.CartEntity;
import vswe.stevescarts.module.CartModule;
import vswe.stevescarts.module.ModuleGroup;
import vswe.stevescarts.module.ModuleSide;
import vswe.stevescarts.module.ModuleType;

import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;

public class ToolModuleType<T extends CartModule> extends ModuleType<T> {
	private final boolean unbreakable;

	// TODO
	public ToolModuleType(BiFunction<CartEntity, ModuleType<T>, T> factory,
						  Identifier id,
						  int moduleCost,
						  EnumSet<ModuleSide> sides,
						  ModuleGroup group,
						  boolean hasRenderer,
						  boolean duplicates,
						  boolean noHullTop, TagKey<ModuleType<?>> incompatibilities, Object2IntMap<TagKey<ModuleType<?>>> requirements,
						  boolean unbreakable) {
		super(factory, id, moduleCost, sides, group, hasRenderer, duplicates, noHullTop, incompatibilities, requirements);
		this.unbreakable = unbreakable;
	}

	public boolean isUnbreakable() {
		return unbreakable;
	}
}
