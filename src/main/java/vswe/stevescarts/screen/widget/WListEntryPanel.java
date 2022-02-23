package vswe.stevescarts.screen.widget;

import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import io.github.cottonmc.cotton.gui.widget.WWidget;

public class WListEntryPanel extends WPlainPanel {
	@Override
	public void layout() {
		super.layout();
		for (WWidget w : this.children) {
			if (!(w instanceof WMovableSlot slot)) {
				continue;
			}
			slot.validate(this.host);
			((WInventoryListPanel<WListEntryPanel>) this.parent).validatePlayerInv = true;
		}
	}
}
