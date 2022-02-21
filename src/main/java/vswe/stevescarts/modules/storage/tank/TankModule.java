package vswe.stevescarts.modules.storage.tank;

import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.BucketItem;
import vswe.stevescarts.entity.ModularMinecartEntity;
import vswe.stevescarts.modules.MinecartModuleType;
import vswe.stevescarts.modules.storage.StorageModule;
import vswe.stevescarts.screen.widget.WFluidSlot;

import java.util.Optional;

// TODO: Implement fluid transfer
@SuppressWarnings("UnstableApiUsage")
public class TankModule extends StorageModule  {
	protected final long capacity;
	protected final SimpleInventory bucketInventory = new SimpleInventory(2);
	protected long amount = 0;
	protected FluidVariant fluidVariant = FluidVariant.blank();

	public TankModule(ModularMinecartEntity minecart, MinecartModuleType<?> type, long capacity) {
		super(minecart, type);
		this.capacity = capacity;
	}

	@Override
	public void configure(WPlainPanel panel) { // TODO
//		InventoryStorage inventoryStorage = InventoryStorage.of(this.bucketInventory, null);
		WLabel label = new WLabel(this.getType().getTranslationText());
		panel.add(label, 0, 0);
		WItemSlot filledBucketSlot = WItemSlot.of(this.bucketInventory, 0);
		WItemSlot emptyBucketSlot = WItemSlot.of(this.bucketInventory, 1);
		WFluidSlot fluidSlot = new WFluidSlot(() -> this.amount, this.capacity);
		filledBucketSlot.setFilter(itemStack -> itemStack.getItem() instanceof BucketItem);
		emptyBucketSlot.setInsertingAllowed(false);
		panel.add(filledBucketSlot, 0, 15);
		panel.add(emptyBucketSlot, 0, 48);
		panel.add(fluidSlot, 20, 15);
		panel.setSize(60, 64);
	}
}
