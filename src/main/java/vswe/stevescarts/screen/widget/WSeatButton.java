package vswe.stevescarts.screen.widget;

import io.github.cottonmc.cotton.gui.client.ScreenDrawing;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.data.Texture;

import net.minecraft.client.util.math.MatrixStack;

import vswe.stevescarts.StevesCarts;

public class WSeatButton extends WButton {
	public static final Texture BG_NORMAL = new Texture(StevesCarts.id("textures/gui/seat_button.png"), 0F, 0F, 1F, 0.12F);
	public static final Texture BG_HOVERED = new Texture(StevesCarts.id("textures/gui/seat_button.png"), 0F, 0.12F, 1F, 0.24F);
	public static final Texture BG_CANT_RIDE = new Texture(StevesCarts.id("textures/gui/seat_button.png"), 0F, 0.24F, 1F, 0.36F);
	public static final Texture FG_CANT_RIDE = new Texture(StevesCarts.id("textures/gui/seat_button.png"), 0F, 0.36F, 1F, 0.46F);
	public static final Texture FG_MOUNT = new Texture(StevesCarts.id("textures/gui/seat_button.png"), 0F, 0.46F, 1F, 0.56F);
	public static final Texture FG_DISMOUNT = new Texture(StevesCarts.id("textures/gui/seat_button.png"), 0F, 0.56F, 1F, 0.66F);

	private boolean canRide = true;
	private boolean riding = false;

	@Override
	public void setSize(int x, int y) {
		this.width = x;
		this.height = y;
	}

	@Override
	public void paint(MatrixStack matrices, int x, int y, int mouseX, int mouseY) {
		boolean hovered = mouseX >= 0 && mouseY >= 0 && mouseX < getWidth() && mouseY < getHeight();
		Texture bg = canRide ? hovered ? BG_HOVERED : BG_NORMAL : BG_CANT_RIDE;
		Texture fg = canRide ? this.riding ? FG_DISMOUNT : FG_MOUNT : FG_CANT_RIDE;
		ScreenDrawing.texturedRect(matrices, x, y, 24, 12, bg, 0xFFFFFFFF);
		ScreenDrawing.texturedRect(matrices, x, y + 1, 24, 10, fg, 0xFFFFFFFF);
	}

	public void setCanRide(boolean canRide) {
		this.canRide = canRide;
	}

	public boolean isRiding() {
		return this.riding;
	}

	public void setRiding(boolean isRiding) {
		this.riding = isRiding;
	}
}
