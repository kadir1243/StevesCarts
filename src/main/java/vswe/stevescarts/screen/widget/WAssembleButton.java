package vswe.stevescarts.screen.widget;

import io.github.cottonmc.cotton.gui.client.ScreenDrawing;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import io.github.cottonmc.cotton.gui.widget.data.Texture;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import vswe.stevescarts.StevesCarts;

public class WAssembleButton extends WButton {
	@Environment(EnvType.CLIENT)
	private static final Texture ENABLED = new Texture(StevesCarts.id("textures/gui/assemble_button.png"), 0.0F, 0.0F, 1.0F, 1.0F/3.0F);
	@Environment(EnvType.CLIENT)
	private static final Texture HOVERED = new Texture(StevesCarts.id("textures/gui/assemble_button.png"), 0.0F, 1.0F/3.0F, 1.0F, 2.0F/3.0F);
	@Environment(EnvType.CLIENT)
	private static final Texture DISABLED = new Texture(StevesCarts.id("textures/gui/assemble_button.png"), 0.0F, 2.0F/3.0F, 1.0F, 1.0F);

	public WAssembleButton(Text label) {
		super(label);
	}

	@Override
	public void paint(MatrixStack matrices, int x, int y, int mouseX, int mouseY) {
		TextRenderer t = MinecraftClient.getInstance().textRenderer;
		int color;
		if (!this.isEnabled()) {
			ScreenDrawing.texturedRect(matrices, x - 1, y - 1, 79, 11, DISABLED, 0xFFFFFFFF);
			color = 0xDDDDDD;
		} else if (this.isHovered()) {
			ScreenDrawing.texturedRect(matrices, x - 1, y - 1, 79, 11, HOVERED, 0xFFFFFFFF);
			color = 0xFFFFFF;
		} else {
			ScreenDrawing.texturedRect(matrices, x - 1, y - 1, 79, 11, ENABLED, 0xFFFFFFFF);
			color = 0x222222;
		}
		matrices.push();
		ScreenDrawing.drawString(matrices, this.getLabel().asOrderedText(), HorizontalAlignment.CENTER, x, y, this.width, color);
		matrices.pop();
	}
}
