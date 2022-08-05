package vswe.stevescarts.entity.network;

import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.entity.CartEntity;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class CartUpdateS2CPacket {
	public static final Identifier ID = StevesCarts.id("update_modules");

	@Environment(EnvType.CLIENT)
	public static void init() {
		ClientPlayNetworking.registerGlobalReceiver(ID, (client, handler, buf, responseSender) -> {
			int entityID = buf.readVarInt();
			NbtCompound moduleData = buf.readNbt();
			client.execute(() -> {
				Entity entity = MinecraftClient.getInstance().world.getEntityById(entityID);
				if (entity == null) {
					StevesCarts.LOGGER.error("Received update packet for non-existent entity with id {}", entityID);
					return;
				} else if (!(entity instanceof CartEntity)) {
					StevesCarts.LOGGER.error("Received update packet for non-cart entity with id {}", entityID);
					return;
				}
				CartEntity cart = (CartEntity) entity;
				cart.readModuleData(moduleData);
			});
		});
	}

	public static void sendToTrackers(CartEntity entity) {
		PacketByteBuf buf = PacketByteBufs.create();
		buf.writeVarInt(entity.getId());
		NbtCompound nbt = new NbtCompound();
		entity.writeModuleData(nbt);
		buf.writeNbt(nbt);
		PlayerLookup.tracking(entity).forEach(player -> ServerPlayNetworking.send(player, ID, buf));
	}
}
