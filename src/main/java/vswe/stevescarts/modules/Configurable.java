package vswe.stevescarts.modules;

import io.github.cottonmc.cotton.gui.widget.WPlainPanel;

import net.minecraft.entity.player.PlayerEntity;

import vswe.stevescarts.screen.ModularCartHandler;

public interface Configurable {
	void configure(WPlainPanel panel, ModularCartHandler handler, PlayerEntity player);

	default boolean shouldSkip() {
		return false;
	}
}
