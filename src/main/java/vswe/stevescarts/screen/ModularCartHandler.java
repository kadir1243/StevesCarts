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
import vswe.stevescarts.modules.Configurable;
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
		panel.setSize(400, 100);
		this.setRootPanel(panel);
		WBox verticalBox = new WBox(Axis.VERTICAL);
		WBox box1 = new WBox(Axis.HORIZONTAL);
		WBox box2 = new WBox(Axis.HORIZONTAL);
		WBox boxToAdd = box1;
		boxToAdd.setSpacing(5);
		panel.add(verticalBox, 20, 30);
		box1.setHorizontalAlignment(HorizontalAlignment.CENTER);
		List<Configurable> panels = minecartEntity.getModuleList().stream().filter(Configurable.class::isInstance).map(Configurable.class::cast).toList();
		int totalWidth = 0;
		for (Configurable configurable : panels) {
			WPlainPanel inPanel = new WPlainPanel();
			configurable.configure(inPanel, this);
			inPanel.layout();
			if (inPanel.getWidth() + totalWidth > 400) {
				boxToAdd = box2;
			}
			totalWidth += inPanel.getWidth() + 5;
			boxToAdd.add(inPanel, inPanel.getWidth(), inPanel.getHeight());
		}
		panel.layout();
		panel.add(this.createPlayerInventoryPanel(), 10, panel.getHeight() - 10);
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
