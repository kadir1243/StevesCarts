package vswe.stevescarts.screen.widget;

import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import io.github.cottonmc.cotton.gui.widget.WWidget;
import vswe.stevescarts.mixins.SlotAccessor;

public class WListEntryPanel extends WPlainPanel {
	@Override
	public void layout() {
		for (WWidget w : this.children) {
			if (!(w instanceof WMovableSlot slot)) {
				continue;
			}

			int index = slot.startIndex;
			for (int y = 0; y < slot.slotsHigh; y++) {
				for (int x = 0; x < slot.slotsWide; x++) {
					int finalIndex = index;
					MovableSlot mSlot = slot.getPeers().stream().filter(s -> s.getIndex() == finalIndex).findFirst().orElse(null);
					if (mSlot == null) {
						continue;
					}
					int xCoord = slot.getAbsoluteX() + (x * 18) + 1;
					int yCoord = slot.getAbsoluteY() + (y * 18) + 1;
					((SlotAccessor) mSlot).setX(xCoord);
					((SlotAccessor) mSlot).setY(yCoord);
					mSlot.setVisible(((WInventoryListPanel<?>) this.parent).isVisible(xCoord, yCoord));
					index++;
				}
			}
		}
	}
}
