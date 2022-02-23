package vswe.stevescarts.screen.widget;

import io.github.cottonmc.cotton.gui.GuiDescription;
import io.github.cottonmc.cotton.gui.client.BackgroundPainter;
import io.github.cottonmc.cotton.gui.client.ScreenDrawing;
import io.github.cottonmc.cotton.gui.impl.VisualLogger;
import io.github.cottonmc.cotton.gui.impl.client.NarrationMessages;
import io.github.cottonmc.cotton.gui.widget.WWidget;
import io.github.cottonmc.cotton.gui.widget.data.InputResult;
import io.github.cottonmc.cotton.gui.widget.icon.Icon;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.screen.narration.NarrationPart;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.jetbrains.annotations.Nullable;
import vswe.stevescarts.mixins.ScreenHandlerAccessor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

// modified version of WMovableSlot
public class WMovableSlot extends WWidget {
	public static BackgroundPainter SLOT = (matrices, left, top, panel) -> {
		if (!(panel instanceof WMovableSlot)) {
			ScreenDrawing.drawBeveledPanel(matrices, left-1, top-1, panel.getWidth()+2, panel.getHeight()+2, 0xB8000000, 0x4C000000, 0xB8FFFFFF);
		} else {
			WMovableSlot slot = (WMovableSlot)panel;
			for(int x = 0; x < slot.getWidth()/18; ++x) {
				for(int y = 0; y < slot.getHeight()/18; ++y) {
					int index = x + y * (slot.getWidth() / 18);
					int lo = 0xB8000000;
					int bg = 0x4C000000;
					//this will cause a slightly discolored bottom border on vanilla backgrounds but it's necessary for color support, it shouldn't be *too* visible unless you're looking for it
					int hi = 0xB8FFFFFF;
					if (slot.isBigSlot()) {
						ScreenDrawing.drawBeveledPanel(matrices, (x * 18) + left - 4, (y * 18) + top - 4, 26, 26,
								lo, bg, hi);
						if (slot.getFocusedSlot() == index) {
							int sx = (x * 18) + left - 4;
							int sy = (y * 18) + top - 4;
							ScreenDrawing.coloredRect(matrices, sx,          sy,          26,     1,      0xFF_FFFFA0);
							ScreenDrawing.coloredRect(matrices, sx,          sy + 1,      1,      26 - 1, 0xFF_FFFFA0);
							ScreenDrawing.coloredRect(matrices, sx + 26 - 1, sy + 1,      1,      26 - 1, 0xFF_FFFFA0);
							ScreenDrawing.coloredRect(matrices, sx + 1,      sy + 26 - 1, 26 - 1, 1,      0xFF_FFFFA0);
						}
					} else {
						ScreenDrawing.drawBeveledPanel(matrices, (x * 18) + left, (y * 18) + top, 16+2, 16+2,
								lo, bg, hi);
						if (slot.getFocusedSlot() == index) {
							int sx = (x * 18) + left;
							int sy = (y * 18) + top;
							ScreenDrawing.coloredRect(matrices, sx,          sy,          18,     1,      0xFF_FFFFA0);
							ScreenDrawing.coloredRect(matrices, sx,          sy + 1,      1,      18 - 1, 0xFF_FFFFA0);
							ScreenDrawing.coloredRect(matrices, sx + 18 - 1, sy + 1,      1,      18 - 1, 0xFF_FFFFA0);
							ScreenDrawing.coloredRect(matrices, sx + 1,      sy + 18 - 1, 18 - 1, 1,      0xFF_FFFFA0);
						}
					}
				}
			}
		}
	};
	private static final VisualLogger LOGGER = new VisualLogger(WMovableSlot.class);
	private final List<MovableSlot> peers = new ArrayList<>();
	@Nullable
	@Environment(EnvType.CLIENT)
	private BackgroundPainter backgroundPainter = null;
	@Nullable
	private Icon icon = null;
	private Inventory inventory;
	int startIndex = 0;
	int slotsWide = 1;
	int slotsHigh = 1;
	private boolean big = false;
	private boolean insertingAllowed = true;
	private boolean takingAllowed = true;
	private int focusedSlot = -1;
	private int hoveredSlot = -1;
	private Predicate<ItemStack> filter = MovableSlot.DEFAULT_ITEM_FILTER;
	private final Set<ChangeListener> listeners = new HashSet<>();

	public WMovableSlot(Inventory inventory, int startIndex, int slotsWide, int slotsHigh, boolean big) {
		this();
		this.inventory = inventory;
		this.startIndex = startIndex;
		this.slotsWide = slotsWide;
		this.slotsHigh = slotsHigh;
		this.big = big;
		//this.ltr = ltr;
	}

	private WMovableSlot() {
		hoveredProperty().addListener((property, from, to) -> {
			assert to != null;
			if (!to) hoveredSlot = -1;
		});
	}

	public static WMovableSlot of(Inventory inventory, int index) {
		WMovableSlot w = new WMovableSlot();
		w.inventory = inventory;
		w.startIndex = index;

		return w;
	}

	public static WMovableSlot of(Inventory inventory, int startIndex, int slotsWide, int slotsHigh) {
		WMovableSlot w = new WMovableSlot();
		w.inventory = inventory;
		w.startIndex = startIndex;
		w.slotsWide = slotsWide;
		w.slotsHigh = slotsHigh;

		return w;
	}

	public static WMovableSlot outputOf(Inventory inventory, int index) {
		WMovableSlot w = new WMovableSlot();
		w.inventory = inventory;
		w.startIndex = index;
		w.big = true;

		return w;
	}
	
	public static WMovableSlot ofPlayerStorage(Inventory inventory) {
		WMovableSlot w = new WMovableSlot() {
			@Override
			protected Text getNarrationName() {
				return inventory instanceof PlayerInventory inv ? inv.getDisplayName() : NarrationMessages.Vanilla.INVENTORY;
			}
		};
		w.inventory = inventory;
		w.startIndex = 9;
		w.slotsWide = 9;
		w.slotsHigh = 3;
		//w.ltr = false;

		return w;
	}

	@Override
	public int getWidth() {
		return slotsWide * 18;
	}

	@Override
	public int getHeight() {
		return slotsHigh * 18;
	}

	@Override
	public boolean canFocus() {
		return true;
	}

	public boolean isBigSlot() {
		return big;
	}
	
	@Nullable
	public Icon getIcon() {
		return this.icon;
	}

	public WMovableSlot setIcon(@Nullable Icon icon) {
		this.icon = icon;

		if (icon != null && (slotsWide * slotsHigh) > 1) {
			LOGGER.warn("Setting icon {} for item slot {} with more than 1 slot ({})", icon, this, slotsWide * slotsHigh);
		}

		return this;
	}

	public boolean isModifiable() {
		return takingAllowed || insertingAllowed;
	}

	public WMovableSlot setModifiable(boolean modifiable) {
		this.insertingAllowed = modifiable;
		this.takingAllowed = modifiable;
		for (MovableSlot peer : peers) {
			peer.setInsertingAllowed(modifiable);
			peer.setTakingAllowed(modifiable);
		}
		return this;
	}

	public boolean isInsertingAllowed() {
		return insertingAllowed;
	}

	public WMovableSlot setInsertingAllowed(boolean insertingAllowed) {
		this.insertingAllowed = insertingAllowed;
		for (MovableSlot peer : peers) {
			peer.setInsertingAllowed(insertingAllowed);
		}
		return this;
	}
	
	public boolean isTakingAllowed() {
		return takingAllowed;
	}

	public WMovableSlot setTakingAllowed(boolean takingAllowed) {
		this.takingAllowed = takingAllowed;
		for (MovableSlot peer : peers) {
			peer.setTakingAllowed(takingAllowed);
		}
		return this;
	}

	public int getFocusedSlot() {
		return focusedSlot;
	}

	@Override
	public void validate(GuiDescription host) {
		super.validate(host);
		peers.clear();
		int index = startIndex;

		for (int y = 0; y < slotsHigh; y++) {
			for (int x = 0; x < slotsWide; x++) {
				// The Slot object is offset +1 because it's the inner area of the slot.
				MovableSlot slot = createSlotPeer(inventory, index, this.getAbsoluteX() + (x * 18) + 1, this.getAbsoluteY() + (y * 18) + 1);
				slot.setInsertingAllowed(insertingAllowed);
				slot.setTakingAllowed(takingAllowed);
				slot.setFilter(filter);
				slot.setVisible(((WInventoryListPanel<?>) this.parent.getParent()).isVisible(slot.x, slot.y));
				for (ChangeListener listener : listeners) {
					slot.addChangeListener(this, listener);
				}
				peers.add(slot);
				((ScreenHandlerAccessor) host).callAddSlot(slot);
				index++;
			}
		}
	}

	@Environment(EnvType.CLIENT)
	@Override
	public void onKeyPressed(int ch, int key, int modifiers) {
		if (isActivationKey(ch) && host instanceof ScreenHandler && focusedSlot >= 0) {
			ScreenHandler handler = (ScreenHandler) host;
			MinecraftClient client = MinecraftClient.getInstance();

			MovableSlot peer = peers.get(focusedSlot);
			client.interactionManager.clickSlot(handler.syncId, peer.id, 0, SlotActionType.PICKUP, client.player);
		}
	}

	protected MovableSlot createSlotPeer(Inventory inventory, int index, int x, int y) {
		return new MovableSlot(inventory, index, x, y);
	}

	@Nullable
	@Environment(EnvType.CLIENT)
	public BackgroundPainter getBackgroundPainter() {
		return backgroundPainter;
	}

	@Environment(EnvType.CLIENT)
	public void setBackgroundPainter(@Nullable BackgroundPainter painter) {
		this.backgroundPainter = painter;
	}

	public Predicate<ItemStack> getFilter() {
		return filter;
	}

	public WMovableSlot setFilter(Predicate<ItemStack> filter) {
		this.filter = filter;
		for (MovableSlot peer : peers) {
			peer.setFilter(filter);
		}
		return this;
	}

	@Environment(EnvType.CLIENT)
	@Override
	public void paint(MatrixStack matrices, int x, int y, int mouseX, int mouseY) {
		if (backgroundPainter != null) {
			backgroundPainter.paintBackground(matrices, x, y, this);
		}

		if (icon != null) {
			icon.paint(matrices, x + 1, y + 1, 16);
		}
	}

	@Nullable
	@Override
	public WWidget cycleFocus(boolean lookForwards) {
		if (focusedSlot < 0) {
			focusedSlot = lookForwards ? 0 : (slotsWide * slotsHigh - 1);
			return this;
		}

		if (lookForwards) {
			focusedSlot++;
			if (focusedSlot >= slotsWide * slotsHigh) {
				focusedSlot = -1;
				return null;
			} else {
				return this;
			}
		} else {
			focusedSlot--;
			return focusedSlot >= 0 ? this : null;
		}
	}
 
	public void addChangeListener(ChangeListener listener) {
		Objects.requireNonNull(listener, "listener");
		listeners.add(listener);

		for (MovableSlot peer : peers) {
			peer.addChangeListener(this, listener);
		}
	}

	@Override
	public void onShown() {
		for (MovableSlot peer : peers) {
			peer.setVisible(true);
		}
	}

	@Override
	public InputResult onMouseMove(int x, int y) {
		int slotX = x / 18;
		int slotY = y / 18;
		hoveredSlot = slotX + slotY * slotsWide;
		return InputResult.PROCESSED;
	}

	@Override
	public void onHidden() {
		super.onHidden();

		for (MovableSlot peer : peers) {
			peer.setVisible(false);
		}
	}

	@Environment(EnvType.CLIENT)
	@Override
	public void addPainters() {
		backgroundPainter = SLOT;
	}

	@Environment(EnvType.CLIENT)
	@Override
	public void addNarrations(NarrationMessageBuilder builder) {
		List<Text> parts = new ArrayList<>();
		Text name = getNarrationName();
		if (name != null) parts.add(name);

		if (focusedSlot >= 0) {
			parts.add(new TranslatableText(NarrationMessages.ITEM_SLOT_TITLE_KEY, focusedSlot + 1, slotsWide * slotsHigh));
		} else if (hoveredSlot >= 0) {
			parts.add(new TranslatableText(NarrationMessages.ITEM_SLOT_TITLE_KEY, hoveredSlot + 1, slotsWide * slotsHigh));
		}

		builder.put(NarrationPart.TITLE, parts.toArray(new Text[0]));
	}

	@Nullable
	protected Text getNarrationName() {
		return null;
	}

	@FunctionalInterface
	public interface ChangeListener {
		void onStackChanged(WMovableSlot slot, Inventory inventory, int index, ItemStack stack);
	}

	public List<MovableSlot> getPeers() {
		return peers;
	}
}
