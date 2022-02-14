package vswe.stevescarts.screen.widget;

import io.github.cottonmc.cotton.gui.ValidatedSlot;
import io.github.cottonmc.cotton.gui.client.BackgroundPainter;
import io.github.cottonmc.cotton.gui.client.ScreenDrawing;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.data.Texture;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.inventory.Inventory;
import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.screen.slot.ModuleSlot;

public class WModuleSlot extends WItemSlot {
	@Environment(EnvType.CLIENT)
	private static final Texture OPEN_TEXTURE = new Texture(StevesCarts.id("textures/gui/module_slot.png"), 0.0F, 0.0F, 0.28125F, 0.5625F);
	@Environment(EnvType.CLIENT)
	private static final BackgroundPainter PAINTER = (matrices, left, top, panel) -> {
		WModuleSlot slot = (WModuleSlot) panel;
		for(int x = 0; x < slot.getWidth()/18; ++x) {
			for (int y = 0; y < slot.getHeight() / 18; ++y) {
				ScreenDrawing.texturedRect(matrices, left + x * 18, top + y * 18, 18, 18, OPEN_TEXTURE, 0xFFFFFFFF);
			}
		}
	};

	public WModuleSlot(Inventory inventory, int startIndex, int slotsWide, int slotsHigh) {
		super(inventory, startIndex, slotsWide, slotsHigh, false);
	}

	@Override
	protected ValidatedSlot createSlotPeer(Inventory inventory, int index, int x, int y) {
		return new ModuleSlot(inventory, index, x, y);
	}

	@Override
	public void addPainters() {
		this.setBackgroundPainter(PAINTER);
	}
}
