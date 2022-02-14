package vswe.stevescarts.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;
import vswe.stevescarts.StevesCarts;

import java.util.Collections;

public class CartAssemblerBlockEntity extends BlockEntity implements SidedInventory {
	public static final int SIZE;
	private ItemStack hull = ItemStack.EMPTY;
	private final DefaultedList<ItemStack> engines = DefaultedList.ofSize(5, ItemStack.EMPTY);
	private ItemStack tool = ItemStack.EMPTY;
	private final DefaultedList<ItemStack> attachments = DefaultedList.ofSize(6, ItemStack.EMPTY);
	private final DefaultedList<ItemStack> storage = DefaultedList.ofSize(4, ItemStack.EMPTY);
	private final DefaultedList<ItemStack> addons = DefaultedList.ofSize(12, ItemStack.EMPTY);
	private ItemStack output = ItemStack.EMPTY;
	private ItemStack fuel = ItemStack.EMPTY;

	public CartAssemblerBlockEntity(BlockPos pos, BlockState state) {
		super(StevesCartsBlockEntities.CART_ASSEMBLER, pos, state);
	}

	@Override
	public void readNbt(NbtCompound nbt) {
		super.readNbt(nbt);
		DefaultedList<ItemStack> list = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
		Inventories.readNbt(nbt, DefaultedList.ofSize(this.size(), ItemStack.EMPTY));
		this.readInventoryList(list);
	}

	@Override
	protected void writeNbt(NbtCompound nbt) {
		super.writeNbt(nbt);
		Inventories.writeNbt(nbt, this.getInventoryList());
	}

	public void onLoad(ServerWorld world) {

	}

	@Override
	public int[] getAvailableSlots(Direction side) {
		return new int[0]; // TODO
	}

	@Override
	public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
		return false; // TODO
	}

	@Override
	public boolean canExtract(int slot, ItemStack stack, Direction dir) {
		return false; // TODO
	}

	@Override
	public int size() {
		return SIZE;
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
		if (slot == 0) {
			return this.hull;
		} else if (slot >= 1 && slot <= 5) {
			return this.engines.get(slot - 1);
		} else if (slot == 6) {
			return this.tool;
		} else if (slot >= 7 && slot <= 12) {
			return this.attachments.get(slot - 7);
		} else if (slot >= 13 && slot <= 16) {
			return this.storage.get(slot - 13);
		} else if (slot >= 17 && slot <= 28) {
			return this.addons.get(slot - 17);
		} else if (slot == 29) {
			return this.output;
		} else if (slot == 30) {
			return this.fuel;
		}
		throw new IndexOutOfBoundsException("Invalid slot: " + slot);
	}

	@Override
	public ItemStack removeStack(int slot, int amount) {
		if (slot == 0) {
			return Inventories.splitStack(Collections.singletonList(this.hull), 0, amount);
		} else if (slot >= 1 && slot <= 5) {
			return Inventories.splitStack(this.engines, slot - 1, amount);
		} else if (slot == 6) {
			return Inventories.splitStack(Collections.singletonList(this.tool), 0, amount);
		} else if (slot >= 7 && slot <= 12) {
			return Inventories.splitStack(this.attachments, slot - 7, amount);
		} else if (slot >= 13 && slot <= 16) {
			return Inventories.splitStack(this.storage, slot - 13, amount);
		} else if (slot >= 17 && slot <= 28) {
			return Inventories.splitStack(this.addons, slot - 17, amount);
		} else if (slot == 29) {
			return Inventories.splitStack(Collections.singletonList(this.output), 0, amount);
		} else if (slot == 30) {
			return Inventories.splitStack(Collections.singletonList(this.fuel), 0, amount);
		} else {
			throw new IndexOutOfBoundsException("Invalid slot: " + slot);
		}
	}

	@Override
	public ItemStack removeStack(int slot) {
		return this.removeStack(slot, Integer.MAX_VALUE);
	}

	@Override
	public void setStack(int slot, ItemStack stack) {
		if (slot == 0) {
			this.hull = stack;
		} else if (slot >= 1 && slot <= 5) {
			this.engines.set(slot - 1, stack);
		} else if (slot == 6) {
			this.tool = stack;
		} else if (slot >= 7 && slot <= 12) {
			this.attachments.set(slot - 7, stack);
		} else if (slot >= 13 && slot <= 16) {
			this.storage.set(slot - 13, stack);
		} else if (slot >= 17 && slot <= 28) {
			this.addons.set(slot - 17, stack);
		} else if (slot == 29) {
			this.output = stack;
		} else if (slot == 30) {
			this.fuel = stack;
		} else {
			StevesCarts.LOGGER.error("Invalid slot: " + slot);
		}
	}

	public DefaultedList<ItemStack> getInventoryList() {
		DefaultedList<ItemStack> list = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
		for (int i = 0; i < this.size(); ++i) {
			list.set(i, this.getStack(i));
		}
		return list;
	}

	public void readInventoryList(DefaultedList<ItemStack> list) {
		for (int i = 0; i < this.size(); ++i) {
			this.setStack(i, list.get(i));
		}
	}

	@Override
	public boolean canPlayerUse(PlayerEntity player) {
		return player.squaredDistanceTo(this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D) <= 64;
	}

	@Override
	public void clear() {
		this.hull = ItemStack.EMPTY;
		this.engines.clear();
		this.tool = ItemStack.EMPTY;
		this.attachments.clear();
		this.storage.clear();
		this.addons.clear();
		this.output = ItemStack.EMPTY;
		this.fuel = ItemStack.EMPTY;
	}

	@Override
	public void markDirty() {
		super.markDirty();
	}

	static {
		SIZE = 1 + 5 + 1 + 6 + 4 + 12 + 1 + 1;
	}
}
