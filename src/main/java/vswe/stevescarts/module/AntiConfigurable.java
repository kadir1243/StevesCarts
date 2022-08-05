package vswe.stevescarts.module;

import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import vswe.stevescarts.screen.CartHandler;

import net.minecraft.entity.player.PlayerEntity;

public interface AntiConfigurable extends Configurable {
	@Override
	default void configure(WPlainPanel panel, CartHandler handler, PlayerEntity player) {
	}

	@Override
	default boolean shouldSkip() {
		return true;
	}
}
