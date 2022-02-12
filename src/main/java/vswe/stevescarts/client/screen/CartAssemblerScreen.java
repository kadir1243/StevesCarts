package vswe.stevescarts.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.screen.CartAssemblerHandler;

@Environment(EnvType.CLIENT)
public class CartAssemblerScreen extends HandledScreen<CartAssemblerHandler> {
	private static final Identifier LEFT_BACKGROUND = StevesCarts.id("textures/gui/garagepart1.png");
	private static final Identifier RIGHT_BACKGROUND = StevesCarts.id("textures/gui/garagepart2.png");
	private static final int[] ASSEMBLER_RECT = { 390, 160, 80, 11 };

	public CartAssemblerScreen(CartAssemblerHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title);
		this.backgroundWidth = 512;
		this.backgroundHeight = 256;
		this.setZOffset(100);
	}

	@Override
	protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
		matrices.push();
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		Window window = MinecraftClient.getInstance().getWindow();
		int xCenter = window.getScaledWidth() / 2;
		int yCenter = window.getScaledHeight() / 2;
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.setShaderTexture(0, LEFT_BACKGROUND);
		drawTexture(matrices, (xCenter - 256), (yCenter - 128) - 20, 0, 0, 256, 256);
		RenderSystem.setShaderTexture(0, RIGHT_BACKGROUND);
		drawTexture(matrices, (xCenter), (yCenter - 128) - 20, 0, 0, 256, 256);
		matrices.pop();
	}

	@Override
	public void renderBackground(MatrixStack matrices, int vOffset) {
		super.renderBackground(matrices, vOffset);
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		renderBackground(matrices);
		super.render(matrices, mouseX, mouseY, delta);
		drawMouseoverTooltip(matrices, mouseX, mouseY);
	}

	@Override
	protected void init() {
		super.init();
	}
}
