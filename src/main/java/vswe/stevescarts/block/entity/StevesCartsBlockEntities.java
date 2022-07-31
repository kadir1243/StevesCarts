package vswe.stevescarts.block.entity;

import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.block.StevesCartsBlocks;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;

public final class StevesCartsBlockEntities {
	public static final BlockEntityType<CartAssemblerBlockEntity> CART_ASSEMBLER = register("cart_assembler", FabricBlockEntityTypeBuilder.create(CartAssemblerBlockEntity::new, StevesCartsBlocks.CART_ASSEMBLER).build(null));

	public static void init() {
	}

	private static <T extends BlockEntity> BlockEntityType<T> register(String name, BlockEntityType<T> type) {
		return Registry.register(Registry.BLOCK_ENTITY_TYPE, StevesCarts.id(name), type);
	}
}
