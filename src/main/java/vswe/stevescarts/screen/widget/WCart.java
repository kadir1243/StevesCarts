package vswe.stevescarts.screen.widget;

import io.github.cottonmc.cotton.gui.client.ScreenDrawing;
import io.github.cottonmc.cotton.gui.widget.WWidget;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.MatrixStack;
import vswe.stevescarts.entity.ModularMinecartEntity;

import java.util.function.Supplier;

public class WCart extends WWidget {
	public final Supplier<ModularMinecartEntity> modules;

	public WCart(Supplier<ModularMinecartEntity> modules, int width, int height) {
		this.modules = modules;
		this.setSize(width, height);
	}

	@Environment(EnvType.CLIENT)
	@Override
	public void paint(MatrixStack matrices, int x, int y, int mouseX, int mouseY) {
		ScreenDrawing.coloredRect(matrices, x, y, this.getWidth(), this.getHeight(), 0xFF303030);
		ScreenDrawing.coloredRect(matrices, x + 2, y + 2, this.getWidth() - 4, this.getHeight() - 4, 0xFF000000);
		// TODO
	}
}
