package vswe.stevescarts.modules;

import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import net.minecraft.entity.player.PlayerEntity;
import vswe.stevescarts.screen.ModularCartHandler;

public interface AntiConfigurable extends Configurable {
	@Override
	default void configure(WPlainPanel panel, ModularCartHandler handler, PlayerEntity player) {
	}

	@Override
	default boolean shouldSkip() {
		return true;
	}
}
