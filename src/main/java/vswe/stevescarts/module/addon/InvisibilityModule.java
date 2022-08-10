package vswe.stevescarts.module.addon;

import io.github.cottonmc.cotton.gui.networking.NetworkSide;
import io.github.cottonmc.cotton.gui.networking.ScreenNetworking;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.entity.CartEntity;
import vswe.stevescarts.module.CartModule;
import vswe.stevescarts.module.Configurable;
import vswe.stevescarts.module.ModuleType;
import vswe.stevescarts.screen.CartHandler;
import vswe.stevescarts.screen.widget.WCustomButton;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class InvisibilityModule extends CartModule implements Configurable {
	public InvisibilityModule(CartEntity minecart, ModuleType<?> type) {
		super(minecart, type);
	}

	public static final Identifier PACKET_TOGGLE_INVISIBILITY = StevesCarts.id("toggle_invisibility");

	@Override
	public void configure(WPlainPanel panel, CartHandler handler, PlayerEntity player) {
//		WLabel label = new WLabel(this.getType().getTranslationText()); TODO
//		panel.add(label, 0, 0);
		WCustomButton button = new WCustomButton(WCustomButton.FG_INVISIBLE, WCustomButton.FG_UNINVISIBLE, Text.translatable("screen.stevescarts.cart.invisibility"));
		panel.add(button, 0, 0, 25, 10);
		if (this.getEntity().isInvisible()) {
			button.changeTexture();
		}
		button.setOnClick(() -> ScreenNetworking.of(handler, NetworkSide.CLIENT).send(PACKET_TOGGLE_INVISIBILITY, buf -> {}));
		ScreenNetworking.of(handler, NetworkSide.SERVER).receive(PACKET_TOGGLE_INVISIBILITY, buf -> this.getEntity().setInvisible(!this.getEntity().isInvisible()));
	}
}
