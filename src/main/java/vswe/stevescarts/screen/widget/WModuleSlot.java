package vswe.stevescarts.screen.widget;

import io.github.cottonmc.cotton.gui.ValidatedSlot;
import io.github.cottonmc.cotton.gui.client.BackgroundPainter;
import io.github.cottonmc.cotton.gui.client.ScreenDrawing;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import io.github.cottonmc.cotton.gui.widget.data.Texture;
import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.item.modules.ModuleItem;
import vswe.stevescarts.modules.MinecartModuleType;
import vswe.stevescarts.modules.ModuleCategory;
import vswe.stevescarts.modules.StevesCartsModuleTypes;
import vswe.stevescarts.modules.hull.HullModule;
import vswe.stevescarts.screen.slot.ModuleSlot;

import java.util.List;
import java.util.stream.Collectors;

public class WModuleSlot extends WItemSlot {
	@Environment(EnvType.CLIENT)
	private static final Texture OPEN_TEXTURE = new Texture(StevesCarts.id("textures/gui/module_slot.png"), 0.0F, 0.0F, 0.28125F, 0.5625F);
	@Environment(EnvType.CLIENT)
	private static final BackgroundPainter PAINTER = (matrices, left, top, panel) -> {
		WModuleSlot slot = (WModuleSlot) panel;
		ScreenDrawing.texturedRect(matrices, left, top - 10, 79, 11, WAssembleButton.ENABLED, 0xFFFFFFFF);
		ScreenDrawing.drawString(matrices, slot.category.getTranslation().asOrderedText(), HorizontalAlignment.CENTER, left + 3, top - 10, 73, slot.category.getTextColor());
		for (int y = 0; y < slot.slotsHigh; ++y) {
			for (int x = 0; x < slot.slotsWide; ++x) {
				ScreenDrawing.texturedRect(matrices, left + x * 18, top + y * 18, 18, 18, OPEN_TEXTURE, 0xFFFFFFFF);
				ScreenDrawing.drawBeveledPanel(matrices, left + x * 18, top + y * 18, 18, 18, 0xB8000000, 0x4C000000, 0xB8FFFFFF);
				ModuleSlot mSlot = slot.peers.get(slot.startIndex + x + y * slot.slotsHigh);
				float progress = mSlot.getAnimationProgress(0);
				Identifier image = OPEN_TEXTURE.image();
				float u1 = 0.28125F;
				float u2 = 0.53125F;
				float v1 = ((1 - progress) * 8) / 32.0F;
				float v2 = 0.25F;
				int height = (int) ((1 - progress) * 8);
				int width = 16;
				int xPos = left + x * 18 + 1;
				int yPos = top + y * 18 + 1;
				ScreenDrawing.texturedRect(matrices, xPos, yPos, width, height, image, u1, v1, u2, v2, 0xFFFFFFFF);
				ScreenDrawing.texturedRect(matrices, xPos, yPos + 8 + (8 - height), width, height, image, u1, v2, u2, v2 + v1, 0xFFFFFFFF);
			}
		}
	};
	private final Int2ObjectMap<ModuleSlot> peers;
	private final int startIndex;
	private final int slotsWide;
	private final int slotsHigh;
	private final ModuleCategory category;

	public WModuleSlot(Inventory inventory, int startIndex, int slotsWide, int slotsHigh, ModuleCategory category) {
		super(inventory, startIndex, slotsWide, slotsHigh, false);
		this.startIndex = startIndex;
		this.slotsWide = slotsWide;
		this.slotsHigh = slotsHigh;
		this.category = category;
		this.peers = new Int2ObjectLinkedOpenHashMap<>(slotsWide * slotsHigh);
	}

	@Override
	protected ValidatedSlot createSlotPeer(Inventory inventory, int index, int x, int y) {
		ModuleSlot mSlot = new ModuleSlot(inventory, index, x, y);
		mSlot.invalidate();
		peers.put(index, mSlot);
		return mSlot;
	}

	public void toggleSlots(MinecartModuleType<? extends HullModule> type) {
		int max;
		if (type != null) {
			max = this.category.getMax(type) + this.startIndex;
			for (int i = this.startIndex; i < max; ++i) {
				peers.get(i).validate();
			}
		} else {
			max = this.startIndex;
		}
		for (int i = max; i < this.category.getMaxMax() + this.startIndex; ++i) {
			ModuleSlot peer = peers.get(i);
			if (peer != null) {
				peer.invalidate();
			}
		}
	}

	@Environment(EnvType.CLIENT)
	@Override
	public void tick() {
		super.tick();
		for (ModuleSlot slot : peers.values()) {
			slot.step();
		}
	}

	@Override
	public void addPainters() {
		this.setBackgroundPainter(PAINTER);
	}

	public boolean hasModule() {
		for (ModuleSlot slot : peers.values()) {
			if (MinecartModuleType.isModule(slot.getStack())) {
				return true;
			}
		}
		return false;
	}

	public List<MinecartModuleType<?>> getModules() {
		return this.peers.values().stream()
				.map(ModuleSlot::getStack)
				.map(ItemStack::getItem)
				.filter(MinecartModuleType::isModule)
				.map(ModuleItem.class::cast)
				.map(ModuleItem::getType)
				.collect(Collectors.toList());
	}
}
