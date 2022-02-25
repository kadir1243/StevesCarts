package vswe.stevescarts.screen.widget;

import io.github.cottonmc.cotton.gui.GuiDescription;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.Property;
import net.minecraft.text.TranslatableText;

public class WPropertyLabel extends WLabel {
	private final String translation;
	private Property property;

	public WPropertyLabel(String translation, Property property) {
		super(new TranslatableText(translation, 0));
		this.translation = translation;
		this.property = property;
	}

	@Override
	public void validate(GuiDescription host) {
		super.validate(host);
	}

	@Override
	public void paint(MatrixStack matrices, int x, int y, int mouseX, int mouseY) {
		this.text = new TranslatableText(this.translation, this.property.get());
		super.paint(matrices, x, y, mouseX, mouseY);
	}
}
