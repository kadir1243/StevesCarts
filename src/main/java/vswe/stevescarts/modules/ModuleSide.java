package vswe.stevescarts.modules;

import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.StringIdentifiable;

public enum ModuleSide implements StringIdentifiable {
	NONE("none"),
	TOP("top"),
	CENTER("center"),
	BOTTOM("bottom"),
	BACK("back"),
	LEFT("left"),
	RIGHT("right"),
	FRONT("front");

	private final String name;

	ModuleSide(String name) {
		this.name = name;
	}

	public Text asText() {
		return new TranslatableText("module.side.stevescarts." + this.asString());
	}

	@Override
	public String asString() {
		return this.name;
	}

	public boolean occupiesSide() {
		return this != NONE;
	}
}
