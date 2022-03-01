package vswe.stevescarts.modules.attachment;

import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import vswe.stevescarts.entity.ModularMinecartEntity;
import vswe.stevescarts.modules.Configurable;
import vswe.stevescarts.modules.MinecartModule;
import vswe.stevescarts.modules.MinecartModuleType;
import vswe.stevescarts.screen.ModularCartHandler;
import vswe.stevescarts.screen.widget.WLightThreshold;

public class TorchPlacerModule extends MinecartModule implements Configurable {
	private final SimpleInventory torchInventory;

	public TorchPlacerModule(ModularMinecartEntity minecart, MinecartModuleType<?> type) {
		super(minecart, type);
		this.torchInventory = new SimpleInventory(3);
	}

	@Override
	public NbtCompound writeNbt(NbtCompound nbt) {
		nbt.put("Inventory", this.torchInventory.toNbtList());
		return super.writeNbt(nbt);
	}

	@Override
	public void readNbt(NbtCompound nbt) {
		this.torchInventory.readNbtList(nbt.getList("Inventory", NbtElement.COMPOUND_TYPE));
		super.readNbt(nbt);
	}

	@Override
	public void configure(WPlainPanel panel, ModularCartHandler handler, PlayerEntity player) {
		WLabel label = new WLabel(this.getType().getTranslationText());
		panel.add(label, 0, 0);
		WItemSlot slots = WItemSlot.of(this.torchInventory, 0, 3, 1);
		slots.setFilter(stack -> stack.isOf(Items.TORCH));
		panel.add(slots, 0, 15);
		WLightThreshold threshold = new WLightThreshold(() -> this.minecart.world.getLightLevel(this.minecart.getBlockPos()));
		panel.add(threshold, 0, 35, 46, 9);
		panel.setSize(60, 45);
	}

	public boolean hasTorch(int index) {
		return this.torchInventory.getStack(index).isOf(Items.TORCH);
	}
}
