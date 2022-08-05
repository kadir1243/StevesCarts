package vswe.stevescarts.entity;

import java.util.List;

import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import vswe.stevescarts.entity.network.CartSpawnS2CPacket;
import vswe.stevescarts.module.CartModule;
import vswe.stevescarts.module.ModuleType;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.MinecartEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class CartEntity extends MinecartEntity {
	private final Int2ObjectMap<CartModule> modules = new Int2ObjectLinkedOpenHashMap<>();

	public CartEntity(EntityType<?> entityType, World world) {
		super(entityType, world);
	}

	@Override
	public Type getMinecartType() {
		return null;
	}

	@Override
	protected void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		modules.clear();
		NbtCompound moduleNbts = nbt.getCompound("modules");
		readModuleData(moduleNbts);
	}

	public void readModuleData(NbtCompound moduleNbts) {
		for (String key : moduleNbts.getKeys()) {
			int id = Integer.parseInt(key);
			CartModule module = ModuleType.fromNbt(moduleNbts.getCompound(key), this);
			modules.put(id, module);
			module.setId(id);
		}
	}

	public ObjectCollection<CartModule> getModules() {
		return this.modules.values();
	}

	@Override
	protected void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		NbtCompound moduleNbts = new NbtCompound();
		writeModuleData(moduleNbts);
		nbt.put("modules", moduleNbts);
	}

	public void writeModuleData(NbtCompound moduleNbts) {
		this.modules.forEach((id, module) -> {
			NbtCompound moduleNbt = ModuleType.toNbt(module);
			moduleNbts.put(String.valueOf(id), moduleNbt);
		});
	}

	@Override
	public Packet<?> createSpawnPacket() {
		return CartSpawnS2CPacket.create(this);
	}

	@Override
	public void onActivatorRail(int x, int y, int z, boolean powered) {
		// TODO
	}

	@Override
	public ActionResult interact(PlayerEntity player, Hand hand) {
		// TODO
		return ActionResult.PASS;
	}

	@Override
	protected Item getItem() {
		return null;
	}

	@Override
	public void dropItems(DamageSource damageSource) {
		this.kill();
		// TODO
	}

	public boolean shouldRenderTop() {
		for (CartModule module : this.modules.values()) {
			if (module.getType().shouldRemoveTop()) {
				return false;
			}
		}
		return true;
	}

	public void createModuleData(List<CartModule> read) {
		for (CartModule module : read) {
			modules.put(nextId(), module);
		}
	}

	public int nextId() {
		for (int i = 0;; i++) {
			if (!modules.containsKey(i)) {
				return i;
			}
		}
	}
}
