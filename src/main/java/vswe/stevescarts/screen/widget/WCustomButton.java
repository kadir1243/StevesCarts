package vswe.stevescarts.screen.widget;

import io.github.cottonmc.cotton.gui.client.ScreenDrawing;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.data.Texture;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import vswe.stevescarts.StevesCarts;

public class WCustomButton extends WButton {
	public static final Texture BG_NORMAL = new Texture(StevesCarts.id("textures/gui/brake.png"), 0F, 0F, 1F, 0.1875F);
	public static final Texture BG_HOVERED = new Texture(StevesCarts.id("textures/gui/brake.png"), 0F, 0.1875F, 1F, 0.375F);
	public static final Texture FG_REVERSE = new Texture(StevesCarts.id("textures/gui/brake.png"), 0F, 0.375F, 1F, 0.53125F);
	public static final Texture FG_STOP = new Texture(StevesCarts.id("textures/gui/brake.png"), 0F, 0.53125F, 1F, 0.6875F);
	public static final Texture FG_CONTINUE = new Texture(StevesCarts.id("textures/gui/brake.png"), 0F, 0.6875F, 1F, 0.84375F);
	public static final Texture FG_INVISIBLE = new Texture(StevesCarts.id("textures/gui/invisibility_core.png"), 0F, 0F, 1F, 0.5F);
	public static final Texture FG_UNINVISIBLE = new Texture(StevesCarts.id("textures/gui/invisibility_core.png"), 0F, 0.5F, 1F, 1F);
	private final Texture first;
	private final Texture second;
	private Texture activeTexture;
	private final Text tooltip;

	public WCustomButton(Texture first, Texture second, Text tooltip) {
		this.first = first;
		this.second = second;
		this.activeTexture = first;
		this.tooltip = tooltip;
	}

	public WCustomButton(Texture texture, Text tooltip) {
		this(texture, texture, tooltip);
	}

	@Override
	public void setSize(int x, int y) {
		this.width = x;
		this.height = y;
	}

	@Override
	public void paint(MatrixStack matrices, int x, int y, int mouseX, int mouseY) {
		boolean hovered = mouseX >= 0 && mouseY >= 0 && mouseX < getWidth() && mouseY < getHeight();
		Texture bg = hovered ? BG_HOVERED : BG_NORMAL;
		ScreenDrawing.texturedRect(matrices, x, y, 24, 12, bg, 0xFFFFFFFF);
		ScreenDrawing.texturedRect(matrices, x, y + 1, 24, 10, this.activeTexture, 0xFFFFFFFF);
	}

	public void changeTexture() {
		this.activeTexture = this.activeTexture == first ? second : first;
	}

	public boolean hasSecondTexture() {
		return this.activeTexture == second;
	}
}
