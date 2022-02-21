package vswe.stevescarts.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import vswe.stevescarts.advancement.StevesCartsStats;
import vswe.stevescarts.block.entity.StevesCartsBlockEntities;
import vswe.stevescarts.screen.CartAssemblerHandler;

public class CartAssemblerBlock extends BlockWithEntity {
	protected CartAssemblerBlock(Settings settings) {
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
		return new SimpleNamedScreenHandlerFactory((syncId, inv, player) -> new CartAssemblerHandler(syncId, inv, ScreenHandlerContext.create(world, pos)), new TranslatableText("screen.stevescarts.cart_assembler"));
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (world.isClient) {
			return ActionResult.SUCCESS;
		}
		player.openHandledScreen(state.createScreenHandlerFactory(world, pos));
		player.increaseStat(StevesCartsStats.INTERACT_WITH_CART_ASSEMBLER, 1);
		return ActionResult.CONSUME;
	}

	@Override
	public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
		if (block instanceof UpgradeBlock) {
			// TODO
		}
	}
}
