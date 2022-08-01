package vswe.stevescarts.entity;

import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;

public class StevesCartsEntities {
	public static final EntityType<CartEntity> CART = FabricEntityTypeBuilder.create(SpawnGroup.MISC, CartEntity::new).dimensions(EntityDimensions.fixed(1, 1)).build();

	public static void init() {
	}
}
