package vswe.stevescarts.screen.widget;

import io.github.cottonmc.cotton.gui.GuiDescription;
import io.github.cottonmc.cotton.gui.client.BackgroundPainter;
import io.github.cottonmc.cotton.gui.client.ScreenDrawing;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import io.github.cottonmc.cotton.gui.widget.WText;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import io.github.cottonmc.cotton.gui.widget.data.Texture;
import io.github.cottonmc.cotton.gui.widget.data.VerticalAlignment;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import vswe.stevescarts.StevesCarts;

import java.util.Collections;
import java.util.List;

public class WInformation extends WPlainPanel {
	public static final Texture INFORMATION_BUTTON = new Texture(StevesCarts.id("textures/gui/information.png"), 0, 0, 1, 1);
	public static final TranslatableText TEXT = new TranslatableText("screen.stevescarts.cart_assembler.information");
	public static final BackgroundPainter PAINTER = (matrices, left, top, panel) -> {
		ScreenDrawing.texturedRect(matrices, left, top, 100, 9, INFORMATION_BUTTON, 0xFFFFFFFF);
		matrices.push();
		matrices.scale(1F, 0.8F, 1F);
		matrices.translate(0F, (top)/0.8F - (top) + 1, 0F);
		ScreenDrawing.drawString(matrices, TEXT.asOrderedText(), HorizontalAlignment.CENTER, left + 12, top + 1, 85, 0xDDDDDD);
		matrices.pop();
	};

	private final WText infoText = new WText(LiteralText.EMPTY);

	public WInformation() {
		this.infoText.setVerticalAlignment(VerticalAlignment.TOP);
		this.infoText.setHorizontalAlignment(HorizontalAlignment.LEFT);
		this.add(this.infoText, 5, 15, 90, 0);
	}

	public void setText(MutableText info) {
		this.infoText.setText(info.formatted(Formatting.DARK_RED));
	}

	public void clear() {
		this.setText((MutableText) LiteralText.EMPTY);
	}

	@Override
	public void validate(GuiDescription c) {
		super.validate(c);
	}

	@Override
	public void addPainters() {
		this.setBackgroundPainter(PAINTER);
		super.addPainters();
	}
}
