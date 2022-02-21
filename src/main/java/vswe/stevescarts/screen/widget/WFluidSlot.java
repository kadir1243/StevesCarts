package vswe.stevescarts.screen.widget;

import io.github.cottonmc.cotton.gui.client.ScreenDrawing;
import io.github.cottonmc.cotton.gui.widget.TooltipBuilder;
import io.github.cottonmc.cotton.gui.widget.WWidget;
import io.github.cottonmc.cotton.gui.widget.data.Texture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;
import reborncore.common.util.Tank;
import vswe.stevescarts.StevesCarts;

public class WFluidSlot extends WWidget {
	public static final Texture TEXTURE = new Texture(StevesCarts.id("textures/gui/tank.png"), 0, 0, 1, 1);
	private final Tank tank;

	public WFluidSlot(Tank tank) {
		this.tank = tank;
	}

	@Override
	public void paint(MatrixStack matrices, int x, int y, int mouseX, int mouseY) {
		super.paint(matrices, x, y, mouseX, mouseY);
		ScreenDrawing.texturedRect(matrices, x, y, 36, 51, TEXTURE, 0xFFFFFFFF);
	}

	@Override
	public void addTooltip(TooltipBuilder tooltip) {
		tooltip.add(new TranslatableText("tooltip.stevescarts.fluid.max").append(this.tank.getFluidValueCapacity().toString()));
		tooltip.add(new TranslatableText("tooltip.stevescarts.fluid.current").append(this.tank.getFluidAmount().toString()));
	}
}
