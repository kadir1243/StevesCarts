package vswe.stevescarts.screen;

import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WBox;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import io.github.cottonmc.cotton.gui.widget.WWidget;
import io.github.cottonmc.cotton.gui.widget.data.Axis;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.Property;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandlerListener;
import vswe.stevescarts.entity.ModularMinecartEntity;
import vswe.stevescarts.modules.Configurable;
import vswe.stevescarts.screen.widget.WFixedPanel;

import java.lang.ref.WeakReference;
import java.util.List;

public class ModularCartHandler extends SyncedGuiDescription {
	private final List<Runnable> tickers = new ObjectArrayList<>();
	private final WeakReference<ModularMinecartEntity> minecartEntity;

	public ModularCartHandler(int syncId, PlayerInventory playerInventory, ModularMinecartEntity minecartEntity) {
		super(StevesCartsScreenHandlers.MODULAR_CART, syncId, playerInventory);
		this.minecartEntity = new WeakReference<>(minecartEntity);
		WPlainPanel rootPanel = new WFixedPanel();
		rootPanel.setInsets(new Insets(3, 0, 7, 0));
		rootPanel.setSize(400, 100);
		this.setRootPanel(rootPanel);
		WBox verticalBox = new WBox(Axis.VERTICAL);
		WBox box1 = new WBox(Axis.HORIZONTAL);
		WBox box2 = new WBox(Axis.HORIZONTAL);
		verticalBox.add(box1);
		verticalBox.add(box2);
		WBox boxToAdd = box1;
		boxToAdd.setSpacing(5);
		rootPanel.add(verticalBox, 20, 30);
		box1.setHorizontalAlignment(HorizontalAlignment.CENTER);
		List<Configurable> panels = minecartEntity.getModuleList().stream().filter(Configurable.class::isInstance).map(Configurable.class::cast).filter(c -> !c.shouldSkip()).toList();
		int totalWidth = 0;
		for (Configurable configurable : panels) {
			WPlainPanel inPanel = new WPlainPanel();
			configurable.configure(inPanel, this, playerInventory.player);
			inPanel.layout();
			if (inPanel.getWidth() + totalWidth > 400) {
				boxToAdd = box2;
			}
			totalWidth += inPanel.getWidth() + 5;
			boxToAdd.add(inPanel, inPanel.getWidth(), inPanel.getHeight());
		}
		rootPanel.layout();
		this.addCentered(this.createPlayerInventoryPanel(), rootPanel.getHeight());
		rootPanel.validate(this);
		this.minecartEntity.get().onScreenOpen();
	}

	public ModularCartHandler(int syncId, PlayerInventory playerInventory, PacketByteBuf buf) {
		this(syncId, playerInventory, (ModularMinecartEntity) playerInventory.player.world.getEntityById(buf.readInt()));
	}

	@Override
	public void close(PlayerEntity player) {
		super.close(player);
		this.minecartEntity.get().onScreenClose();
	}

	public ModularMinecartEntity getMinecartEntity() {
		return minecartEntity.get();
	}

	public void addCentered(WWidget widget, int y, int width, int height) {
		WPlainPanel root = (WPlainPanel) this.getRootPanel();
		root.add(widget, (root.getWidth() - width) / 2 - root.getInsets().left(), y, width, height);
	}

	public void addCentered(WWidget widget, int y) {
		WPlainPanel root = (WPlainPanel) this.getRootPanel();
		root.add(widget, (root.getWidth() - widget.getWidth()) / 2 - root.getInsets().left(), y);
	}

	@Override
	public void addProperties(PropertyDelegate propertyDelegate) {
		super.addProperties(propertyDelegate);
	}

	@Override
	public Property addProperty(Property property) {
		return super.addProperty(property);
	}

	@Override
	public void sendContentUpdates() {
		super.sendContentUpdates();
		this.tickers.forEach(Runnable::run);
	}

	public void addTicker(Runnable ticker) {
		this.tickers.add(ticker);
	}
}
