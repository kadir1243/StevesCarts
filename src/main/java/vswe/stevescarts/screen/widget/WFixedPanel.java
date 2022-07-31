package vswe.stevescarts.screen.widget;

import io.github.cottonmc.cotton.gui.widget.WPlainPanel;

public class WFixedPanel extends WPlainPanel {
	@Override
	public boolean canResize() {
		return false;
	}
}
