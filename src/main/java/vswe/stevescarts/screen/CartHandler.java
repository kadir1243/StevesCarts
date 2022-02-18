package vswe.stevescarts.screen;

import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import io.github.cottonmc.cotton.gui.widget.WListPanel;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.entity.ModularMinecartEntity;
import vswe.stevescarts.mixins.ServerPlayerEntityAccessor;
import vswe.stevescarts.modules.Controllable;

public class CartHandler extends SyncedGuiDescription {
	public CartHandler(int syncId, PlayerInventory playerInventory, ModularMinecartEntity minecartEntity) {
		super(null, syncId, playerInventory);
		WPlainPanel panel = new WPlainPanel();
		this.setRootPanel(panel);
		panel.setSize(20, 20);
		WListPanel<Controllable, WPlainPanel> panelList = new WListPanel<>(minecartEntity.getModuleList().stream().filter(Controllable.class::isInstance).map(Controllable.class::cast).toList(), WPlainPanel::new, Controllable::configure);
		panel.add(panelList, 10, 10);
		panel.validate(this);
	}

	public static void openCartScreen(ServerPlayerEntity player, ModularMinecartEntity minecartEntity) {
		if (player.currentScreenHandler != player.playerScreenHandler) {
			player.closeHandledScreen();
		}
		((ServerPlayerEntityAccessor) player).invokeIncrementScreenHandlerSyncId();
		CartHandler handler = new CartHandler(((ServerPlayerEntityAccessor) player).getScreenHandlerSyncId(), player.getInventory(), minecartEntity);
		PacketByteBuf buf = PacketByteBufs.create();
		buf.writeByte(handler.syncId);
		buf.writeByte(minecartEntity.getId());
		ServerPlayNetworking.send(player, StevesCartsScreenHandlers.PACKET_OPEN_CART, buf);
	}

	@Environment(EnvType.CLIENT)
	public static void handleOpen(MinecraftClient client, int syncId, int cartId) {
		Entity entity = client.world.getEntityById(cartId);
		if (!(entity instanceof ModularMinecartEntity cart)) {
			StevesCarts.LOGGER.error("Received open cart packet for non-cart entity");
			return;
		}
		CartHandler handler = new CartHandler(syncId, client.player.getInventory(), cart);
		Screen screen = new CottonInventoryScreen<>(handler, client.player.getInventory());
		client.setScreen(screen);
	}
}
