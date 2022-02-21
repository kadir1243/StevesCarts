package vswe.stevescarts.modules;

import vswe.stevescarts.modules.hull.HullModule;

public enum ModuleCategory {
	HULL(1),
	ENGINE(5) {
		@Override
		public int getMax(MinecartModuleType<? extends HullModule> type) {
			return type.getHullData().engineMaxCount();
		}
	},
	TOOL(1),
	ATTACHMENT(6),
	STORAGE(4),
	ADDON(12) {
		@Override
		public int getMax(MinecartModuleType<? extends HullModule> type) {
			return type.getHullData().addonMaxCount();
		}
	};

	private final int maxMax;

	ModuleCategory(int maxMax) {
		this.maxMax = maxMax;
	}

	public int getMax(MinecartModuleType<? extends HullModule> type) {
		return this.getMaxMax();
	}

	public int getMaxMax() {
		return this.maxMax;
	}
}
