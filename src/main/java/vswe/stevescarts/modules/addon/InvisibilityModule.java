package vswe.stevescarts.modules.addon;

import io.github.cottonmc.cotton.gui.networking.NetworkSide;
import io.github.cottonmc.cotton.gui.networking.ScreenNetworking;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.TranslatableText;
import vswe.stevescarts.entity.ModularMinecartEntity;
import vswe.stevescarts.modules.Configurable;
import vswe.stevescarts.modules.MinecartModule;
import vswe.stevescarts.modules.MinecartModuleType;
import vswe.stevescarts.screen.ModularCartHandler;
import vswe.stevescarts.screen.StevesCartsScreenHandlers;

public class InvisibilityModule extends MinecartModule implements Configurable {
	public InvisibilityModule(ModularMinecartEntity minecart, MinecartModuleType<?> type) {
		super(minecart, type);
	}

	@Override
	public void configure(WPlainPanel panel, ModularCartHandler handler, PlayerEntity player) {
		WButton button = new WButton(new TranslatableText("screen.stevescarts.cart.invisibility"));
		panel.add(button, 0, 0, 50, 10);
		button.setOnClick(() -> ScreenNetworking.of(handler, NetworkSide.CLIENT).send(StevesCartsScreenHandlers.PACKET_TOGGLE_INVISIBILITY, buf -> {}));
		ScreenNetworking.of(handler, NetworkSide.SERVER).receive(StevesCartsScreenHandlers.PACKET_TOGGLE_INVISIBILITY, buf -> this.getMinecart().setInvisible(!this.getMinecart().isInvisible()));
	}
}
