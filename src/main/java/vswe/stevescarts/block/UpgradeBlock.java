package vswe.stevescarts.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class UpgradeBlock extends Block {
	public static final BooleanProperty CONNECTED = BooleanProperty.of("connected");

	public UpgradeBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.getStateManager().getDefaultState().with(CONNECTED, false).with(Properties.FACING, Direction.UP));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder);
		builder.add(CONNECTED, Properties.FACING);
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		if (direction == state.get(Properties.FACING).getOpposite()) {
			return this.getDefaultState();
		}
		if (neighborState.isOf(StevesCartsBlocks.CART_ASSEMBLER)) {
			if (state.get(CONNECTED)) {
				return state;
			} else {
				return this.getDefaultState().with(CONNECTED, true).with(Properties.FACING, direction.getOpposite());
			}
		} else if (state.get(CONNECTED) && world.getBlockState(pos.offset(state.get(Properties.FACING).getOpposite())).isOf(StevesCartsBlocks.CART_ASSEMBLER)) {
			return this.getDefaultState().with(CONNECTED, false).with(Properties.FACING, Direction.UP);
		}
		return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}

	@Nullable
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		if (ctx.getWorld().getBlockState(ctx.getBlockPos()).isOf(StevesCartsBlocks.CART_ASSEMBLER)) {
			return this.getDefaultState().with(CONNECTED, true).with(Properties.FACING, ctx.getPlayerLookDirection());
		}
		return super.getPlacementState(ctx);
	}
}
