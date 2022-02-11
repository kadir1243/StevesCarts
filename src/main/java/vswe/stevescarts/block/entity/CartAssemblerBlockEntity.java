package vswe.stevescarts.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

public class CartAssemblerBlockEntity extends BlockEntity {
	public CartAssemblerBlockEntity(BlockPos pos, BlockState state) {
		super(StevesCartsBlockEntities.CART_ASSEMBLER, pos, state);
	}

	@Override
	public void readNbt(NbtCompound nbt) {
		super.readNbt(nbt);
	}

	@Override
	protected void writeNbt(NbtCompound nbt) {
		super.writeNbt(nbt);
	}
}
