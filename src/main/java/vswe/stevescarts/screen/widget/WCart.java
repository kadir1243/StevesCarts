package vswe.stevescarts.screen.widget;

import io.github.cottonmc.cotton.gui.client.ScreenDrawing;
import io.github.cottonmc.cotton.gui.widget.WWidget;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import vswe.stevescarts.item.ModularMinecartItem;
import vswe.stevescarts.modules.MinecartModule;
import vswe.stevescarts.modules.MinecartModuleType;

public class WCart extends WWidget {
	public static boolean renderingCart = false;
	private final Supplier<List<MinecartModuleType<?>>> modules;
	private ItemStack stack = ItemStack.EMPTY;
	private final List<MinecartModule> modules2 = new ArrayList<>();

	public WCart(Supplier<List<MinecartModuleType<?>>> modules, int width, int height) {
		this.modules = modules;
		this.setSize(width, height);
	}

	@Environment(EnvType.CLIENT)
	@Override
	public void paint(MatrixStack matrices, int x, int y, int mouseX, int mouseY) {
		ScreenDrawing.coloredRect(matrices, x, y, this.getWidth(), this.getHeight(), 0xFF303030);
		ScreenDrawing.coloredRect(matrices, x + 2, y + 2, this.getWidth() - 4, this.getHeight() - 4, 0xFF000000);
		MinecraftClient mc = MinecraftClient.getInstance();
		ItemRenderer renderer = mc.getItemRenderer();
		renderer.zOffset = 100f;
		renderingCart = true;
		renderer.renderInGui(this.stack, x + getWidth() / 2 - 9, y + getHeight() / 2 - 9);
		renderingCart = false;
		renderer.zOffset = 0f;
	}

	public void markDirty() {
		this.stack = ModularMinecartItem.create(this.modules.get().stream().map(MinecartModuleType::createModule).map(MinecartModule.class::cast).toList());
	}
}
