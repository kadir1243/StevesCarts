package vswe.stevescarts.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class UpgradeBlock extends Block {
	public static final BooleanProperty CONNECTED = BooleanProperty.of("connected");
	private static final VoxelShape DISCONNECTED_SHAPE;
	private static final VoxelShape[] DIRECTIONAL_SHAPES;

	public UpgradeBlock(Settings settings) {
		super(settings.nonOpaque());
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
		if (ctx.getWorld().getBlockState(ctx.getBlockPos()).isOf(StevesCartsBlocks.CART_ASSEMBLER) || ctx.getWorld().getBlockState(ctx.getBlockPos().offset(ctx.getPlayerLookDirection())).isOf(StevesCartsBlocks.CART_ASSEMBLER)) {
			return this.getDefaultState().with(CONNECTED, true).with(Properties.FACING, ctx.getPlayerLookDirection().getOpposite());
		}
		return super.getPlacementState(ctx);
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		if (state.get(CONNECTED)) {
			return DIRECTIONAL_SHAPES[state.get(Properties.FACING).getId()];
		}
		return DISCONNECTED_SHAPE;
	}
	
	static {
		double margin = 0.1875;
		double thickness = 0.125;
		DIRECTIONAL_SHAPES = new VoxelShape[6];
		DISCONNECTED_SHAPE = VoxelShapes.cuboid(margin, thickness, margin, 1 - margin, 1 - thickness, 1 - margin);
		DIRECTIONAL_SHAPES[Direction.DOWN.getId()] = VoxelShapes.cuboid(margin, 0, margin, 1 - margin, thickness, 1 - margin);
		DIRECTIONAL_SHAPES[Direction.UP.getId()] = VoxelShapes.cuboid(margin, 1 - thickness, margin, 1 - margin, 1, 1 - margin);
		DIRECTIONAL_SHAPES[Direction.WEST.getId()] = VoxelShapes.cuboid(0, margin, margin, thickness, 1 - margin, 1 - margin);
		DIRECTIONAL_SHAPES[Direction.EAST.getId()] = VoxelShapes.cuboid(1 - thickness, margin, margin, 1, 1 - margin, 1 - margin);
		DIRECTIONAL_SHAPES[Direction.NORTH.getId()] = VoxelShapes.cuboid(margin, margin, 0, 1 - margin, 1 - margin, thickness);
		DIRECTIONAL_SHAPES[Direction.SOUTH.getId()] = VoxelShapes.cuboid(margin, margin, 1 - thickness, 1 - margin, 1 - margin, 1);
	}
}
