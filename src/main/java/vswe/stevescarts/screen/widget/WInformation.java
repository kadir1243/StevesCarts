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

public class WInformation extends WPlainPanel {
	public static final Texture INFORMATION_BUTTON = new Texture(StevesCarts.id("textures/gui/information.png"), 0, 0, 1, 1);
	public static final TranslatableText TEXT = new TranslatableText("screen.stevescarts.cart_assembler.information");
	public static final TranslatableText READY = (TranslatableText) new TranslatableText("screen.stevescarts.cart_assembler.ready").formatted(Formatting.DARK_GREEN);
	public static final BackgroundPainter PAINTER = (matrices, left, top, panel) -> {
		ScreenDrawing.texturedRect(matrices, left, top, 100, 9, INFORMATION_BUTTON, 0xFFFFFFFF);
		matrices.push();
		matrices.scale(1F, 0.8F, 1F);
		matrices.translate(0F, (top) / 0.8F - (top) + 1, 0F);
		ScreenDrawing.drawString(matrices, TEXT.asOrderedText(), HorizontalAlignment.CENTER, left + 12, top + 1, 85, 0xDDDDDD);
		matrices.pop();
	};

	private final WText infoText = new WText(LiteralText.EMPTY);
	private final WText statusText = new WText(LiteralText.EMPTY);

	public WInformation() {
		this.statusText.setVerticalAlignment(VerticalAlignment.TOP);
		this.statusText.setHorizontalAlignment(HorizontalAlignment.LEFT);
		this.infoText.setVerticalAlignment(VerticalAlignment.TOP);
		this.infoText.setHorizontalAlignment(HorizontalAlignment.LEFT);
		this.add(this.statusText, 0, 65, 105, 0);
		this.add(this.infoText, 5, 15, 100, 0);
	}

	public void setErrText(MutableText err) {
		this.setStatusText(err.formatted(Formatting.DARK_RED));
	}

	public void setSuccessText(MutableText err) {
		this.setStatusText(err.formatted(Formatting.GREEN));
	}

	public void setStatusText(Text info) {
		this.statusText.setText(info);
	}

	public void setInfoText(Text info) {
		this.infoText.setText(info);
	}

	public void successStatus() {
		this.setStatusText(READY);
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
