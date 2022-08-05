package vswe.stevescarts.module;

import io.github.cottonmc.cotton.gui.widget.WPlainPanel;

import net.minecraft.entity.player.PlayerEntity;

import vswe.stevescarts.screen.CartHandler;

public interface Configurable {
	void configure(WPlainPanel panel, CartHandler handler, PlayerEntity player);

	default boolean shouldSkip() {
		return false;
	}
}
