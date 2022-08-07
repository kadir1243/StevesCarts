package vswe.stevescarts.entity;

import vswe.stevescarts.StevesCarts;

import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.registry.Registry;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;

public class StevesCartsEntities {
	public static final EntityType<CartEntity> CART = FabricEntityTypeBuilder.<CartEntity>create(SpawnGroup.MISC, CartEntity::new).dimensions(EntityDimensions.fixed(1, 1)).build();

	public static void init() {
		Registry.register(Registry.ENTITY_TYPE, StevesCarts.id("cart"), CART);
	}
}
