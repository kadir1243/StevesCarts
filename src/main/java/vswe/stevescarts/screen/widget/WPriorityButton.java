package vswe.stevescarts.screen.widget;

import io.github.cottonmc.cotton.gui.client.ScreenDrawing;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.data.Texture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Util;
import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.util.ByteSupplier;

public class WPriorityButton extends WButton {
	public static final Texture[] NORMAL_TEXTURES = Util.make(new Texture[4], arr -> {
		for (int i = 0; i < 4; ++i) {
			float uStart = i / 4.0f;
			float uEnd = (i + 1) / 4.0f;
			arr[i] = new Texture(StevesCarts.id("textures/gui/priority.png"), uStart, 0.0f, uEnd, 0.5f);
		}
	});
	public static final Texture[] HOVERED_TEXTURES = Util.make(new Texture[4], arr -> {
		for (int i = 0; i < 4; ++i) {
			Texture normalTexture = NORMAL_TEXTURES[i];
			arr[i] = NORMAL_TEXTURES[i].withUv(normalTexture.u1(), 0.5f, normalTexture.u2(), 1.0f);
		}
	});
	private final ByteSupplier prioritySupplier;

	public WPriorityButton(ByteSupplier supplier) {
		this.prioritySupplier = supplier;
	}

	@Override
	public void paint(MatrixStack matrices, int x, int y, int mouseX, int mouseY) {
		boolean hovered = (mouseX>=0 && mouseY>=0 && mouseX<getWidth() && mouseY<getHeight());
		Texture renderTexture = hovered ? HOVERED_TEXTURES[this.prioritySupplier.get()] : NORMAL_TEXTURES[this.prioritySupplier.get()];
		ScreenDrawing.texturedRect(matrices, x, y, 16, 16, renderTexture, 0xFFFFFFFF);
	}
}
