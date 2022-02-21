package vswe.stevescarts.entity.network;

import com.google.common.collect.Multimap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.entity.ModularMinecartEntity;
import vswe.stevescarts.modules.MinecartModuleType;

public class UpdatePacket {
	public static final Identifier ID = StevesCarts.id("update_modules");

	@Environment(EnvType.CLIENT)
	public static void init() {
		ClientPlayNetworking.registerGlobalReceiver(ID, (client, handler, buf, responseSender) -> {
			int entityID = buf.readVarInt();
			Multimap<MinecartModuleType<?>, NbtCompound> moduleData = ModularMinecartEntity.readModuleData(buf);
			client.execute(() -> {
				Entity entity = MinecraftClient.getInstance().world.getEntityById(entityID);
				if (entity == null) {
					StevesCarts.LOGGER.error("Received update packet for non-existent entity with id {}", entityID);
					return;
				} else if (!(entity instanceof ModularMinecartEntity)) {
					StevesCarts.LOGGER.error("Received update packet for non-cart entity with id {}", entityID);
					return;
				}
				ModularMinecartEntity cart = (ModularMinecartEntity) entity;
				cart.setModuleData(moduleData, false);
			});
		});
	}

	public static void sendToTrackers(ModularMinecartEntity entity) {
		PacketByteBuf buf = PacketByteBufs.create();
		buf.writeVarInt(entity.getId());
		entity.writeModuleData(buf);
		PlayerLookup.tracking(entity).forEach(player -> ServerPlayNetworking.send(player, ID, buf));
	}
}
