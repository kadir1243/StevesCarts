package vswe.stevescarts.screen;

import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WBox;
import io.github.cottonmc.cotton.gui.widget.WListPanel;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import io.github.cottonmc.cotton.gui.widget.WWidget;
import io.github.cottonmc.cotton.gui.widget.data.Axis;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import vswe.stevescarts.entity.ModularMinecartEntity;
import vswe.stevescarts.modules.Configurable;
import vswe.stevescarts.modules.storage.StorageModule;
import vswe.stevescarts.screen.widget.WFixedPanel;
import vswe.stevescarts.screen.widget.WInventoryListPanel;

import java.lang.ref.WeakReference;
import java.util.List;

public class ModularCartHandler extends SyncedGuiDescription {
	private final WeakReference<ModularMinecartEntity> minecartEntity;

	public ModularCartHandler(int syncId, PlayerInventory playerInventory, ModularMinecartEntity minecartEntity) {
		super(StevesCartsScreenHandlers.MODULAR_CART, syncId, playerInventory);
		this.minecartEntity = new WeakReference<>(minecartEntity);
		WPlainPanel rootPanel = new WFixedPanel();
		rootPanel.setInsets(new Insets(3, 0, 7, 0));
		rootPanel.setSize(280, 300);
		this.setRootPanel(rootPanel);
		List<Configurable> configurables = minecartEntity.getModuleList().stream().filter(Configurable.class::isInstance).map(Configurable.class::cast).toList();
		WInventoryListPanel<Configurable, WPlainPanel> panels = new WInventoryListPanel<>(configurables, WPlainPanel::new, (configurable, configPanel) -> configurable.configure(configPanel, this));
		panels.setListItemHeight(panels.streamChildren().filter(WPlainPanel.class::isInstance).mapToInt(WWidget::getHeight).filter(height -> height >= 0).max().orElse(5));

		this.addCentered(this.createPlayerInventoryPanel(), 184);
		rootPanel.validate(this);
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
