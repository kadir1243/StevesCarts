package vswe.stevescarts.block;

import org.jetbrains.annotations.Nullable;
import vswe.stevescarts.block.entity.CartAssemblerBlockEntity;
import vswe.stevescarts.block.entity.StevesCartsBlockEntities;
import vswe.stevescarts.screen.CartAssemblerHandler;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class CartAssemblerBlock extends BlockWithEntity {
	public CartAssemblerBlock(Settings settings) {
		super(settings);
	}

	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return StevesCartsBlockEntities.CART_ASSEMBLER.instantiate(pos, state);
	}

	@Nullable
	@Override
	public NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
		return new SimpleNamedScreenHandlerFactory((syncId, playerInventory, playerEntity) -> new CartAssemblerHandler(syncId, playerInventory, ScreenHandlerContext.create(world, pos)), CartAssemblerBlockEntity.NAME);
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (world.isClient) {
			return ActionResult.SUCCESS;
		}
		player.openHandledScreen(state.createScreenHandlerFactory(world, pos));
		return ActionResult.CONSUME;
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}

	@Override
	public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
		BlockEntity b = world.getBlockEntity(pos);
		if (!state.isOf(newState.getBlock()) && b instanceof CartAssemblerBlockEntity be) {
			be.scatter(world, pos);
		}
		super.onStateReplaced(state, world, pos, newState, moved);
	}
}
