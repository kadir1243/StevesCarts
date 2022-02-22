package vswe.stevescarts.screen.widget;

import io.github.cottonmc.cotton.gui.client.BackgroundPainter;
import io.github.cottonmc.cotton.gui.client.ScreenDrawing;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import io.github.cottonmc.cotton.gui.widget.WText;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import io.github.cottonmc.cotton.gui.widget.data.Texture;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import vswe.stevescarts.StevesCarts;

import java.util.Collections;
import java.util.List;

public class WInformation extends WPlainPanel {
	public static final Texture INFORMATION_BUTTON = new Texture(StevesCarts.id("textures/gui/information.png"), 0, 0, 1, 1);
	public static final TranslatableText TEXT = new TranslatableText("screen.stevescarts.cart_assembler.information");

	public static final BackgroundPainter PAINTER = (matrices, left, top, panel) -> {
		ScreenDrawing.texturedRect(matrices, left, top, 100, 9, INFORMATION_BUTTON, 0xFFFFFFFF);
		ScreenDrawing.drawString(matrices, TEXT.asOrderedText(), HorizontalAlignment.CENTER, left + 15, top, 85, 0xDDDDDD);
	};

	private final WText infoText = new WText(LiteralText.EMPTY);
	private List<Text> info = Collections.emptyList();

	public WInformation() {

	}

	public void setInfo(List<Text> info) {
		this.info = info;
	}

	@Override
	public void addPainters() {
		this.setBackgroundPainter(PAINTER);
		super.addPainters();
	}
}
