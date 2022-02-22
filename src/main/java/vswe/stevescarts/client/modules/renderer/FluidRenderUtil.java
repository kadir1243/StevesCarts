package vswe.stevescarts.client.modules.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.math.Vec3f;
import reborncore.client.RenderUtil;
import reborncore.common.util.Tank;

public class FluidRenderUtil {
	public static void renderFluidCuboid(MatrixStack matrices, Tank tank, float x, float y, float z, float sizeX, float sizeY, float sizeZ) {
		if (tank.isEmpty()) {
			return;
		}
		Fluid mcFluid = tank.getFluid();
		FluidRenderHandler renderHandler = FluidRenderHandlerRegistry.INSTANCE.get(mcFluid);
		Sprite still = renderHandler.getFluidSprites(null, null, mcFluid.getDefaultState())[0];
		float filled = tank.getAmount() / (float) tank.getCapacity();
		matrices.push();
		matrices.translate(x, y + sizeY * (1.0f - filled) / 2.0f, z);
		RenderUtil.bindBlockTexture();
		int color = renderHandler.getFluidColor(null, null, mcFluid.getDefaultState());
		float r = (float) (color >> 16 & 255) / 255.0f;
		float g = (float) (color >> 8 & 255) / 255.0f;
		float b = (float) (color & 255) / 255.0f;
		RenderSystem.setShaderColor(r, g, b, 1.0f);
		matrices.scale(0.5F, 0.5F, 0.5F);
		renderCuboid(still, matrices, 2 * sizeX, 2 * sizeY, 2 * sizeZ, r, g, b);
		matrices.pop();
		RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
	}

	private static void renderCuboid(Sprite sprite, MatrixStack matrices, float sizeX, float sizeY, float sizeZ, float r, float g, float b) {
		renderFace(sprite, matrices, sizeX, sizeZ, 0, 90F, 0F, -(float) (sizeY / 2), 0F, r, g, b);
		renderFace(sprite, matrices, sizeX, sizeZ, 0, -90F, 0F, sizeY / 2, 0F, r, g, b);
		renderFace(sprite, matrices, sizeX, sizeY, 0, 0, 0F, 0F, sizeZ / 2, r, g, b);
		renderFace(sprite, matrices, sizeX, sizeY, 180F, 0F, 0F, 0F, -(float) (sizeZ / 2), r, g, b);
		renderFace(sprite, matrices, sizeZ, sizeY, 90F, 0, sizeX / 2, 0F, 0F, r, g, b);
		renderFace(sprite, matrices, sizeZ, sizeY, -90F, 0F, -(float) (sizeX / 2), 0F, 0F, r, g, b);
	}

	private static void renderFace(Sprite icon, MatrixStack matrices, double totalTargetW, double totalTargetH, float yaw, float roll, float offX, float offY, float offZ, float r, float g, float b) {
		matrices.push();
		matrices.translate(offX, offY, offZ);
		matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(yaw));
		matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(roll));
		Tessellator tess = Tessellator.getInstance();
		BufferBuilder buffer = tess.getBuffer();
		float srcX = icon.getMinU();
		float srcY = icon.getMaxU();
		float srcW = icon.getMaxU() - srcX;
		float srcH = icon.getMaxV() - srcY;
		float d = 0.001F;
		float currentTargetX = 0F;
		RenderLayer.getTranslucent().startDrawing();
		while (totalTargetW - currentTargetX > d * 2) {
			float currentTargetW = (float) Math.min(totalTargetW - currentTargetX, 1);
			float currentTargetY = 0F;
			while (totalTargetH - currentTargetY > d * 2) {
				float currentTargetH = (float) Math.min(totalTargetH - currentTargetY, 1);

				buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL);
				buffer.vertex(currentTargetX - totalTargetW / 2.0, currentTargetY - totalTargetH / 2.0, 0.0)
						.color(r, g, b, 1.0F)
						.texture(srcX, srcY)
						.light(LightmapTextureManager.MAX_LIGHT_COORDINATE)
						.normal(0.0f, 1.0f, 0.0f)
						.next();
				buffer.vertex(currentTargetX + currentTargetW - totalTargetW / 2.0, currentTargetY - totalTargetH / 2.0, 0.0)
						.color(r, g, b, 1.0F)
						.texture(srcX + srcW * currentTargetW, srcY)
						.light(LightmapTextureManager.MAX_LIGHT_COORDINATE)
						.normal(0.0f, 1.0f, 0.0f)
						.next();
				buffer.vertex(currentTargetX + currentTargetW - totalTargetW / 2.0, currentTargetY + currentTargetH - totalTargetH / 2.0, 0.0)
						.color(r, g, b, 1.0F)
						.texture(srcX + srcW * currentTargetW, srcY + srcH * currentTargetH)
						.light(LightmapTextureManager.MAX_LIGHT_COORDINATE)
						.normal(0.0f, 1.0f, 0.0f)
						.next();
				buffer.vertex(currentTargetX - totalTargetW / 2.0, currentTargetY + currentTargetH - totalTargetH / 2.0, 0.0)
						.color(r, g, b, 1.0F)
						.texture(srcX, srcY + srcH * currentTargetH)
						.light(LightmapTextureManager.MAX_LIGHT_COORDINATE)
						.normal(0.0f, 1.0f, 0.0f)
						.next();
				tess.draw();
				currentTargetY += currentTargetH - d;
			}
			currentTargetX += currentTargetW - d;
		}
		RenderLayer.getTranslucent().endDrawing();
		matrices.pop();
	}
}
