package vswe.stevescarts.module.storage;

import java.util.concurrent.ThreadLocalRandom;

import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import vswe.stevescarts.entity.CartEntity;
import vswe.stevescarts.module.ModuleType;
import vswe.stevescarts.screen.CartHandler;

import net.minecraft.block.entity.ChestLidAnimator;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;

public class ChestModule extends StorageModule implements Inventory {
	protected final int slotsWidth;
	protected final int slotsHeight;
	protected final Text label;
	protected final int size;
	protected final DefaultedList<ItemStack> inventory;
	protected final ChestLidAnimator chestLidAnimator = new ChestLidAnimator();

	public ChestModule(CartEntity minecart, ModuleType<?> type, int slotsWidth, int slotsHeight) {
		super(minecart, type);
		this.slotsWidth = slotsWidth;
		this.slotsHeight = slotsHeight;
		this.label = type.getTranslationKeyText();
		this.size = slotsWidth * slotsHeight;
		this.inventory = DefaultedList.ofSize(this.size, ItemStack.EMPTY);
	}

	@Override
	public void writeToNbt(NbtCompound nbt) {
		nbt.put("Inventory", Inventories.writeNbt(new NbtCompound(), this.inventory));
		super.writeToNbt(nbt);
	}

	@Override
	public void readFromNbt(NbtCompound nbt) {
		Inventories.readNbt(nbt.getCompound("Inventory"), this.inventory);
		super.readFromNbt(nbt);
	}

	public int getSlotsWidth() {
		return slotsWidth;
	}

	public int getSlotsHeight() {
		return slotsHeight;
	}

	@Override
	public void configure(WPlainPanel panel, CartHandler handler, PlayerEntity player) {
		WLabel label = new WLabel(this.label);
		WItemSlot slots = WItemSlot.of(this, 0, this.slotsWidth, this.slotsHeight);
		panel.add(label, 0, 0);
		panel.add(slots, 0, 15);
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

	@Override
	public void tick() {
		if (this.getEntity().world.isClient && this.shouldAnimate()) {
			this.chestLidAnimator.step();
		}
	}

	@Override
	public void onScreenOpen() {
		if (this.getEntity().world.isClient && this.shouldAnimate()) {
			this.chestLidAnimator.setOpen(true);
		}
		if (!this.getEntity().world.isClient) {
			this.getEntity().world.playSound(null, this.getEntity().getX(), this.getEntity().getY(), this.getEntity().getZ(), SoundEvents.BLOCK_CHEST_OPEN, SoundCategory.BLOCKS, 0.5f, ThreadLocalRandom.current().nextFloat() * 0.1f + 0.9f);
		}
	}

	@Override
	public void onScreenClose() {
		if (this.getEntity().world.isClient && this.shouldAnimate()) {
			this.chestLidAnimator.setOpen(false);
		}
		if (!this.getEntity().world.isClient) {
			this.getEntity().world.playSound(null, this.getEntity().getX(), this.getEntity().getY(), this.getEntity().getZ(), SoundEvents.BLOCK_CHEST_CLOSE, SoundCategory.BLOCKS, 0.5f, ThreadLocalRandom.current().nextFloat() * 0.1f + 0.9f);
		}
	}

	public boolean shouldAnimate() {
		return true;
	}

	public float getOpenProgress(float delta) {
		return -this.chestLidAnimator.getProgress(delta);
	}
}
