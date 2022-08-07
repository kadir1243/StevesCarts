package vswe.stevescarts.block;

import org.jetbrains.annotations.Nullable;
import vswe.stevescarts.block.entity.CartAssemblerBlockEntity;
import vswe.stevescarts.block.entity.StevesCartsBlockEntities;
import vswe.stevescarts.screen.CartAssemblerHandler;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.InventoryProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;

public class CartAssemblerBlock extends BlockWithEntity implements InventoryProvider {
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
		return new ExtendedScreenHandlerFactory() {
			@Override
			public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
				buf.writeBlockPos(pos);
			}

			@Override
			public Text getDisplayName() {
				return CartAssemblerBlockEntity.NAME;
			}

			@Override
			public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
				return new CartAssemblerHandler(syncId, inv, ScreenHandlerContext.create(world, pos), (CartAssemblerBlockEntity) world.getBlockEntity(pos));
			}
		};
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

	@Override
	public SidedInventory getInventory(BlockState state, WorldAccess world, BlockPos pos) {
		return world.getBlockEntity(pos, StevesCartsBlockEntities.CART_ASSEMBLER).orElseThrow();
	}

	@Override
	@Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
		return world.isClient ? CartAssemblerBlock.checkType(type, StevesCartsBlockEntities.CART_ASSEMBLER, CartAssemblerBlockEntity::clientTick) : null;
	}
}
