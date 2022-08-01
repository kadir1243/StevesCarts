package vswe.stevescarts.module.hull;

import java.util.EnumSet;
import java.util.function.BiFunction;

import vswe.stevescarts.entity.CartEntity;
import vswe.stevescarts.module.CartModule;
import vswe.stevescarts.module.ModuleGroup;
import vswe.stevescarts.module.ModuleSide;
import vswe.stevescarts.module.ModuleType;

import net.minecraft.util.Identifier;

public class HullModuleType<T extends CartModule> extends ModuleType<T> {
	private final HullData hullData;

	public HullModuleType(BiFunction<CartEntity, ModuleType<T>, T> factory, Identifier id, EnumSet<ModuleSide> sides, HullData hullData) {
		super(factory, id, 0, sides, ModuleGroup.HULL, true, false, false);
		this.hullData = hullData;
	}

	public HullData getHullData() {
		return hullData;
	}
}
