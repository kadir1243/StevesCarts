package vswe.stevescarts.module.engine;

import io.github.cottonmc.cotton.gui.networking.NetworkSide;
import io.github.cottonmc.cotton.gui.networking.ScreenNetworking;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.entity.CartEntity;
import vswe.stevescarts.module.ModuleType;
import vswe.stevescarts.module.StevesCartsModules;
import vswe.stevescarts.screen.CartHandler;
import vswe.stevescarts.screen.widget.WPropertyLabel;

import java.util.Optional;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.Property;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.util.Identifier;

import net.fabricmc.fabric.api.registry.FuelRegistry;

// TODO
public class CoalEngineModule extends EngineModule {
	private final int fuelSlots;
	private final float fuelMultiplier;
	private final SimpleInventory inventory;
	private int fireIndex = 0;
	private int fuelAmount = 0;
	private int nextPuff = 0;
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

	public CoalEngineModule(CartEntity minecart, ModuleType<?> type, int fuelSlots, float fuelMultiplier) {
		super(minecart, type);
		this.fuelSlots = fuelSlots;
		this.fuelMultiplier = fuelMultiplier;
		this.inventory = new SimpleInventory(fuelSlots);
	}

	@Override
	public void writeToNbt(NbtCompound nbt) {
		nbt.put("Inventory", this.inventory.toNbtList());
		nbt.putInt("Fuel", this.fuelAmount);
		super.writeToNbt(nbt);
	}

	@Override
	public void readFromNbt(NbtCompound nbt) {
		this.inventory.readNbtList(nbt.getList("Inventory", NbtElement.COMPOUND_TYPE));
		this.fuelAmount = nbt.getInt("Fuel");
		super.readFromNbt(nbt);
	}

	@Override
	public boolean canPropel() {
		return this.fuelAmount > 0;
	}

	@Override
	public void onPropel() {
		this.fuelAmount--;
	}

	@Override
	public void configure(WPlainPanel panel, CartHandler handler, PlayerEntity player) {
		WLabel label = new WLabel(StevesCartsModules.COAL_ENGINE.getTranslationText());
		panel.add(label, 0, 0);
		WItemSlot fuel = WItemSlot.of(this.inventory, 0, this.fuelSlots, 1);
		fuel.setFilter(stack -> Optional.of(FuelRegistry.INSTANCE.get(stack.getItem())).orElse(0) > 0 && stack.getItem().getRecipeRemainder() == null);
		panel.add(fuel, 0, 15);
		Property fuelProperty = Property.create();
		fuelProperty.set(this.fuelAmount);
		WLabel fuelLabel = new WPropertyLabel("screen.stevescarts.cart.fuel", fuelProperty);
		handler.addTicker(() -> {
			fuelProperty.set(this.fuelAmount);
			if (fuelProperty.hasChanged()) {
				ScreenNetworking.of(handler, NetworkSide.SERVER).send(PACKET_COAL_FUEL_UPDATE, buf -> buf.writeVarInt(fuelProperty.get()));
			}
		});
		panel.add(fuelLabel, 0, 35);
		panel.setSize(72, 50);
		super.addPriorityButton(handler, panel, 56, 15);
		ScreenNetworking.of(handler, NetworkSide.CLIENT).receive(PACKET_COAL_FUEL_UPDATE, buf -> fuelProperty.set(buf.readVarInt()));
	}

	public static final Identifier PACKET_COAL_FUEL_UPDATE = StevesCarts.id("coal_fuel_update");

	@Override
	public void tick() {
		if (this.fuelAmount <= 0) {
			int nextSlot = -1;
			for (int i = 0; i < this.inventory.size(); i++) {
				ItemStack stack = this.inventory.getStack(i);
				if (!stack.isEmpty()) {
					nextSlot = i;
					break;
				}
			}
			if (nextSlot != -1) {
				ItemStack stack = this.inventory.getStack(nextSlot);
				this.fuelAmount = this.getFuel(stack);
				stack.decrement(1);
			}
		}
		if (this.fuelAmount > 0) {
			this.fireIndex++;
			if (this.fireIndex >= 4) {
				this.fireIndex = 0;
			}
		} else {
			this.fireIndex = -1;
		}
		if (this.isPropelling()) {
			if (this.nextPuff <= 0) {
				if (this.getEntity().world.isClient()) {
					this.getEntity().world.addParticle(ParticleTypes.SMOKE, this.getEntity().getX(), this.getEntity().getY() + 0.5, this.getEntity().getZ(), 0, 0.05, 0);
				}
				this.nextPuff = 20;
			} else {
				this.nextPuff--;
			}
		}
	}

	public int getFireIndex() {
		return this.fireIndex;
	}

	public int getFuel(ItemStack stack) {
		return (int) (FuelRegistry.INSTANCE.get(stack.getItem()) * this.fuelMultiplier);
	}

	@Override
	public String getDiscriminator() {
		return "coal";
	}
}
