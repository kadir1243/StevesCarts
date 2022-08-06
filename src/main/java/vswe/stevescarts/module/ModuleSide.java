package vswe.stevescarts.module;

import net.minecraft.text.Text;

public enum ModuleSide {
	TOP,
	CENTER,
	BOTTOM,
	BACK,
	LEFT,
	RIGHT,
	FRONT;

	public Text asText() {
		return Text.translatable("module.side.stevescarts." + this.name().toLowerCase());
	}
}
