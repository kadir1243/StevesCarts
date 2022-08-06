package vswe.stevescarts.screen.widget;

import io.github.cottonmc.cotton.gui.client.ScreenDrawing;
import io.github.cottonmc.cotton.gui.widget.TooltipBuilder;
import io.github.cottonmc.cotton.gui.widget.WWidget;
import io.github.cottonmc.cotton.gui.widget.data.Texture;
import reborncore.client.RenderUtil;
import reborncore.common.fluid.FluidUtils;
import reborncore.common.util.Tank;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import vswe.stevescarts.StevesCarts;

public class WFluidSlot extends WWidget {
	public static final Texture TEXTURE = new Texture(StevesCarts.id("textures/gui/tank.png"), 0, 0, 1, 1);
	private final Tank tank;

	public WFluidSlot(Tank tank) {
		this.tank = tank;
		this.setSize(36, 51);
	}

	@Override
	public void paint(MatrixStack matrices, int x, int y, int mouseX, int mouseY) {
		super.paint(matrices, x, y, mouseX, mouseY);
		RenderUtil.
		renderGuiTank(this.tank.getFluidInstance(), this.tank.getFluidValueCapacity(), this.tank.getFluidAmount(), x + 1, y + 1, 100.0, 34, 49);

		ScreenDrawing.texturedRect(matrices, x, y, 36, 51, TEXTURE, 0xFFFFFFFF);
	}

	@Override
	public void addTooltip(TooltipBuilder tooltip) {
		tooltip.add(Text.translatable("tooltip.stevescarts.fluid.max").append(this.tank.getFluidValueCapacity().toString()));
		tooltip.add(Text.translatable("tooltip.stevescarts.fluid.current").append(this.tank.getFluidAmount().toString()));
		if (!this.tank.isEmpty()) {
			tooltip.add(Text.translatable("tooltip.stevescarts.fluid.name").append(FluidUtils.getFluidName(this.tank.getFluidInstance())).formatted(Formatting.GRAY));
		}
	}
}
