package vswe.stevescarts.screen.widget;

import io.github.cottonmc.cotton.gui.client.ScreenDrawing;
import io.github.cottonmc.cotton.gui.widget.TooltipBuilder;
import io.github.cottonmc.cotton.gui.widget.WWidget;
import io.github.cottonmc.cotton.gui.widget.data.Texture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;
import vswe.stevescarts.StevesCarts;

import java.util.function.LongSupplier;

public class WFluidSlot extends WWidget {
	public static final Texture TEXTURE = new Texture(StevesCarts.id("textures/gui/tank.png"), 0, 0, 1, 1);
	private final LongSupplier current;
	private final long max;

	public WFluidSlot(LongSupplier current, long max) {
		this.current = current;
		this.max = max;
	}

	@Override
	public void paint(MatrixStack matrices, int x, int y, int mouseX, int mouseY) {
		super.paint(matrices, x, y, mouseX, mouseY);
		ScreenDrawing.texturedRect(matrices, x, y, 36, 51, TEXTURE, 0xFFFFFFFF);
	}

	@Override
	public void addTooltip(TooltipBuilder tooltip) {
		long current = this.current.getAsLong();
		tooltip.add(new TranslatableText("tooltip.stevescarts.fluid.max", this.max / 81));
		long currentMb = current / 81;
		if (currentMb > 0) {
			if (currentMb * 81 == current) {
				tooltip.add(new TranslatableText("tooltip.stevescarts.fluid.current", currentMb));
			} else {
				tooltip.add(new TranslatableText("tooltip.stevescarts.fluid.current.extra", currentMb, current - currentMb * 81));
			}
		}
	}
}
