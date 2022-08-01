package vswe.stevescarts.module;

import java.util.EnumSet;

import net.minecraft.util.Identifier;

public class HullModuleType<T extends CartModule> extends ModuleType<T> {
	private final HullData hullData;

	public HullModuleType(Identifier id, int moduleCost, EnumSet<ModuleSide> sides, ModuleGroup group, boolean hasRenderer, boolean duplicates, boolean noHullTop, HullData hullData) {
		super(factory, id, moduleCost, sides, group, hasRenderer, duplicates, noHullTop);
		this.hullData = hullData;
	}

	public HullData getHullData() {
		return hullData;
	}
}
