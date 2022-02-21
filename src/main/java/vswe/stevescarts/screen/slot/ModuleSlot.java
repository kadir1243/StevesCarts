package vswe.stevescarts.screen.slot;

import io.github.cottonmc.cotton.gui.ValidatedSlot;
import net.minecraft.block.entity.ChestLidAnimator;
import net.minecraft.client.block.ChestAnimationProgress;
import net.minecraft.inventory.Inventory;

public class ModuleSlot extends ValidatedSlot implements ChestAnimationProgress {
	private boolean valid = true;
	private final ChestLidAnimator animator = new ChestLidAnimator();

	public ModuleSlot(Inventory inventory, int index, int x, int y) {
		super(inventory, index, x, y);
	}

	public void validate() {
		this.valid = true;
		this.animator.setOpen(true);
	}

	public boolean isValid() {
		return valid;
	}

	public void invalidate() {
		valid = false;
		this.animator.setOpen(false);
	}

	@Override
	public boolean isEnabled() {
		return this.valid;
	}

	@Override
	public float getAnimationProgress(float tickDelta) {
		return this.animator.getProgress(tickDelta);
	}

	public void step() {
		this.animator.step();
	}
}
