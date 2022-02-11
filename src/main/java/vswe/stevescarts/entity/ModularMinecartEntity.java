package vswe.stevescarts.entity;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.collection.Int2ObjectBiMap;
import net.minecraft.world.World;
import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.entity.network.SpawnPacket;
import vswe.stevescarts.entity.network.UpdatePacket;
import vswe.stevescarts.modules.MinecartModule;
import vswe.stevescarts.modules.MinecartModuleType;
import vswe.stevescarts.modules.ModuleStorage;
import vswe.stevescarts.modules.hull.HullModule;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

// TODO
public class ModularMinecartEntity extends AbstractMinecartEntity {
	public Map<Integer, MinecartModule> modules = new HashMap<>();

	public ModularMinecartEntity(EntityType<?> entityType, World world) {
		super(entityType, world);
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

	public Map<Integer, MinecartModule> getModules() {
		return this.modules;
	}

	public Collection<MinecartModule> getModuleList() {
		return this.modules.values();
	}

	@Override
	protected void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		this.setModules(
				ModuleStorage.read(nbt, this)
				.stream()
				.collect(Collectors.toMap(MinecartModule::getId, Function.identity())),
				false
		);
	}

	@Override
	protected void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		ModuleStorage.write(nbt, this.getModuleList());
	}

	public void writeModuleData(PacketByteBuf buf) {
		buf.writeVarInt(this.modules.size());
		for (MinecartModule module : this.getModuleList()) {
			buf.writeVarInt(MinecartModuleType.REGISTRY.getRawId(module.getType()));
			buf.writeNbt(module.writeNbt(new NbtCompound()));
		}
	}

	public void setModuleData(Multimap<MinecartModuleType<?>, NbtCompound> moduleData, boolean update) {
		List<MinecartModule> modules = new ArrayList<>();
		moduleData.forEach((moduleType, nbt) -> {
			MinecartModule module = moduleType.createModule(this);
			module.readNbt(nbt);
			modules.add(module);
		});
		this.setModules(modules.stream().collect(Collectors.toMap(MinecartModule::getId, Function.identity())), update);
	}

	public static Multimap<MinecartModuleType<?>, NbtCompound> readModuleData(PacketByteBuf buf) {
		Multimap<MinecartModuleType<?>, NbtCompound> map = ArrayListMultimap.create();
		int size = buf.readVarInt();
		for (int i = 0; i < size; i++) {
			MinecartModuleType<?> type = MinecartModuleType.REGISTRY.get(buf.readVarInt());
			NbtCompound compound = buf.readNbt();
			map.put(type, compound);
		}
		return map;
	}

	private void setModules(Map<Integer, MinecartModule> modules, boolean update) {
		this.modules = modules;
		if (update) {
			UpdatePacket.sendToTrackers(this);
		}
	}

	public void addModule(int id, MinecartModule module, boolean update) {
		this.modules.put(id, module);
		module.setId(id);
		if (update) {
			UpdatePacket.sendToTrackers(this);
		}
	}

	public void addModule(MinecartModule module, boolean update) {
		addModule(nextId(), module, update);
	}

	private int nextId() {
		int id = 0;
		while (this.modules.containsKey(id)) {
			id++;
		}
		return id;
	}
}
