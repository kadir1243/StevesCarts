package vswe.stevescarts.client.modules.renderer;

import alexiil.mc.lib.attributes.fluid.amount.FluidAmount;
import alexiil.mc.lib.attributes.fluid.render.DefaultFluidVolumeRenderer;
import alexiil.mc.lib.attributes.fluid.render.FluidRenderFace;
import alexiil.mc.lib.attributes.fluid.render.FluidVolumeRenderer;
import alexiil.mc.lib.attributes.fluid.volume.FluidKeys;
import alexiil.mc.lib.attributes.fluid.volume.FluidVolume;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3f;
import reborncore.client.RenderUtil;
import reborncore.common.util.Tank;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class FluidRenderUtil {
	private static final EnumSet<Direction> ALL_DIRECTIONS = EnumSet.allOf(Direction.class);

	public static void renderFluidCuboid(MatrixStack matrices, Tank tank, float x, float y, float z, float sizeX, float sizeY, float sizeZ) {
		if (tank.isEmpty()) {
			return;
		}
		List<FluidRenderFace> faces = new ArrayList<>();
		float tankPercent = tank.getAmount() / (float) tank.getCapacity();
		FluidRenderFace.appendCuboid(x / 16.0f, y / 16.0f, z / 16.0f, (x + sizeX) / 16.0f, (y + sizeY) / 16.0f * tankPercent, (z + sizeZ) / 16.0f, 1, ALL_DIRECTIONS, faces);
		DefaultFluidVolumeRenderer.INSTANCE.render(FluidKeys.get(tank.getFluid()).withAmount(FluidAmount.of(tank.getAmount(), tank.getCapacity())), faces, MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers(), matrices);
	}
}
