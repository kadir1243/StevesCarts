package vswe.stevescarts.module.attachment;

import io.github.cottonmc.cotton.gui.networking.NetworkSide;
import io.github.cottonmc.cotton.gui.networking.ScreenNetworking;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.entity.CartEntity;
import vswe.stevescarts.module.CartModule;
import vswe.stevescarts.module.Configurable;
import vswe.stevescarts.module.ModuleType;
import vswe.stevescarts.screen.CartHandler;
import vswe.stevescarts.screen.widget.WSeatButton;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

public class SeatModule extends CartModule implements Configurable {
	public static final Identifier PACKET_SEAT_CLICK = StevesCarts.id("seat_click");
	public static final Identifier PACKET_SEAT_UPDATE = StevesCarts.id("seat_update");

	public SeatModule(CartEntity entity, ModuleType<?> type) {
		super(entity, type);
	}

	@Override
	public void configure(WPlainPanel panel, CartHandler handler, PlayerEntity player) {
		WLabel label = new WLabel(this.getType().getTranslationText());
		panel.add(label, 0, 0);
		WSeatButton seatButton = new WSeatButton();
		seatButton.setCanRide(handler.getMinecartEntity().getFirstPassenger() == null || handler.getMinecartEntity().hasPassenger(player));
		seatButton.setRiding(handler.getMinecartEntity().hasPassenger(player));
		panel.add(seatButton, 0, 15, 24, 12);
		seatButton.setOnClick(() -> {
			ScreenNetworking.of(handler, NetworkSide.CLIENT).send(PACKET_SEAT_CLICK, buf -> buf.writeBoolean(seatButton.isRiding()));
		});
		ScreenNetworking.of(handler, NetworkSide.SERVER).receive(PACKET_SEAT_CLICK, buf -> {
			boolean riding = buf.readBoolean();
			if (riding) {
				player.stopRiding();
				ScreenNetworking.of(handler, NetworkSide.SERVER).send(PACKET_SEAT_UPDATE, buf1 -> buf1.writeBoolean(false));
			} else {
				player.startRiding(handler.getMinecartEntity());
				ScreenNetworking.of(handler, NetworkSide.SERVER).send(PACKET_SEAT_UPDATE, buf1 -> buf1.writeBoolean(true));
			}
		});
		ScreenNetworking.of(handler, NetworkSide.CLIENT).receive(PACKET_SEAT_UPDATE, buf -> {
			seatButton.setRiding(buf.readBoolean());
		});
	}
}
