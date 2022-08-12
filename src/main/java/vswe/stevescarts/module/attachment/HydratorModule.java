package vswe.stevescarts.module.attachment;

import java.util.stream.Stream;

import org.jetbrains.annotations.Nullable;
import vswe.stevescarts.entity.CartEntity;
import vswe.stevescarts.module.CartModule;
import vswe.stevescarts.module.ModuleType;
import vswe.stevescarts.module.Worker;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;

public class HydratorModule extends CartModule implements Worker {
	public static final FluidVariant WATER = FluidVariant.of(Fluids.WATER);

	public HydratorModule(@Nullable CartEntity entity, ModuleType<?> type) {
		super(entity, type);
	}

	@Override
	public void work() {
		World world = this.getEntity().world;
		BlockPos on = this.getRailPos().down();
		Stream.of(Direction.EAST, Direction.WEST, Direction.SOUTH, Direction.NORTH).forEach(dir -> {
			BlockPos next = on.toImmutable().offset(dir);
			BlockState state = world.getBlockState(next);
			if (!state.isOf(Blocks.FARMLAND)) {
				return;
			}
			int currentMoisture = state.get(FarmlandBlock.MOISTURE);
			if (currentMoisture == FarmlandBlock.MAX_MOISTURE) {
				return;
			}
			int waterCost = (FarmlandBlock.MAX_MOISTURE - currentMoisture) * 81;
			Transaction transaction = Transaction.openOuter();
			long extracted = this.getEntity().getFluidStorage().extract(WATER, waterCost, transaction);
			transaction.addCloseCallback((t, res) -> {
				if (extracted == waterCost) {
					world.setBlockState(next, state.with(FarmlandBlock.MOISTURE, FarmlandBlock.MAX_MOISTURE), Block.NOTIFY_LISTENERS);
				}
			});
			transaction.commit();
		});
	}
}
