package vswe.stevescarts.modules.attachment;

import io.github.cottonmc.cotton.gui.networking.NetworkSide;
import io.github.cottonmc.cotton.gui.networking.ScreenNetworking;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;

import net.minecraft.entity.player.PlayerEntity;

import vswe.stevescarts.entity.ModularMinecartEntity;
import vswe.stevescarts.modules.Configurable;
import vswe.stevescarts.modules.MinecartModule;
import vswe.stevescarts.modules.MinecartModuleType;
import vswe.stevescarts.screen.ModularCartHandler;
import vswe.stevescarts.screen.StevesCartsScreenHandlers;
import vswe.stevescarts.screen.widget.WSeatButton;

public class SeatModule extends MinecartModule implements Configurable {
	public SeatModule(ModularMinecartEntity minecart, MinecartModuleType<?> type) {
		super(minecart, type);
	}

	@Override
	public void configure(WPlainPanel panel, ModularCartHandler handler, PlayerEntity player) {
		WLabel label = new WLabel(this.getType().getTranslationText());
		panel.add(label, 0, 0);
		WSeatButton seatButton = new WSeatButton();
		seatButton.setCanRide(handler.getMinecartEntity().getFirstPassenger() == null || handler.getMinecartEntity().hasPassenger(player));
		seatButton.setRiding(handler.getMinecartEntity().hasPassenger(player));
		panel.add(seatButton, 0, 15, 24, 12);
		seatButton.setOnClick(() -> {
			ScreenNetworking.of(handler, NetworkSide.CLIENT).send(StevesCartsScreenHandlers.PACKET_SEAT_CLICK, buf -> buf.writeBoolean(seatButton.isRiding()));
		});
		ScreenNetworking.of(handler, NetworkSide.SERVER).receive(StevesCartsScreenHandlers.PACKET_SEAT_CLICK, buf -> {
			boolean riding = buf.readBoolean();
			if (riding) {
				player.stopRiding();
				ScreenNetworking.of(handler, NetworkSide.SERVER).send(StevesCartsScreenHandlers.PACKET_SEAT_UPDATE, buf1 -> buf1.writeBoolean(false));
			} else {
				player.startRiding(handler.getMinecartEntity());
				ScreenNetworking.of(handler, NetworkSide.SERVER).send(StevesCartsScreenHandlers.PACKET_SEAT_UPDATE, buf1 -> buf1.writeBoolean(true));
			}
		});
		ScreenNetworking.of(handler, NetworkSide.CLIENT).receive(StevesCartsScreenHandlers.PACKET_SEAT_UPDATE, buf -> {
			seatButton.setRiding(buf.readBoolean());
		});
	}
}
