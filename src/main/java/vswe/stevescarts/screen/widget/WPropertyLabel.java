package vswe.stevescarts.screen.widget;

import io.github.cottonmc.cotton.gui.GuiDescription;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.text.TranslatableText;

public class WPropertyLabel extends WLabel {
	private final String translation;
	private final int property;
	private PropertyDelegate delegate;

	public WPropertyLabel(String translation, int property) {
		super(new TranslatableText(translation, 0));
		this.translation = translation;
		this.property = property;
	}

	@Override
	public void validate(GuiDescription host) {
		super.validate(host);
		this.delegate = host.getPropertyDelegate();
	}

	@Override
	public void paint(MatrixStack matrices, int x, int y, int mouseX, int mouseY) {
		this.text = new TranslatableText(this.translation, this.delegate.get(this.property));
		super.paint(matrices, x, y, mouseX, mouseY);
	}
}
