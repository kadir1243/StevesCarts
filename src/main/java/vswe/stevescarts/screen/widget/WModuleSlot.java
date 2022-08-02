package vswe.stevescarts.screen.widget;

import java.util.List;
import java.util.stream.Collectors;

import io.github.cottonmc.cotton.gui.ValidatedSlot;
import io.github.cottonmc.cotton.gui.client.BackgroundPainter;
import io.github.cottonmc.cotton.gui.client.ScreenDrawing;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import io.github.cottonmc.cotton.gui.widget.data.Texture;
import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.module.ModuleGroup;
import vswe.stevescarts.module.ModuleItem;
import vswe.stevescarts.module.ModuleType;
import vswe.stevescarts.module.hull.HullModule;

import net.minecraft.client.MinecraftClient;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class WModuleSlot extends WItemSlot {
	@Environment(EnvType.CLIENT)
	private static final Texture OPEN_TEXTURE = new Texture(StevesCarts.id("textures/gui/module_slot.png"), 0.0F, 0.0F, 0.28125F, 0.5625F);
	@Environment(EnvType.CLIENT)
	public static final Texture TEXT_BG = new Texture(StevesCarts.id("textures/gui/assemble_button.png"), 0.0F, 0.0F, 1.0F, 1.0F / 3.0F);
	@Environment(EnvType.CLIENT)
	private static final BackgroundPainter PAINTER = (matrices, left, top, panel) -> {
		WModuleSlot slot = (WModuleSlot) panel;
		ScreenDrawing.texturedRect(matrices, left, top - 10, 79, 11, TEXT_BG, 0xFFFFFFFF);
		matrices.push();
		matrices.scale(1F, 0.8F, 1F);
		matrices.translate(0F, (top - 10) / 0.8F - (top - 10) + 1, 0F);
		ScreenDrawing.drawString(matrices, slot.group.getTranslation().asOrderedText(), HorizontalAlignment.LEFT, left + 5, top - 8, 0, slot.group.getTextColor());
		matrices.pop();

		for (int y = 0; y < slot.slotsHigh; ++y) {
			for (int x = 0; x < slot.slotsWide; ++x) {
				int index = slot.startIndex + x + y * slot.slotsWide;
				ScreenDrawing.texturedRect(matrices, left + x * 18, top + y * 18, 18, 18, OPEN_TEXTURE, 0xFFFFFFFF);
				ScreenDrawing.drawBeveledPanel(matrices, left + x * 18, top + y * 18, 18, 18, 0xB8000000, 0x4C000000, 0xB8FFFFFF);
				AnimatableSlot mSlot = slot.peers.get(index);
				float progress = mSlot.getAnimationProgress(MinecraftClient.getInstance().getTickDelta());
				int xPos = left + x * 18 + 1;
				int yPos = top + y * 18 + 1;
				Identifier image = OPEN_TEXTURE.image();
				float u1 = 0.28125F;
				float u2 = 0.53125F;
				float v1 = ((progress) * 8) / 32.0F;
				float v2 = 0.25F;
				int height = (int) ((1 - progress) * 8);
				ScreenDrawing.texturedRect(matrices, xPos, yPos, 16, height, image, u1, v1, u2, v2, 0xFFFFFFFF);
				ScreenDrawing.texturedRect(matrices, xPos, yPos + 8 + (8 - height), 16, height, image, u1, v2, u2, v2 + ((1 - progress) * 8) / 32.0F, 0xFFFFFFFF);
			}
		}
	};
	private final Int2ObjectMap<AnimatableSlot> peers;
	private final int startIndex;
	private final int slotsWide;
	private final int slotsHigh;
	private final ModuleGroup group;

	public WModuleSlot(Inventory inventory, ModuleGroup group, int startIndex, int slotsWide, int slotsHigh) {
		super(inventory, startIndex, slotsWide, slotsHigh, false);
		this.group = group;
		this.startIndex = startIndex;
		this.slotsWide = slotsWide;
		this.slotsHigh = slotsHigh;
		this.peers = new Int2ObjectLinkedOpenHashMap<>(slotsWide * slotsHigh);
	}

	@Override
	protected ValidatedSlot createSlotPeer(Inventory inventory, int index, int x, int y) {
		AnimatableSlot slot = new AnimatableSlot(inventory, index, x, y);
		slot.invalidate();
		peers.put(index, slot);
		return slot;
	}

	public void toggleSlots(ModuleType<? extends HullModule> type) {
		int max;
		if (type != null) {
			max = this.group.getMax(type) + this.startIndex;
			for (int i = this.startIndex; i < max; ++i) {
				peers.get(i).validate((i - this.startIndex) * 2);
			}
		} else {
			max = this.startIndex;
		}
		for (int i = max; i < this.group.getMaxMax() + this.startIndex; ++i) {
			AnimatableSlot peer = peers.get(i);
			if (peer != null) {
				peer.invalidate();
			}
		}
	}

	@Environment(EnvType.CLIENT)
	@Override
	public void tick() {
		super.tick();
		for (AnimatableSlot slot : peers.values()) {
			slot.step();
		}
	}

	@Override
	public void addPainters() {
		this.setBackgroundPainter(PAINTER);
	}

	public boolean hasModule() {
		for (AnimatableSlot slot : peers.values()) {
			if (ModuleType.isModule(slot.getStack())) {
				return true;
			}
		}
		return false;
	}

	public List<ModuleType<?>> getModules() {
		return this.peers.values().stream()
				.map(ValidatedSlot::getStack)
				.map(ItemStack::getItem)
				.filter(ModuleType::isModule)
				.map(ModuleItem.class::cast)
				.map(ModuleItem::getType)
				.collect(Collectors.toList());
	}
}
