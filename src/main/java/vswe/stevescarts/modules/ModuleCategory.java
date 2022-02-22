package vswe.stevescarts.modules;

import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import vswe.stevescarts.modules.hull.HullModule;

public enum ModuleCategory {
	HULL(1, 0xFFFFFF, new TranslatableText("module.stevescarts.category.hull")),
	ENGINE(5, 0xFFA500, new TranslatableText("module.stevescarts.category.engines")) {
		@Override
		public int getMax(MinecartModuleType<? extends HullModule> type) {
			return type.getHullData().engineMaxCount();
		}
	},
	TOOL(1, 0xFFA500, new TranslatableText("module.stevescarts.category.tool")),
	ATTACHMENT(6, 0xFFA500, new TranslatableText("module.stevescarts.category.attachments")),
	STORAGE(4, 0xFFA500, new TranslatableText("module.stevescarts.category.storage")),
	ADDON(12, 0xFFA500, new TranslatableText("module.stevescarts.category.addons")) {
		@Override
		public int getMax(MinecartModuleType<? extends HullModule> type) {
			return type.getHullData().addonMaxCount();
		}
	};

	private final int maxMax;
	private final int textColor;
	private final Text translation;

	ModuleCategory(int maxMax, int textColor, Text translation) {
		this.maxMax = maxMax;
		this.textColor = textColor;
		this.translation = translation;
	}

	public int getMax(MinecartModuleType<? extends HullModule> type) {
		return this.getMaxMax();
	}

	public int getMaxMax() {
		return this.maxMax;
	}

	public int getTextColor() {
		return textColor;
	}

	public Text getTranslation() {
		return translation;
	}
}
