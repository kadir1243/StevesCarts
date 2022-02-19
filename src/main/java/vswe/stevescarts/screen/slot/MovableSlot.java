package vswe.stevescarts.screen.slot;

import io.github.cottonmc.cotton.gui.ValidatedSlot;
import net.minecraft.inventory.Inventory;
import vswe.stevescarts.mixins.SlotAccessor;

/**
 * A slot that can be moved around.
 */
public class MovableSlot extends ValidatedSlot {
	public MovableSlot(Inventory inventory, int index, int x, int y) {
		super(inventory, index, x, y);
	}

	public void setSlotX(int x) {
		((SlotAccessor) this).setX(x);
	}

	public void setSlotY(int y) {
		((SlotAccessor) this).setY(y);
	}
}
