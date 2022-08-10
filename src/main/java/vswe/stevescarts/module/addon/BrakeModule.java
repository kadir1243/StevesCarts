package vswe.stevescarts.module.addon;

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
import vswe.stevescarts.screen.widget.WCustomButton;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class BrakeModule extends CartModule implements Configurable, Toggleable {
	private boolean active = false;

	public BrakeModule(CartEntity minecart, ModuleType<?> type) {
		super(minecart, type);
	}

	@Override
	public void writeToNbt(NbtCompound nbt) {
		nbt.putBoolean("active", this.active);
		super.writeToNbt(nbt);
	}

	@Override
	public void readFromNbt(NbtCompound nbt) {
		this.active = nbt.getBoolean("active");
		super.readFromNbt(nbt);
	}

	@Override
	public boolean shouldMove() {
		return !this.active;
	}

	@Override
	public boolean isActive() {
		return this.active;
	}

	public static final Identifier PACKET_BRAKE = StevesCarts.id("brake");
	public static final Identifier PACKET_REVERSE = StevesCarts.id("reverse");

	@Override
	public void configure(WPlainPanel panel, CartHandler handler, PlayerEntity player) {
		WLabel label = new WLabel(this.getType().getTranslationText());
		panel.add(label, 0, 0);
		WCustomButton brakeButton = new WCustomButton(WCustomButton.FG_STOP, WCustomButton.FG_CONTINUE, Text.translatable("screen.stevescarts.cart.brake"));
		panel.add(brakeButton, 0, 10, 24, 12);
		brakeButton.setOnClick(() -> {
			brakeButton.changeTexture();
			ScreenNetworking.of(handler, NetworkSide.CLIENT).send(PACKET_BRAKE, buf -> buf.writeBoolean(brakeButton.hasSecondTexture()));
		});
		if (this.active) {
			brakeButton.changeTexture();
		}
		WCustomButton reverseButton = new WCustomButton(WCustomButton.FG_REVERSE, Text.translatable("screen.stevescarts.cart.reverse"));
		panel.add(reverseButton, 0, 23, 24, 12);
		reverseButton.setOnClick(() -> ScreenNetworking.of(handler, NetworkSide.CLIENT).send(PACKET_REVERSE, buf -> {}));
		panel.setSize(30, 50);
		ScreenNetworking.of(handler, NetworkSide.SERVER).receive(PACKET_BRAKE, buf -> this.active = buf.readBoolean());
		ScreenNetworking.of(handler, NetworkSide.SERVER).receive(PACKET_REVERSE, buf -> this.getEntity().reverse());
	}
}
