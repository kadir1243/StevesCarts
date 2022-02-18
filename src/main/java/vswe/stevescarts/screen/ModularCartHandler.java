package vswe.stevescarts.screen;

import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WListPanel;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import io.github.cottonmc.cotton.gui.widget.WWidget;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import vswe.stevescarts.entity.ModularMinecartEntity;
import vswe.stevescarts.modules.Configurable;
import vswe.stevescarts.screen.widget.WFixedPanel;

import java.util.List;
import java.util.stream.Collectors;

public class ModularCartHandler extends SyncedGuiDescription {
	public ModularCartHandler(int syncId, PlayerInventory playerInventory, ModularMinecartEntity minecartEntity) {
		super(StevesCartsScreenHandlers.MODULAR_CART, syncId, playerInventory);
		WPlainPanel panel = new WFixedPanel();
		panel.setInsets(new Insets(3, 0, 7, 0));
		panel.setSize(256, 270);
		this.setRootPanel(panel);
		List<Configurable> panels = minecartEntity.getModuleList().stream().filter(Configurable.class::isInstance).map(Configurable.class::cast).collect(Collectors.toList());
		WListPanel<Configurable, WPlainPanel> panelList = new WListPanel<>(panels, WPlainPanel::new, Configurable::configure);
		addCentered(panelList, 16, 230, 160);
		addCentered(createPlayerInventoryPanel(true), 184);
		panel.validate(this);
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
