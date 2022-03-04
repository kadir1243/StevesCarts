package vswe.stevescarts.modules.attachment;

import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.tag.ItemTags;

import vswe.stevescarts.entity.ModularMinecartEntity;
import vswe.stevescarts.modules.Configurable;
import vswe.stevescarts.modules.MinecartModule;
import vswe.stevescarts.modules.MinecartModuleType;
import vswe.stevescarts.screen.ModularCartHandler;

public class RailerModule extends MinecartModule implements Configurable {
	private final SimpleInventory railInventory = new SimpleInventory(1);

	public RailerModule(ModularMinecartEntity minecart, MinecartModuleType<?> type) {
		super(minecart, type);
	}

	@Override
	public NbtCompound writeNbt(NbtCompound nbt) {
		nbt.put("Inventory", this.railInventory.toNbtList());
		return super.writeNbt(nbt);
	}

	@Override
	public void readNbt(NbtCompound nbt) {
		this.railInventory.readNbtList(nbt.getList("Inventory", NbtElement.COMPOUND_TYPE));
		super.readNbt(nbt);
	}

	@Override
	public void configure(WPlainPanel panel, ModularCartHandler handler, PlayerEntity player) {
		WLabel label = new WLabel(this.getType().getTranslationText());
		panel.add(label, 0, 0);
		WItemSlot slots = WItemSlot.of(this.railInventory, 0, 1, 1);
		slots.setFilter(stack -> stack.isIn(ItemTags.RAILS));
		panel.add(slots, 0, 15);
	}

	public ItemStack getFirst() {
		return this.railInventory.getStack(0);
	}
}
