package vswe.stevescarts.screen.widget;

import io.github.cottonmc.cotton.gui.client.ScreenDrawing;
import io.github.cottonmc.cotton.gui.widget.WWidget;
import io.github.cottonmc.cotton.gui.widget.data.InputResult;
import io.github.cottonmc.cotton.gui.widget.data.Texture;

import java.util.function.IntConsumer;
import java.util.function.IntSupplier;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

import vswe.stevescarts.StevesCarts;

public class WLightThreshold extends WWidget {
	public static final Texture BG_NORMAL = new Texture(StevesCarts.id("textures/gui/light_threshold.png"), 0.0F, 0.0F, 0.359375F, 0.28125F);
	public static final Texture BG_HOVERED = new Texture(StevesCarts.id("textures/gui/light_threshold.png"), 0.359375F, 0, 0.71875F, 0.28125F);
	public static final Texture FG = new Texture(StevesCarts.id("textures/gui/light_threshold.png"), 0.0F, 0.28125F, 0.34375F, 0.5F);
	public static final Texture BAR = new Texture(StevesCarts.id("textures/gui/light_threshold.png"), 0.0F, 0.5F, 0.0078125F, 0.1953125F);
	private final IntSupplier lightSupplier;
	private int current;
	private final IntConsumer setter;

	public WLightThreshold(IntSupplier lightSupplier, int current, IntConsumer setter) {
		this.lightSupplier = lightSupplier;
		this.current = current;
		this.setter = setter;
	}

	@Override
	public InputResult onClick(int x, int y, int button) {
		if (this.isWithinBounds(x, y)) {
			int light = (int) (((float) x / this.width) * 15);
			this.current = light;
			this.setter.accept(light);
		}
		return super.onClick(x, y, button);
	}

	@Override
	public void paint(MatrixStack matrices, int x, int y, int mouseX, int mouseY) {
		boolean hovered = mouseX >= 0 && mouseY >= 0 && mouseX < getWidth() && mouseY < getHeight();
		Texture bg = hovered ? BG_HOVERED : BG_NORMAL;
		ScreenDrawing.texturedRect(matrices, x, y, 46, 9, bg, 0xFFFFFFFF);
		super.paint(matrices, x, y, mouseX, mouseY);
		float lightFraction = MathHelper.clamp(this.lightSupplier.getAsInt() / 15.0F, 0.0F, 1.0F);
		int lightWidth = (int) (44 * lightFraction);
		float lightU = 0.34375F * lightFraction;
		ScreenDrawing.texturedRect(matrices, x + 1, y + 1, lightWidth, 7, FG.withUv(FG.u1(), FG.v1(), lightU, FG.v2()), 0xFFFFFFFF);
		int barX = (int) ((this.current / 15.0) * 44);
		ScreenDrawing.texturedRect(matrices, x + barX, y + 1, 1, 7, BAR, 0xFFFFFFFF);
	}
}
