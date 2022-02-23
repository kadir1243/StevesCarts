package vswe.stevescarts.screen.widget;

import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WListPanel;
import io.github.cottonmc.cotton.gui.widget.WPlayerInvPanel;
import io.github.cottonmc.cotton.gui.widget.data.InputResult;

import java.util.List;
import java.util.function.BiConsumer;

public class WInventoryListPanel<D> extends WListPanel<D, WListEntryPanel> {
	private final WPlayerInvPanel playerInv;
	public boolean validatePlayerInv = false;

	public WInventoryListPanel(List<D> data, BiConsumer<D, WListEntryPanel> configurator, WPlayerInvPanel playerInv) {
		super(data, WListEntryPanel::new, configurator);
		this.playerInv = playerInv;
	}

	@Override
	public InputResult onMouseScroll(int x, int y, double amount) {
		return super.onMouseScroll(x, y, amount);
	}

	@Override
	public void layout() {
		super.layout();
		this.streamChildren().forEach(w -> {
			if (!(w instanceof WListEntryPanel list)) {
				return;
			}
			list.setHost(this.getHost());
			list.layout();
		});
	}

	public boolean isVisible(int x, int y) {
		return x >= this.getAbsoluteX() && x <= this.getAbsoluteX() + this.getWidth() && y >= this.getAbsoluteY() && y <= this.getAbsoluteY() + this.getHeight();
	}
}
