package vswe.stevescarts.client.render.module.storage;

import alexiil.mc.lib.attributes.fluid.amount.FluidAmount;
import alexiil.mc.lib.attributes.fluid.render.DefaultFluidVolumeRenderer;
import alexiil.mc.lib.attributes.fluid.render.FluidRenderFace;
import alexiil.mc.lib.attributes.fluid.volume.FluidKeys;
import reborncore.common.util.Tank;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Direction;

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
