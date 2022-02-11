package vswe.stevescarts.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import vswe.stevescarts.block.StevesCartsBlocks;

public final class StevesCartsBlockEntities {
	public static final BlockEntityType<CartAssemblerBlockEntity> CART_ASSEMBLER = FabricBlockEntityTypeBuilder.create(CartAssemblerBlockEntity::new, StevesCartsBlocks.CART_ASSEMBLER).build();

	public static void init() {
	}
}
