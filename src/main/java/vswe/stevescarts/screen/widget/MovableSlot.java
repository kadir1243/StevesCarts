package vswe.stevescarts.screen.widget;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import io.github.cottonmc.cotton.gui.impl.VisualLogger;

import java.util.Objects;
import java.util.function.Predicate;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

// modified version of ValidatedSlot
public class MovableSlot extends Slot {
	public static final Predicate<ItemStack> DEFAULT_ITEM_FILTER = stack -> true;
	private static final VisualLogger LOGGER = new VisualLogger(MovableSlot.class);
	private final int slotNumber;
	private boolean insertingAllowed = true;
	private boolean takingAllowed = true;
	private Predicate<ItemStack> filter = DEFAULT_ITEM_FILTER;
	protected final Multimap<WMovableSlot, WMovableSlot.ChangeListener> listeners = HashMultimap.create();
	private boolean visible = true;

	public MovableSlot(Inventory inventory, int index, int x, int y) {
		super(inventory, index, x, y);
		if (inventory==null) throw new IllegalArgumentException("Can't make an itemslot from a null inventory!");
		this.slotNumber = index;
	}

	@Override
	public boolean canInsert(ItemStack stack) {
		return insertingAllowed && inventory.isValid(slotNumber, stack) && filter.test(stack);
	}

	@Override
	public boolean canTakeItems(PlayerEntity player) {
		return takingAllowed && inventory.canPlayerUse(player);
	}

	@Override
	public ItemStack getStack() {
		if (inventory==null) {
			LOGGER.warn("Prevented null-inventory from WMovableSlot with slot #: {}", slotNumber);
			return ItemStack.EMPTY;
		}

		ItemStack result = super.getStack();
		if (result==null) {
			LOGGER.warn("Prevented null-itemstack crash from: {}", inventory.getClass().getCanonicalName());
			return ItemStack.EMPTY;
		}

		return result;
	}

	@Override
	public void markDirty() {
		listeners.forEach((slot, listener) -> listener.onStackChanged(slot, inventory, getInventoryIndex(), getStack()));
		super.markDirty();
	}
	
	public int getInventoryIndex() {
		return slotNumber;
	}

	public boolean isInsertingAllowed() {
		return insertingAllowed;
	}

	public void setInsertingAllowed(boolean insertingAllowed) {
		this.insertingAllowed = insertingAllowed;
	}

	public boolean isTakingAllowed() {
		return takingAllowed;
	}

	public void setTakingAllowed(boolean takingAllowed) {
		this.takingAllowed = takingAllowed;
	}

	public Predicate<ItemStack> getFilter() {
		return filter;
	}

	public void setFilter(Predicate<ItemStack> filter) {
		this.filter = filter;
	}

	public void addChangeListener(WMovableSlot owner, WMovableSlot.ChangeListener listener) {
		Objects.requireNonNull(owner, "owner");
		Objects.requireNonNull(listener, "listener");
		listeners.put(owner, listener);
	}

	@Override
	public boolean isEnabled() {
		return isVisible();
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
}
