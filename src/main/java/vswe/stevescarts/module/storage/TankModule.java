package vswe.stevescarts.module.storage;

import io.github.cottonmc.cotton.gui.networking.NetworkSide;
import io.github.cottonmc.cotton.gui.networking.ScreenNetworking;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import reborncore.common.fluid.FluidUtils;
import reborncore.common.fluid.FluidValue;
import reborncore.common.util.Tank;
import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.entity.CartEntity;
import vswe.stevescarts.module.ModuleType;
import vswe.stevescarts.screen.CartHandler;
import vswe.stevescarts.screen.widget.WFluidSlot;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.Identifier;

public class TankModule extends StorageModule {
	protected final SimpleInventory bucketInventory = new SimpleInventory(2);
	protected final Tank tank;
	private final Identifier packetId;

	public TankModule(CartEntity entity, ModuleType<?> type, FluidValue capacity) {
		super(entity, type);
		this.tank = new Tank("Tank", capacity, null);
		this.packetId = tankUpdatePacketId(type.getId().getPath());
	}

	@Override
	public void writeToNbt(NbtCompound nbt) {
		this.tank.write(nbt);
		nbt.put("Buckets", this.bucketInventory.toNbtList());
		super.writeToNbt(nbt);
	}

	@Override
	public void readFromNbt(NbtCompound nbt) {
		this.tank.read(nbt);
		this.bucketInventory.readNbtList(nbt.getList("Buckets", NbtElement.COMPOUND_TYPE));
		super.readFromNbt(nbt);
	}

	@Override
	public void configure(WPlainPanel panel, CartHandler handler, PlayerEntity player) {
		WLabel label = new WLabel(this.getType().getTranslationKeyText());
		panel.add(label, 0, 0);
		WItemSlot filledBucketSlot = WItemSlot.of(this.bucketInventory, 0);
		WItemSlot emptyBucketSlot = WItemSlot.of(this.bucketInventory, 1);
		WFluidSlot fluidSlot = new WFluidSlot(this.tank);
		filledBucketSlot.addChangeListener((slot, inventory, index, stack) -> {
			if (handler.getNetworkSide() == NetworkSide.SERVER) {
				if (!FluidUtils.drainContainers(this.tank, this.bucketInventory, 0, 1)) {
					FluidUtils.fillContainers(this.tank, this.bucketInventory, 0, 1);
				}
			}
		});
		emptyBucketSlot.setInsertingAllowed(false);
		panel.add(filledBucketSlot, 0, 15);
		panel.add(emptyBucketSlot, 0, 48);
		panel.add(fluidSlot, 20, 15, 36, 51);
		ScreenNetworking.of(handler, NetworkSide.CLIENT).receive(this.packetId, buf -> this.tank.read(buf.readNbt()));
		handler.addTicker(() -> ScreenNetworking.of(handler, NetworkSide.SERVER).send(this.packetId, buf -> buf.writeNbt(this.tank.write(new NbtCompound()))));
	}

	public Tank getTank() {
		return this.tank;
	}

	public static Identifier tankUpdatePacketId(String discriminator) {
		return StevesCarts.id("tank_update_" + discriminator);
	}
}
