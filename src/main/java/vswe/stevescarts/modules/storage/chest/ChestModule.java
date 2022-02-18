package vswe.stevescarts.modules.storage.chest;

import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import vswe.stevescarts.entity.ModularMinecartEntity;
import vswe.stevescarts.modules.Controllable;
import vswe.stevescarts.modules.MinecartModule;
import vswe.stevescarts.modules.MinecartModuleType;

public class ChestModule extends MinecartModule implements Controllable, Inventory {
	private final int slotsWidth;
	private final int slotsHeight;
	private final int size;
	private final DefaultedList<ItemStack> inventory;

	protected ChestModule(ModularMinecartEntity minecart, MinecartModuleType<?> type, int slotsWidth, int slotsHeight) {
		super(minecart, type);
		this.slotsWidth = slotsWidth;
		this.slotsHeight = slotsHeight;
		this.size = slotsWidth * slotsHeight;
		this.inventory = DefaultedList.ofSize(this.size, ItemStack.EMPTY);
	}

	@Override
	public NbtCompound writeNbt(NbtCompound nbt) {
		nbt.put("Inventory", Inventories.writeNbt(new NbtCompound(), this.inventory));
		return super.writeNbt(nbt);
	}

	@Override
	public void readNbt(NbtCompound nbt) {
		Inventories.readNbt(nbt.getCompound("Inventory"), this.inventory);
		super.readNbt(nbt);
	}

	public int getSlotsWidth() {
		return slotsWidth;
	}

	public int getSlotsHeight() {
		return slotsHeight;
	}

	public void configure(WPlainPanel panel) {
		WLabel label = new WLabel(new TranslatableText("screen.stevescarts.chest.title"));
		WItemSlot slots = WItemSlot.of(this, 0, this.slotsWidth, this.slotsHeight);
		panel.add(label, 0, 0);
		panel.add(slots, 15, 0);
	}

	@Override
	public int size() {
		return this.size;
	}

	@Override
	public boolean isEmpty() {
		for (int i = 0; i < this.size(); ++i) {
			if (this.getStack(i).isEmpty()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public ItemStack getStack(int slot) {
		return this.inventory.get(slot);
	}

	@Override
	public ItemStack removeStack(int slot, int amount) {
		ItemStack itemStack = Inventories.splitStack(this.inventory, slot, amount);
		if (!itemStack.isEmpty()) {
			this.markDirty();
		}
		return itemStack;
	}

	@Override
	public ItemStack removeStack(int slot) {
		return Inventories.removeStack(this.inventory, slot);
	}

	@Override
	public void setStack(int slot, ItemStack stack) {
		this.inventory.set(slot, stack);
		if (stack.getCount() > this.getMaxCountPerStack()) {
			stack.setCount(this.getMaxCountPerStack());
		}
		this.markDirty();
	}

	@Override
	public void markDirty() {
	}

	@Override
	public boolean canPlayerUse(PlayerEntity player) {
		return true;
	}

	@Override
	public void clear() {
		this.inventory.clear();
	}
}
