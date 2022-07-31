package vswe.stevescarts.block.entity;

import java.util.Collections;

import org.jetbrains.annotations.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class CartAssemblerBlockEntity extends BlockEntity implements SidedInventory {
	public static final Text NAME = Text.translatable("screen.stevescarts.cart_assembler");
	public static final int SIZE;
	public static final int HULL_SLOT = 0;
	public static final int ENGINE_SLOT_START = 1;
	public static final int ENGINE_SLOT_END = 5;
	public static final int TOOL_SLOT = 6;
	public static final int ATTACHMENT_SLOT_START = 7;
	public static final int ATTACHMENT_SLOT_END = 12;
	public static final int STORAGE_SLOT_START = 13;
	public static final int STORAGE_SLOT_END = 16;
	public static final int ADDON_SLOT_START = 17;
	public static final int ADDON_SLOT_END = 28;
	public static final int OUTPUT_SLOT = 29;
	public static final int FUEL_SLOT = 30;
	private final DefaultedList<ItemStack> engines = DefaultedList.ofSize(5, ItemStack.EMPTY);
	private final DefaultedList<ItemStack> attachments = DefaultedList.ofSize(6, ItemStack.EMPTY);
	private final DefaultedList<ItemStack> storage = DefaultedList.ofSize(4, ItemStack.EMPTY);
	private final DefaultedList<ItemStack> addons = DefaultedList.ofSize(12, ItemStack.EMPTY);
	private ItemStack hull = ItemStack.EMPTY;
	private ItemStack tool = ItemStack.EMPTY;
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
		if (slot == HULL_SLOT) {
			return this.hull;
		} else if (slot >= ENGINE_SLOT_START && slot <= ENGINE_SLOT_END) {
			return this.engines.get(slot - ENGINE_SLOT_START);
		} else if (slot == TOOL_SLOT) {
			return this.tool;
		} else if (slot >= ATTACHMENT_SLOT_START && slot <= ATTACHMENT_SLOT_END) {
			return this.attachments.get(slot - ATTACHMENT_SLOT_START);
		} else if (slot >= STORAGE_SLOT_START && slot <= STORAGE_SLOT_END) {
			return this.storage.get(slot - STORAGE_SLOT_START);
		} else if (slot >= ADDON_SLOT_START && slot <= ADDON_SLOT_END) {
			return this.addons.get(slot - ADDON_SLOT_START);
		} else if (slot == OUTPUT_SLOT) {
			return this.output;
		} else if (slot == FUEL_SLOT) {
			return this.fuel;
		}
		throw new IndexOutOfBoundsException("Invalid slot: " + slot);
	}

	@Override
	public ItemStack removeStack(int slot, int amount) {
		if (slot == HULL_SLOT) {
			return Inventories.splitStack(Collections.singletonList(this.hull), 0, amount);
		} else if (slot >= ENGINE_SLOT_START && slot <= ENGINE_SLOT_END) {
			return Inventories.splitStack(this.engines, slot - ENGINE_SLOT_START, amount);
		} else if (slot == TOOL_SLOT) {
			return Inventories.splitStack(Collections.singletonList(this.tool), 0, amount);
		} else if (slot >= ATTACHMENT_SLOT_START && slot <= ATTACHMENT_SLOT_END) {
			return Inventories.splitStack(this.attachments, slot - ATTACHMENT_SLOT_START, amount);
		} else if (slot >= STORAGE_SLOT_START && slot <= STORAGE_SLOT_END) {
			return Inventories.splitStack(this.storage, slot - STORAGE_SLOT_START, amount);
		} else if (slot >= ADDON_SLOT_START && slot <= ADDON_SLOT_END) {
			return Inventories.splitStack(this.addons, slot - ADDON_SLOT_START, amount);
		} else if (slot == OUTPUT_SLOT) {
			return Inventories.splitStack(Collections.singletonList(this.output), 0, amount);
		} else if (slot == FUEL_SLOT) {
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
		if (slot == HULL_SLOT) {
			this.hull = stack;
		} else if (slot >= ENGINE_SLOT_START && slot <= ENGINE_SLOT_END) {
			this.engines.set(slot - ENGINE_SLOT_START, stack);
		} else if (slot == TOOL_SLOT) {
			this.tool = stack;
		} else if (slot >= ATTACHMENT_SLOT_START && slot <= ATTACHMENT_SLOT_END) {
			this.attachments.set(slot - ATTACHMENT_SLOT_START, stack);
		} else if (slot >= STORAGE_SLOT_START && slot <= STORAGE_SLOT_END) {
			this.storage.set(slot - STORAGE_SLOT_START, stack);
		} else if (slot >= ADDON_SLOT_START && slot <= ADDON_SLOT_END) {
			this.addons.set(slot - ADDON_SLOT_START, stack);
		} else if (slot == OUTPUT_SLOT) {
			this.output = stack;
		} else if (slot == FUEL_SLOT) {
			this.fuel = stack;
		} else {
			throw new IndexOutOfBoundsException("Invalid slot: " + slot);
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

	public void scatter(World world, BlockPos pos) {
		ItemScatterer.spawn(world, pos, this);
	}

	static {
		SIZE = 1 + 5 + 1 + 6 + 4 + 12 + 1 + 1;
	}
}
