package vswe.stevescarts.entity;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.modules.MinecartModule;
import vswe.stevescarts.modules.MinecartModuleType;
import vswe.stevescarts.modules.hull.HullModule;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// TODO
public class ModularMinecartEntity extends AbstractMinecartEntity {
	public List<MinecartModule> modules = new ArrayList<>();

	public ModularMinecartEntity(EntityType<?> entityType, World world) {
		super(entityType, world);
	}

	public ModularMinecartEntity(World world, double x, double y, double z, MinecartModuleType<? extends HullModule> hull) {
		super(StevesCarts.MODULAR_MINECART_ENTITY, world, x, y, z);
		HullModule hullModule = hull.createModule(this);
		this.modules.add(hullModule);
	}

	@Override
	public Packet<?> createSpawnPacket() {
		return SpawnPacket.create(this);
	}

	@Override
	public Type getMinecartType() {
		return null;
	}

	@Override
	public ItemStack getPickBlockStack() {
		return ItemStack.EMPTY; // TODO
	}

	public List<MinecartModule> getModules() {
		return this.modules;
	}

	private void writeModuleData(PacketByteBuf buf) {
		buf.writeVarInt(this.modules.size());
		for (MinecartModule module : this.modules) {
			buf.writeVarInt(MinecartModuleType.REGISTRY.getRawId(module.getType()));
			buf.writeNbt(module.writeNbt(new NbtCompound()));
		}
	}

	private void setModules(List<MinecartModule> modules) {
		this.modules = modules;
	}

	public static class SpawnPacket {
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
				int moduleCount = buf.readVarInt();
				Multimap<MinecartModuleType<?>, NbtCompound> moduleData = ArrayListMultimap.create();
				for (int i = 0; i < moduleCount; i++) {
					MinecartModuleType<?> moduleType = MinecartModuleType.REGISTRY.get(buf.readVarInt());
					NbtCompound nbt = buf.readNbt();
					moduleData.put(moduleType, nbt);
				}
				client.execute(() -> {
					ClientWorld world = MinecraftClient.getInstance().world;
					ModularMinecartEntity entity = StevesCarts.MODULAR_MINECART_ENTITY.create(world);
					if (entity != null && world != null) {
						List<MinecartModule> modules = new ArrayList<>();
						entity.updatePosition(x, y, z);
						entity.updateTrackedPosition(x, y, z);
						entity.setPitch(pitch);
						entity.setYaw(yaw);
						entity.setVelocity(vX, vY, vZ);
						entity.setId(entityID);
						entity.setUuid(entityUUID);
						moduleData.forEach((moduleType, nbt) -> {
							MinecartModule module = moduleType.createModule(entity);
							module.readNbt(nbt);
							modules.add(module);
						});
						entity.setModules(modules);
						world.addEntity(entityID, entity);
					}
				});
			});
		}

		private static Packet<?> create(ModularMinecartEntity entity) {
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
}
