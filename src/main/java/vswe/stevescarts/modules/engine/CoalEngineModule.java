package vswe.stevescarts.modules.engine;

import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.screen.PropertyDelegate;
import vswe.stevescarts.entity.ModularMinecartEntity;
import vswe.stevescarts.modules.MinecartModuleType;
import vswe.stevescarts.screen.ModularCartHandler;
import vswe.stevescarts.screen.widget.WPropertyLabel;

import java.util.Optional;

// TODO
public class CoalEngineModule extends EngineModule {
	private final int fuelSlots;
	private final float fuelMultiplier;
	private int fireIndex = 0;
	private final SimpleInventory inventory;
	private int fuelAmount = 0;
	private final PropertyDelegate propertyDelegate = new PropertyDelegate() {
		@Override
		public int get(int index) {
			return index == 0 ? fuelAmount : 0;
		}

		@Override
		public void set(int index, int value) {
			fuelAmount = index == 0 ? value : fuelAmount;
		}

		@Override
		public int size() {
			return 1;
		}
	};

	public CoalEngineModule(ModularMinecartEntity minecart, MinecartModuleType<?> type, int fuelSlots, float fuelMultiplier) {
		super(minecart, type);
		this.fuelSlots = fuelSlots;
		this.fuelMultiplier = fuelMultiplier;
		this.inventory = new SimpleInventory(fuelSlots);
	}

	@Override
	public NbtCompound writeNbt(NbtCompound nbt) {
		nbt.put("Inventory", this.inventory.toNbtList());
		nbt.putInt("Fuel", this.fuelAmount);
		return super.writeNbt(nbt);
	}

	@Override
	public void readNbt(NbtCompound nbt) {
		this.inventory.readNbtList(nbt.getList("Inventory", NbtElement.COMPOUND_TYPE));
		this.fuelAmount = nbt.getInt("Fuel");
		super.readNbt(nbt);
	}

	@Override
	public boolean canPropel() {
		return false;
	}

	@Override
	public void onPropel() {

	}

	// TODO: add priority button
	@Override
	public void configure(WPlainPanel panel, ModularCartHandler handler, PlayerEntity player) {
		WLabel label = new WLabel(this.getType().getTranslationText());
		panel.add(label, 0, 0);
		WItemSlot fuel = WItemSlot.of(this.inventory, 0, this.fuelSlots, 1);
		fuel.setFilter(stack -> Optional.of(FuelRegistry.INSTANCE.get(stack.getItem())).orElse(0) > 0);
		panel.add(fuel, 0, 15);
		handler.setPropertyDelegate(this.propertyDelegate); // this is conc
		handler.addProperties(this.propertyDelegate);
		WLabel fuelLabel = new WPropertyLabel("screen.stevescarts.cart.fuel", 0);
		panel.add(fuelLabel, 0, 35);
	}

	@Override
	public void tick() {
		this.fireIndex++;
		if (this.fireIndex >= 4) {
			this.fireIndex = 0;
		}
	}

	public int getFireIndex() {
		return this.fireIndex;
	}

	public int getFuel(ItemStack stack) {
		return (int) (FuelRegistry.INSTANCE.get(stack.getItem()) * this.fuelMultiplier);
	}
}
