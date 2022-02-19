package vswe.stevescarts.modules;

import vswe.stevescarts.modules.hull.HullModule;

public enum ModuleCategory {
	HULL,
	ENGINE {
		@Override
		public int getMax(MinecartModuleType<? extends HullModule> type) {
			return type.getHullData().engineMaxCount();
		}
	},
	TOOL,
	ATTACHMENT,
	STORAGE,
	ADDON {
		@Override
		public int getMax(MinecartModuleType<? extends HullModule> type) {
			return type.getHullData().addonMaxCount();
		}
	};

	public int getMax(MinecartModuleType<? extends HullModule> type) {
		throw new UnsupportedOperationException("Category not supported");
	}
}
