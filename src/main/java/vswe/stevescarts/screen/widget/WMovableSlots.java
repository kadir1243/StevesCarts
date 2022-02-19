package vswe.stevescarts.screen.widget;

import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.inventory.Inventory;
import vswe.stevescarts.screen.slot.MovableSlot;

import java.util.ArrayList;
import java.util.List;

public class WMovableSlots extends WItemSlot {
	private final List<MovableSlot> slots = new ArrayList<>();
	private final int slotsWide;
	private final int slotsHigh;

	public WMovableSlots(Inventory inventory, int startIndex, int slotsWide, int slotsHigh, boolean big) {
		super(inventory, startIndex, slotsWide, slotsHigh, big);
		this.slotsWide = slotsWide;
		this.slotsHigh = slotsHigh;
	}

	@Override
	protected MovableSlot createSlotPeer(Inventory inventory, int index, int x, int y) {
		MovableSlot slot = new MovableSlot(inventory, index, x, y);
		slots.add(slot);
		return slot;
	}

	@Override
	public void paint(MatrixStack matrices, int x, int y, int mouseX, int mouseY) {
		for (int y1 = 0; y1 < slotsHigh; y1++) {
			for (int x1 = 0; x1 < slotsWide; x1++) {
				MovableSlot slot = slots.get(y1 * slotsWide + x1);
				slot.setSlotY(y  + (y1 * 18) + 1);
				int maxY = this.getParent().getHeight() + this.getParent().getAbsoluteY();
				int minY = this.getParent().getAbsoluteY();
				if (slot.y + 18 > maxY || slot.y < minY) {
					slot.setVisible(false);
				}
			}
		}
		super.paint(matrices, x, y, mouseX, mouseY);
	}
}
