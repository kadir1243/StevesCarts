package vswe.stevescarts.screen;

import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WBox;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import io.github.cottonmc.cotton.gui.widget.WWidget;
import io.github.cottonmc.cotton.gui.widget.data.Axis;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import vswe.stevescarts.entity.ModularMinecartEntity;
import vswe.stevescarts.modules.storage.StorageModule;
import vswe.stevescarts.screen.widget.WFixedPanel;

import java.lang.ref.WeakReference;
import java.util.List;

public class ModularCartHandler extends SyncedGuiDescription {
	private final WeakReference<ModularMinecartEntity> minecartEntity;

	public ModularCartHandler(int syncId, PlayerInventory playerInventory, ModularMinecartEntity minecartEntity) {
		super(StevesCartsScreenHandlers.MODULAR_CART, syncId, playerInventory);
		this.minecartEntity = new WeakReference<>(minecartEntity);
		WPlainPanel panel = new WFixedPanel();
		panel.setInsets(new Insets(3, 0, 7, 0));
		panel.setSize(400, 280);
		this.setRootPanel(panel);
		WBox box = new WBox(Axis.HORIZONTAL);
		panel.add(box, 20, 30);
		box.setHorizontalAlignment(HorizontalAlignment.CENTER);
		List<StorageModule> panels = minecartEntity.getModuleList().stream().filter(StorageModule.class::isInstance).map(StorageModule.class::cast).toList();
		for (StorageModule storageModule : panels) {
			WPlainPanel inPanel = new WPlainPanel();
			storageModule.configure(inPanel);
			box.add(inPanel, inPanel.getWidth(), inPanel.getHeight());
		}

		panel.add(this.createPlayerInventoryPanel(), 10, 184);
		panel.validate(this);
		this.minecartEntity.get().onScreenOpen();
	}

	@Override
	public void close(PlayerEntity player) {
		super.close(player);
		this.minecartEntity.get().onScreenClose();
	}

	public void addCentered(WWidget widget, int y, int width, int height) {
		WPlainPanel root = (WPlainPanel) this.getRootPanel();
		root.add(widget, (root.getWidth() - width) / 2 - root.getInsets().left(), y, width, height);
	}

	public ModularCartHandler(int syncId, PlayerInventory playerInventory, PacketByteBuf buf) {
		this(syncId, playerInventory, (ModularMinecartEntity) playerInventory.player.world.getEntityById(buf.readInt()));
	}

	public void addCentered(WWidget widget, int y) {
		WPlainPanel root = (WPlainPanel) this.getRootPanel();
		root.add(widget, (root.getWidth() - widget.getWidth()) / 2 - root.getInsets().left(), y);
	}
}
