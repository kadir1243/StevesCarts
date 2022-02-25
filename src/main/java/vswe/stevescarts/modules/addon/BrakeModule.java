package vswe.stevescarts.modules.addon;

import io.github.cottonmc.cotton.gui.networking.NetworkSide;
import io.github.cottonmc.cotton.gui.networking.ScreenNetworking;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.TranslatableText;
import vswe.stevescarts.entity.ModularMinecartEntity;
import vswe.stevescarts.modules.Configurable;
import vswe.stevescarts.modules.MinecartModule;
import vswe.stevescarts.modules.MinecartModuleType;
import vswe.stevescarts.screen.ModularCartHandler;
import vswe.stevescarts.screen.StevesCartsScreenHandlers;
import vswe.stevescarts.screen.widget.WBrakeButton;

public class BrakeModule extends MinecartModule implements Configurable, Toggleable {
	private boolean active = false;

	public BrakeModule(ModularMinecartEntity minecart, MinecartModuleType<?> type) {
		super(minecart, type);
	}

	@Override
	public boolean shouldMove() {
		return !this.active;
	}

	@Override
	public boolean isActive() {
		return this.active;
	}

	@Override
	public void configure(WPlainPanel panel, ModularCartHandler handler, PlayerEntity player) {
		WLabel label = new WLabel(this.getType().getTranslationText());
		panel.add(label, 0, 0);
		WBrakeButton brakeButton = new WBrakeButton(WBrakeButton.FG_STOP, WBrakeButton.FG_CONTINUE, new TranslatableText("screen.stevescarts.cart.brake"));
		panel.add(brakeButton, 0, 10);
		brakeButton.setOnClick(() -> {
			brakeButton.changeTexture();
			ScreenNetworking.of(handler, NetworkSide.CLIENT).send(StevesCartsScreenHandlers.PACKET_BRAKE, buf -> buf.writeBoolean(brakeButton.hasSecondTexture()));
		});
		if (this.active) {
			brakeButton.changeTexture();
		}
		WBrakeButton reverseButton = new WBrakeButton(WBrakeButton.FG_REVERSE, new TranslatableText("screen.stevescarts.cart.reverse"));
		panel.add(reverseButton, 0, 23);
		reverseButton.setOnClick(() -> ScreenNetworking.of(handler, NetworkSide.CLIENT).send(StevesCartsScreenHandlers.PACKET_REVERSE, buf -> {}));
		panel.setSize(30, 50);
		ScreenNetworking.of(handler, NetworkSide.SERVER).receive(StevesCartsScreenHandlers.PACKET_BRAKE, buf -> this.active = buf.readBoolean());
		ScreenNetworking.of(handler, NetworkSide.SERVER).receive(StevesCartsScreenHandlers.PACKET_REVERSE, buf -> this.minecart.reverse());
	}
}
