package vswe.stevescarts.modules.engine;

import io.github.cottonmc.cotton.gui.networking.NetworkSide;
import io.github.cottonmc.cotton.gui.networking.ScreenNetworking;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.Property;
import net.minecraft.screen.PropertyDelegate;
import vswe.stevescarts.entity.ModularMinecartEntity;
import vswe.stevescarts.modules.MinecartModuleType;
import vswe.stevescarts.modules.StevesCartsModuleTypes;
import vswe.stevescarts.screen.ModularCartHandler;
import vswe.stevescarts.screen.StevesCartsScreenHandlers;
import vswe.stevescarts.screen.widget.WPropertyLabel;

import java.util.Optional;

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
		return this.fuelAmount > 0;
	}

	@Override
	public void onPropel() {
		this.fuelAmount--;
	}

	// TODO: add priority button
	// TODO: why property delegate :concern:
	@Override
	public void configure(WPlainPanel panel, ModularCartHandler handler, PlayerEntity player) {
		WLabel label = new WLabel(StevesCartsModuleTypes.COAL_ENGINE.getTranslationText());
		panel.add(label, 0, 0);
		WItemSlot fuel = WItemSlot.of(this.inventory, 0, this.fuelSlots, 1);
		fuel.setFilter(stack -> Optional.of(FuelRegistry.INSTANCE.get(stack.getItem())).orElse(0) > 0 && stack.getItem().getRecipeRemainder() == null);
		panel.add(fuel, 0, 15);
		handler.addProperties(this.propertyDelegate);
		Property fuelProperty = Property.create();
		fuelProperty.set(this.fuelAmount);
		WLabel fuelLabel = new WPropertyLabel("screen.stevescarts.cart.fuel", fuelProperty);
		handler.addTicker(() -> {
			fuelProperty.set(this.fuelAmount);
			if (fuelProperty.hasChanged()) {
				ScreenNetworking.of(handler, NetworkSide.SERVER).send(StevesCartsScreenHandlers.PACKET_COAL_FUEL_UPDATE, buf -> buf.writeVarInt(fuelProperty.get()));
			}
		});
		panel.add(fuelLabel, 0, 35);
		panel.setSize(60, 50);
		ScreenNetworking.of(handler, NetworkSide.CLIENT).receive(StevesCartsScreenHandlers.PACKET_COAL_FUEL_UPDATE, buf -> fuelProperty.set(buf.readVarInt()));
	}

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
				if (this.minecart.world.isClient()) {
					this.minecart.world.addParticle(ParticleTypes.SMOKE, this.minecart.getX(), this.minecart.getY() + 0.5, this.minecart.getZ(), 0, 0.05, 0);
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
}
