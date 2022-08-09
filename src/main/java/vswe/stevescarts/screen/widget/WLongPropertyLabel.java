package vswe.stevescarts.screen.widget;

import io.github.cottonmc.cotton.gui.GuiDescription;
import io.github.cottonmc.cotton.gui.widget.WLabel;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import vswe.stevescarts.util.LongProperty;

public class WLongPropertyLabel extends WLabel {
	private final String translation;
	private final LongProperty property;

	public WLongPropertyLabel(String translation, LongProperty property) {
		super(Text.translatable(translation, 0));
		this.translation = translation;
		this.property = property;
	}

	@Override
	public void validate(GuiDescription host) {
		super.validate(host);
	}

	@Override
	public void paint(MatrixStack matrices, int x, int y, int mouseX, int mouseY) {
		this.text = Text.translatable(this.translation, this.property.getAsLong());
		super.paint(matrices, x, y, mouseX, mouseY);
	}
}
