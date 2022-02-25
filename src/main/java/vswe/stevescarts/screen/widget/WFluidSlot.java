package vswe.stevescarts.screen.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.cottonmc.cotton.gui.client.ScreenDrawing;
import io.github.cottonmc.cotton.gui.widget.TooltipBuilder;
import io.github.cottonmc.cotton.gui.widget.WWidget;
import io.github.cottonmc.cotton.gui.widget.data.Texture;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import reborncore.client.RenderUtil;
import reborncore.common.fluid.FluidUtils;
import reborncore.common.fluid.FluidValue;
import reborncore.common.fluid.container.FluidInstance;
import reborncore.common.util.Tank;
import vswe.stevescarts.StevesCarts;

public class WFluidSlot extends WWidget {
	public static final Texture TEXTURE = new Texture(StevesCarts.id("textures/gui/tank.png"), 0, 0, 1, 1);
	private final Tank tank;

	public WFluidSlot(Tank tank) {
		this.tank = tank;
		this.setSize(36, 51);
	}

	// TODO: find out why the one in reborn core isnt working (and possibly fix it there so that this one doesnt need to be here)
	public static void renderGuiTank(FluidInstance fluid, FluidValue capacity, FluidValue amount, double x, double y, double zLevel,
									 double width, double height) {
		if (fluid == null || fluid.getFluid() == null || fluid.getAmount().lessThanOrEqual(FluidValue.EMPTY)) {
			return;
		}

		Sprite icon = RenderUtil.getStillTexture(fluid);
		if (icon == null) {
			return;
		}

		int renderAmount = (int) Math.max(Math.min(height, amount.getRawValue() * height / capacity.getRawValue()), 1);
		int posY = (int) (y + height - renderAmount);

		RenderUtil.bindBlockTexture();
		int color = FluidRenderHandlerRegistry.INSTANCE.get(fluid.getFluid()).getFluidColor(null, null, fluid.getFluid().getDefaultState());
		float r = (float) (color >> 16 & 0xFF) / 255.0F;
		float g = (float) (color >> 8 & 0xFF) / 255.0F;
		float b = (float) (color & 0xFF) / 255.0F;
		RenderSystem.setShaderColor(r, g, b, 1.0F);

		RenderSystem.enableBlend();
		RenderLayer.getTranslucent().startDrawing();
		for (int i = 0; i < width; i += 16) {
			for (int j = 0; j < renderAmount; j += 16) {
				int drawWidth = (int) Math.min(width - i, 16);
				int drawHeight = Math.min(renderAmount - j, 16);

				int drawX = (int) (x + i);
				int drawY = posY + j;

				float minU = icon.getMinU();
				float maxU = icon.getMaxU();
				float minV = icon.getMinV();
				float maxV = icon.getMaxV();

				Tessellator tessellator = Tessellator.getInstance();
				BufferBuilder tes = tessellator.getBuffer();
				tes.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL);
				tes.vertex(drawX, drawY + drawHeight, 0)
						.color(r, g, b, 1.0F)
						.texture(minU, minV + (maxV - minV) * drawHeight / 16F)
						.light(LightmapTextureManager.MAX_LIGHT_COORDINATE)
						.normal(0, 1, 0)
						.next();
				tes.vertex(drawX + drawWidth, drawY + drawHeight, 0)
						.color(r, g, b, 1.0F)
						.texture(minU + (maxU - minU) * drawWidth / 16F, minV + (maxV - minV) * drawHeight / 16F)
						.light(LightmapTextureManager.MAX_LIGHT_COORDINATE)
						.normal(0, 1, 0)
						.next();
				tes.vertex(drawX + drawWidth, drawY, 0)
						.color(r, g, b, 1.0F)
						.texture(minU + (maxU - minU) * drawWidth / 16F, minV)
						.light(LightmapTextureManager.MAX_LIGHT_COORDINATE)
						.normal(0, 1, 0)
						.next();
				tes.vertex(drawX, drawY, 0)
						.color(r, g, b, 1.0F)
						.texture(minU, minV)
						.light(LightmapTextureManager.MAX_LIGHT_COORDINATE)
						.normal(0, 1, 0)
						.next();
				tessellator.draw();
			}
		}
		RenderLayer.getTranslucent().endDrawing();
		RenderSystem.disableBlend();
	}

	@Override
	public void paint(MatrixStack matrices, int x, int y, int mouseX, int mouseY) {
		super.paint(matrices, x, y, mouseX, mouseY);
		/*RenderUtil.*/
		renderGuiTank(this.tank.getFluidInstance(), this.tank.getFluidValueCapacity(), this.tank.getFluidAmount(), x + 1, y + 1, 100.0, 34, 49);

		ScreenDrawing.texturedRect(matrices, x, y, 36, 51, TEXTURE, 0xFFFFFFFF);
	}

	@Override
	public void addTooltip(TooltipBuilder tooltip) {
		tooltip.add(new TranslatableText("tooltip.stevescarts.fluid.max").append(this.tank.getFluidValueCapacity().toString()));
		tooltip.add(new TranslatableText("tooltip.stevescarts.fluid.current").append(this.tank.getFluidAmount().toString()));
		if (!this.tank.isEmpty()) {
			tooltip.add(new TranslatableText("tooltip.stevescarts.fluid.name").append(FluidUtils.getFluidName(this.tank.getFluidInstance())).formatted(Formatting.GRAY));
		}
	}
}
