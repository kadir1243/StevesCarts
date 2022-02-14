package vswe.stevescarts.screen.slot;

import io.github.cottonmc.cotton.gui.ValidatedSlot;
import net.minecraft.inventory.Inventory;

public class ModuleSlot extends ValidatedSlot {
	private boolean valid = true;
	private int animationProgress;

	public ModuleSlot(Inventory inventory, int index, int x, int y) {
		super(inventory, index, x, y);
	}

	public void validate(boolean valid) {
		this.valid = valid;
	}

	public boolean isValid() {
		return valid;
	}

	public void invalidate() {
		valid = false;
		if (animationProgress > 8) {
			animationProgress = 8;
		}
	}

	@Override
	public boolean isEnabled() {
		return this.valid;
	}
}
