package vswe.stevescarts.entity;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import vswe.stevescarts.entity.network.CartSpawnS2CPacket;
import vswe.stevescarts.entity.network.CartUpdateS2CPacket;
import vswe.stevescarts.module.CartModule;
import vswe.stevescarts.module.ModuleGroup;
import vswe.stevescarts.module.ModuleType;
import vswe.stevescarts.module.Worker;
import vswe.stevescarts.module.engine.EngineModule;
import vswe.stevescarts.module.storage.TankModule;
import vswe.stevescarts.screen.CartHandler;

import net.minecraft.block.BlockState;
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
import net.minecraft.tag.BlockTags;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;

public class CartEntity extends MinecartEntity {
	private final Int2ObjectMap<CartModule> modules = new Int2ObjectLinkedOpenHashMap<>();
	private final FluidStorage fluidStorage = this.new FluidStorage();
	private int stopTicks = 0;

	public CartEntity(EntityType<?> entityType, World world) {
		super(entityType, world);
	}

	public CartEntity(World world, List<ModuleType<?>> types) {
		super(StevesCartsEntities.CART, world);
		for (int i = 0; i < types.size(); i++) {
			modules.put(i, types.get(i).create(this));
		}
	}

	@Override
	public Type getMinecartType() {
		return null;
	}

	public FluidStorage getFluidStorage() {
		return fluidStorage;
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
		for (CartModule minecartModule : this.modules.values()) {
			minecartModule.onActivate();
		}
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
	protected void moveOnRail(BlockPos pos, BlockState state) {
		if (this.stopTicks > 0) {
			return;
		}
		super.moveOnRail(pos, state);
	}

	@Override
	public void tick() {
		super.tick();
		this.modules.values().forEach(cartModule -> {
			cartModule.setEntity(this);
			cartModule.tick();
		});
		this.modules.values().stream().filter(Worker.class::isInstance).map(Worker.class::cast).sorted().forEach(Worker::work);
		boolean move = true;
		if (this.stopTicks > 0) {
			this.stopTicks--;
			move = false;
		}
		for (ObjectIterator<CartModule> iterator = this.modules.values().iterator(); iterator.hasNext() && move; ) {
			CartModule minecartModule = iterator.next();
			if (!minecartModule.shouldMove()) {
				move = false;
				break;
			}
		}

		if (move) {
			this.modules.int2ObjectEntrySet()
					.stream()
					.filter(entry -> entry.getValue().getType().getGroup() == ModuleGroup.ENGINE && entry.getValue().canPropel() && ((EngineModule) entry.getValue()).getPriority() != EngineModule.DISABLED)
					.peek(entry -> ((EngineModule) entry.getValue()).setPropelling(false))
					.map(Map.Entry::getValue)
					.sorted()
					.findFirst()
					.filter(t -> {
						int x = (int) Math.floor(this.getX());
						int y = (int) Math.floor(this.getY());
						int z = (int) Math.floor(this.getZ());
						BlockPos.Mutable pos = new BlockPos.Mutable(x, y, z);
						if (world.getBlockState(pos.down()).isIn(BlockTags.RAILS)) pos.move(Direction.DOWN);
						BlockState state = world.getBlockState(pos);
						return state.isIn(BlockTags.RAILS);
					})
					.ifPresent(this::propel);
		}
	}

	private void propel(CartModule engine) {
		((EngineModule) engine).setPropelling(true);
		engine.onPropel();
		Vec3d velocity = this.getVelocity();
		double horizontal = velocity.horizontalLength();
		if (horizontal > 0.01) {
			this.setVelocity(velocity.add(velocity.x / horizontal * 0.01, 0.0, velocity.z / horizontal * 0.01));
		} else {
			this.setVelocity(0.01, 0.0, 0.01);
		}
	}

	@Override
	protected void applySlowdown() {
		double drag = this.hasPassengers() ? 0.96 : 0.94;
		Vec3d vec3d = this.getVelocity();
		vec3d = vec3d.multiply(drag, 0.0, drag);
		if (this.isTouchingWater()) {
			vec3d = vec3d.multiply(0.85f);
		}
		this.setVelocity(vec3d);
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

	public void updateModuleData(NbtCompound nbt) {
		for (String key : nbt.getKeys()) {
			int id = Integer.parseInt(key);
			CartModule module = modules.get(id);
			module.readFromNbt(nbt.getCompound(key));
		}
	}

	public void reverse() {
		this.setVelocity(this.getVelocity().multiply(-1, 0, -1));
	}

	public void stopFor(int ticks) {
		this.stopTicks = ticks;
	}

	public boolean isStopped() {
		return this.stopTicks > 0;
	}

	@SuppressWarnings("UnstableApiUsage")
	public class FluidStorage implements Storage<FluidVariant> {
		private final Long zero = 0L;

		@Override
		public long insert(FluidVariant resource, long maxAmount, TransactionContext transaction) {
			return CartEntity.this
					.getTanks()
					.filter(tank -> tank.getTank().simulateInsert(resource, maxAmount, transaction) > 0)
					.findFirst()
					.map(tank -> tank.getTank().insert(resource, maxAmount, transaction))
					.orElse(zero);
		}

		@Override
		public long extract(FluidVariant resource, long maxAmount, TransactionContext transaction) {
			return CartEntity.this
					.getTanks()
					.filter(tank -> tank.getTank().simulateExtract(resource, maxAmount, transaction) > 0)
					.findFirst()
					.map(tank -> tank.getTank().extract(resource, maxAmount, transaction))
					.orElse(zero);
		}

		@Override
		public Iterator<StorageView<FluidVariant>> iterator() {
			return CartEntity.this
					.getTanks()
					.map(tank -> tank.getTank().iterator())
					.flatMap(it -> StreamSupport.stream(Spliterators.spliteratorUnknownSize(it, Spliterator.ORDERED), false))
					.iterator();
		}
	}

	private Stream<TankModule> getTanks() {
		return this.modules.values().stream().filter(module -> module instanceof TankModule).map(module -> (TankModule) module);
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
