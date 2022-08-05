package vswe.stevescarts.module.storage;

import vswe.stevescarts.entity.CartEntity;
import vswe.stevescarts.module.ModuleType;
import vswe.stevescarts.util.DualAnimator;

import net.minecraft.util.math.MathHelper;

public class ExtractingChestsModule extends ChestModule {
	public static final float OFFSET_START = -14.0F;
	public static final float OFFSET_END = -24.5F;
	private final DualAnimator animator = new DualAnimator();

	public ExtractingChestsModule(CartEntity minecart, ModuleType<?> type) {
		super(minecart, type, 18, 4);
	}

	@Override
	public boolean shouldAnimate() {
		return false;
	}

	@Override
	public void tick() {
		if (this.getEntity().world.isClient) {
			this.animator.step();
		}
	}

	@Override
	public void onScreenOpen() {
		if (this.getEntity().world.isClient) {
			this.animator.setOpen(true);
		}
	}

	@Override
	public void onScreenClose() {
		if (this.getEntity().world.isClient) {
			this.animator.setOpen(false);
		}
	}

	public float getTranslationOffset(float delta) {
		return MathHelper.lerp(this.animator.getFirstProgress(delta), OFFSET_START, OFFSET_END);
	}

	public float getRotationOffset(float delta) {
		return this.animator.getNextProgress(delta);
	}
}
