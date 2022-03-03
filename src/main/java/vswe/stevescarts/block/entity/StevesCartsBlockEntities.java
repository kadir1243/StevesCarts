package vswe.stevescarts.block.entity;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerBlockEntityEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;

import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.block.StevesCartsBlocks;

public final class StevesCartsBlockEntities {
	public static final BlockEntityType<CartAssemblerBlockEntity> CART_ASSEMBLER = register("cart_assembler", FabricBlockEntityTypeBuilder.create(CartAssemblerBlockEntity::new, StevesCartsBlocks.CART_ASSEMBLER).build());

	public static void init() {
		ServerBlockEntityEvents.BLOCK_ENTITY_LOAD.register((blockEntity, world) -> {
			if (blockEntity.getType() == StevesCartsBlockEntities.CART_ASSEMBLER) {
				((CartAssemblerBlockEntity) blockEntity).onLoad(world);
			}
		});
	}

	private static <T extends BlockEntity> BlockEntityType<T> register(String name, BlockEntityType<T> type) {
		return Registry.register(Registry.BLOCK_ENTITY_TYPE, StevesCarts.id(name), type);
	}
}
