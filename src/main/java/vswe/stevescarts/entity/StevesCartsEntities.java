package vswe.stevescarts.entity;

import vswe.stevescarts.StevesCarts;

import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.registry.Registry;

import net.fabricmc.fabric.api.lookup.v1.entity.EntityApiLookup;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;

public class StevesCartsEntities {
	public static final EntityType<CartEntity> CART = FabricEntityTypeBuilder.<CartEntity>create(SpawnGroup.MISC, CartEntity::new).dimensions(EntityDimensions.fixed(1, 1)).build();
	public static final EntityApiLookup<Storage<FluidVariant>, Void> ENTITY_FLUID = EntityApiLookup.get(StevesCarts.id("entity_fluid_storage"), Storage.asClass(), Void.class);

	public static void init() {
		Registry.register(Registry.ENTITY_TYPE, StevesCarts.id("cart"), CART);
		ENTITY_FLUID.registerForType((entity, v) -> entity.getFluidStorage(), CART);
	}
}
