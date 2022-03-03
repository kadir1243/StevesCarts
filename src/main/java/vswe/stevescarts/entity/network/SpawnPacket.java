package vswe.stevescarts.entity.network;

import com.google.common.collect.Multimap;

import java.util.UUID;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.entity.ModularMinecartEntity;
import vswe.stevescarts.entity.StevesCartsEntities;
import vswe.stevescarts.modules.MinecartModuleType;

public class SpawnPacket {
	public static final Identifier ID = StevesCarts.id("spawn_minecart");

	@Environment(EnvType.CLIENT)
	public static void init() {
		ClientPlayNetworking.registerGlobalReceiver(ID, (client, handler, buf, responseSender) -> {
			UUID entityUUID = buf.readUuid();
			int entityID = buf.readVarInt();
			double x = buf.readDouble();
			double y = buf.readDouble();
			double z = buf.readDouble();
			float pitch = (float) (buf.readByte() * 360) / 256.0F;
			float yaw = (float) (buf.readByte() * 360) / 256.0F;
			double vX = buf.readDouble();
			double vY = buf.readDouble();
			double vZ = buf.readDouble();
			Multimap<MinecartModuleType<?>, NbtCompound> moduleData = ModularMinecartEntity.readModuleData(buf);
			client.execute(() -> {
				ClientWorld world = MinecraftClient.getInstance().world;
				ModularMinecartEntity entity = StevesCartsEntities.MODULAR_MINECART_ENTITY.create(world);
				if (entity != null && world != null) {
					entity.updatePosition(x, y, z);
					entity.updateTrackedPosition(x, y, z);
					entity.setPitch(pitch);
					entity.setYaw(yaw);
					entity.setVelocity(vX, vY, vZ);
					entity.setId(entityID);
					entity.setUuid(entityUUID);
					entity.setModuleData(moduleData, false);
					world.addEntity(entityID, entity);
				}
			});
		});
	}

	public static Packet<?> create(ModularMinecartEntity entity) {
		PacketByteBuf buf = PacketByteBufs.create();
		buf.writeUuid(entity.getUuid());
		buf.writeVarInt(entity.getId());
		buf.writeDouble(entity.getX());
		buf.writeDouble(entity.getY());
		buf.writeDouble(entity.getZ());
		buf.writeByte(MathHelper.floor(entity.getPitch() * 256.0F / 360.0F));
		buf.writeByte(MathHelper.floor(entity.getYaw() * 256.0F / 360.0F));
		buf.writeDouble(entity.getVelocity().x);
		buf.writeDouble(entity.getVelocity().y);
		buf.writeDouble(entity.getVelocity().z);
		entity.writeModuleData(buf);
		return ServerPlayNetworking.createS2CPacket(ID, buf);
	}
}
