package vswe.stevescarts.util.animator;

import org.junit.jupiter.api.Test;

import net.minecraft.block.entity.ChestLidAnimator;

import static org.junit.jupiter.api.Assertions.*;

class ChestLidAnimatorTest {
	@Test
	public void test() {
		ChestLidAnimator animator = new ChestLidAnimator();
		animator.setOpen(true);
		for (int i = 0; i < 10; i++) {
			animator.step();
		}
		assertEquals(Math.round(animator.getProgress(1.0F) * 10) / 10.0F, 1.0F);
		animator.setOpen(false);
		animator.step();
		assertEquals(Math.round(animator.getProgress(1.0F) * 10) / 10.0F, 0.9F);
		animator.step();
		assertEquals(Math.round(animator.getProgress(1.0F) * 10) / 10.0F, 0.8F);
		animator.step();
		assertEquals(Math.round(animator.getProgress(1.0F) * 10) / 10.0F, 0.7F);
		animator.step();
		assertEquals(Math.round(animator.getProgress(1.0F) * 10) / 10.0F, 0.6F);
	}
}
