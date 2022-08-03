package vswe.stevescarts.screen.widget;

import io.github.cottonmc.cotton.gui.ValidatedSlot;

import net.minecraft.block.entity.ChestLidAnimator;
import net.minecraft.block.entity.LidOpenable;
import net.minecraft.inventory.Inventory;

public class AnimatableSlot extends ValidatedSlot implements LidOpenable {
	private final ChestLidAnimator animator = new ChestLidAnimator();
	private boolean valid = true;
	private int tickOffset;

	public AnimatableSlot(Inventory inventory, int index, int x, int y) {
		super(inventory, index, x, y);
	}

	public void validate() {
		this.valid = true;
		this.animator.setOpen(true);
	}

	public void validate(int tickOffset) {
		this.validate();
		this.tickOffset = tickOffset;
	}

	public boolean isValid() {
		return valid;
	}

	public void invalidate() {
		valid = false;
		this.animator.setOpen(false);
		this.tickOffset = 0;
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
		if (tickOffset > 0) {
			tickOffset--;
		} else {
			this.animator.step();
		}
	}
}
