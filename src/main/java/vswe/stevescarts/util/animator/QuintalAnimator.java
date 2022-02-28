package vswe.stevescarts.util.animator;

import net.minecraft.block.entity.ChestLidAnimator;

public class QuintalAnimator {
	private final ChestLidAnimator first = new ChestLidAnimator();
	private final QuadralAnimator next = new QuadralAnimator();
	private boolean open = false;

	public void setOpen(boolean open) {
		this.open = open;
		this.first.setOpen(open);
		this.next.setOpen(open);
	}

	public void step() {
		if (this.open) {
			this.first.step();
			if (this.first.getProgress(1.0F) >= 1.0F) {
				this.next.step();
			}
		} else {
			this.next.step();
			if (this.next.getFirstProgress(1.0F) <= 0.0F) {
				this.first.step();
			}
		}
	}

	public float getFirstProgress(float tickDelta) {
		return this.first.getProgress(tickDelta);
	}

	public float getSecondProgress(float tickDelta) {
		return this.next.getFirstProgress(tickDelta);
	}

	public float getThirdProgress(float tickDelta) {
		return this.next.getSecondProgress(tickDelta);
	}

	public float getFourthProgress(float tickDelta) {
		return this.next.getThirdProgress(tickDelta);
	}

	public float getFifthProgress(float tickDelta) {
		return this.next.getFourthProgress(tickDelta);
	}
}
