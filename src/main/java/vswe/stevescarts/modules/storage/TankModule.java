package vswe.stevescarts.modules.storage;

import io.github.cottonmc.cotton.gui.networking.NetworkSide;
import io.github.cottonmc.cotton.gui.networking.ScreenNetworking;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import reborncore.common.fluid.FluidUtils;
import reborncore.common.fluid.FluidValue;
import reborncore.common.util.Tank;
import vswe.stevescarts.entity.ModularMinecartEntity;
import vswe.stevescarts.modules.MinecartModuleType;
import vswe.stevescarts.modules.storage.StorageModule;
import vswe.stevescarts.screen.ModularCartHandler;
import vswe.stevescarts.screen.StevesCartsScreenHandlers;
import vswe.stevescarts.screen.widget.WFluidSlot;

public class TankModule extends StorageModule {
	protected final SimpleInventory bucketInventory = new SimpleInventory(2);
	protected final Tank tank;

	public TankModule(ModularMinecartEntity minecart, MinecartModuleType<?> type, FluidValue capacity) {
		super(minecart, type);
		this.tank = new Tank("Tank", capacity, null);
	}

	@Override
	public NbtCompound writeNbt(NbtCompound nbt) {
		this.tank.write(nbt);
		nbt.put("Buckets", this.bucketInventory.toNbtList());
		return super.writeNbt(nbt);
	}

	@Override
	public void readNbt(NbtCompound nbt) {
		this.tank.read(nbt);
		this.bucketInventory.readNbtList(nbt.getList("Buckets", NbtElement.COMPOUND_TYPE));
		super.readNbt(nbt);
	}

	@Override
	public void configure(WPlainPanel panel, ModularCartHandler handler, PlayerEntity player) {
		WLabel label = new WLabel(this.getType().getTranslationText());
		panel.add(label, 0, 0);
		WItemSlot filledBucketSlot = WItemSlot.of(this.bucketInventory, 0);
		WItemSlot emptyBucketSlot = WItemSlot.of(this.bucketInventory, 1);
		WFluidSlot fluidSlot = new WFluidSlot(this.tank);
		filledBucketSlot.addChangeListener((slot, inventory, index, stack) -> {
			if (handler.getNetworkSide() == NetworkSide.SERVER) {
				if (!FluidUtils.drainContainers(this.tank, this.bucketInventory, 0, 1)) {
					FluidUtils.fillContainers(this.tank, this.bucketInventory, 0, 1);
					ScreenNetworking.of(handler, NetworkSide.SERVER).send(StevesCartsScreenHandlers.PACKET_TANK_UPDATE, buf -> buf.writeNbt(this.tank.write(new NbtCompound())));
				}
			}
		});
		emptyBucketSlot.setInsertingAllowed(false);
		panel.add(filledBucketSlot, 0, 15);
		panel.add(emptyBucketSlot, 0, 48);
		panel.add(fluidSlot, 20, 15, 36, 51);
		ScreenNetworking.of(handler, NetworkSide.CLIENT).receive(StevesCartsScreenHandlers.PACKET_TANK_UPDATE, buf -> this.tank.read(buf.readNbt()));
	}

	public Tank getTank() {
		return this.tank;
	}
}
