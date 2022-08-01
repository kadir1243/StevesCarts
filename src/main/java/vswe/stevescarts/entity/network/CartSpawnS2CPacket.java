package vswe.stevescarts.entity.network;

import java.util.UUID;

import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.entity.CartEntity;
import vswe.stevescarts.entity.StevesCartsEntities;

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

public class CartSpawnS2CPacket {
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
			NbtCompound moduleData = buf.readNbt();
			client.execute(() -> {
				ClientWorld world = MinecraftClient.getInstance().world;
				CartEntity entity = StevesCartsEntities.CART.create(world);
				if (entity != null && world != null) {
					entity.updatePosition(x, y, z);
					entity.updateTrackedPosition(x, y, z);
					entity.setPitch(pitch);
					entity.setYaw(yaw);
					entity.setVelocity(vX, vY, vZ);
					entity.setId(entityID);
					entity.setUuid(entityUUID);
					entity.readModuleData(moduleData);
					world.addEntity(entityID, entity);
				}
			});
		});
	}

	public static Packet<?> create(CartEntity entity) {
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
		NbtCompound nbt = new NbtCompound();
		entity.writeModuleData(nbt);
		buf.writeNbt(nbt);
		return ServerPlayNetworking.createS2CPacket(ID, buf);
	}
}

