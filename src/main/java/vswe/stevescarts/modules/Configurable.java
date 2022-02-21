package vswe.stevescarts.modules;

import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;

public interface Configurable {
	void configure(WPlainPanel panel, SyncedGuiDescription description);
}
