package vswe.stevescarts.entity;

import java.util.List;

import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import vswe.stevescarts.entity.network.CartSpawnS2CPacket;
import vswe.stevescarts.entity.network.CartUpdateS2CPacket;
import vswe.stevescarts.module.CartModule;
import vswe.stevescarts.module.ModuleType;
import vswe.stevescarts.screen.CartHandler;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.vehicle.MinecartEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;

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
		if (!this.isAlive()) {
			return ActionResult.PASS;
		}
		if (!player.world.isClient && !player.isSpectator()) {
			player.openHandledScreen(this.new CartScreenHandlerFactory());
			this.onScreenOpen();
		}
		return ActionResult.success(player.world.isClient);
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

	@Override
	public void tick() {
		super.tick();
		this.modules.values().forEach(cartModule -> {
			cartModule.setEntity(this);
			cartModule.tick();
		});
	}

	public void onScreenOpen() {
		this.modules.values().forEach(CartModule::onScreenOpen);
	}

	public void onScreenClose() {
		this.modules.values().forEach(CartModule::onScreenClose);
		if (!this.world.isClient) {
			CartUpdateS2CPacket.sendToTrackers(this);
		}
	}

	private class CartScreenHandlerFactory implements ExtendedScreenHandlerFactory {
		@Override
		public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
			buf.writeInt(CartEntity.this.getId());
		}

		@Override
		public Text getDisplayName() {
			return CartEntity.this.getDisplayName();
		}

		@Override
		public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
			return new CartHandler(syncId, inv, CartEntity.this);
		}
	}
}
