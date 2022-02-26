package vswe.stevescarts.modules.storage;

import net.minecraft.util.math.MathHelper;
import vswe.stevescarts.entity.ModularMinecartEntity;
import vswe.stevescarts.modules.MinecartModuleType;
import vswe.stevescarts.util.DualAnimator;

public class ExtractingChestsModule extends ChestModule {
	public static final float OFFSET_START = -14.0F;
	public static final float OFFSET_END = -24.5F;
	private final DualAnimator animator = new DualAnimator();

	public ExtractingChestsModule(ModularMinecartEntity minecart, MinecartModuleType<?> type) {
		super(minecart, type, 18, 4);
	}

	@Override
	public boolean shouldAnimate() {
		return false;
	}

	@Override
	public void tick() {
		if (this.minecart.world.isClient) {
			this.animator.step();
		}
	}

	@Override
	public void onScreenOpen() {
		if (this.minecart.world.isClient) {
			this.animator.setOpen(true);
		}
	}

	@Override
	public void onScreenClose() {
		if (this.minecart.world.isClient) {
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
