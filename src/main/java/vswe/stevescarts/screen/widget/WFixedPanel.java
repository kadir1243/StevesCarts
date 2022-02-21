package vswe.stevescarts.screen.widget;

import io.github.cottonmc.cotton.gui.widget.WPlainPanel;

/**
 * This class is a panel that is fixed in size.
 */
public class WFixedPanel extends WPlainPanel {
	@Override
	public boolean canResize() {
		return false;
	}
}
