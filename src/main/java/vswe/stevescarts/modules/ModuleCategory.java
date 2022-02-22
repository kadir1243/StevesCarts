package vswe.stevescarts.modules;

import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import vswe.stevescarts.modules.hull.HullModule;

public enum ModuleCategory {
	HULL(1, 0xFFFFFF, new TranslatableText("module.stevescarts.category.hull.title")),
	ENGINE(5, 0xFFA500, new TranslatableText("module.stevescarts.category.engines.title")) {
		@Override
		public int getMax(MinecartModuleType<? extends HullModule> type) {
			return type.getHullData().engineMaxCount();
		}
	},
	TOOL(1, 0xA020F0, new TranslatableText("module.stevescarts.category.tool.title")),
	ATTACHMENT(6, 0x0D98BA, new TranslatableText("module.stevescarts.category.attachments.title")),
	STORAGE(4, 0xFF4848, new TranslatableText("module.stevescarts.category.storage.title")),
	ADDON(12, 0x056608, new TranslatableText("module.stevescarts.category.addons.title")) {
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
