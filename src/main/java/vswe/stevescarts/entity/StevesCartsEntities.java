package vswe.stevescarts.entity;

import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import net.fabricmc.fabric.api.lookup.v1.entity.EntityApiLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;

import vswe.stevescarts.StevesCarts;

public class StevesCartsEntities {
	public static EntityType<ModularMinecartEntity> MODULAR_MINECART_ENTITY = FabricEntityTypeBuilder.<ModularMinecartEntity>create(SpawnGroup.MISC, ModularMinecartEntity::new).dimensions(EntityDimensions.fixed(1, 1)).build();
	public static final EntityApiLookup<Storage<FluidVariant>, Void> ENTITY_FLUID = EntityApiLookup.get(StevesCarts.id("entity_fluid_storage"), Storage.asClass(), Void.class);
	public static final Identifier PACKET_CART_INVENTORY = StevesCarts.id("cart_inventory");

	public static void init() {
		Registry.register(Registry.ENTITY_TYPE, StevesCarts.id("cart"), MODULAR_MINECART_ENTITY);
		ENTITY_FLUID.registerForType((entity, v) -> entity.getFluidStorage(), MODULAR_MINECART_ENTITY);
		ServerPlayNetworking.registerGlobalReceiver(PACKET_CART_INVENTORY, (server, player, handler, buf, responseSender) -> server.execute(() -> ((ModularMinecartEntity) player.getVehicle()).openScreen(player)));
	}
}
