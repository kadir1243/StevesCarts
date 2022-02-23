package vswe.stevescarts.screen.widget;

import io.github.cottonmc.cotton.gui.widget.WListPanel;
import io.github.cottonmc.cotton.gui.widget.WWidget;
import io.github.cottonmc.cotton.gui.widget.data.InputResult;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class WInventoryListPanel<D> extends WListPanel<D, WSlotPanel> {
	public WInventoryListPanel(List<D> data, BiConsumer<D, WSlotPanel> configurator) {
		super(data, WSlotPanel::new, configurator);
	}

	@Override
	public InputResult onMouseScroll(int x, int y, double amount) {
		return super.onMouseScroll(x, y, amount);
	}

	@Override
	public void layout() {
		super.layout();
	}

	public boolean isVisible(int x, int y) {
		return this.x >= this.getAbsoluteX() && this.x <= this.getAbsoluteX() + this.getWidth() && this.y >= this.getAbsoluteY() && this.y <= this.getAbsoluteY() + this.getHeight();
	}
}
