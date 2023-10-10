package vswe.stevescarts.module.attachment;

import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.AutomaticItemPlacementContext;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import vswe.stevescarts.entity.CartEntity;
import vswe.stevescarts.module.CartModule;
import vswe.stevescarts.module.Configurable;
import vswe.stevescarts.module.ModuleType;
import vswe.stevescarts.module.Worker;
import vswe.stevescarts.screen.CartHandler;

public class RailerModule extends CartModule implements Configurable, Worker {
	private final SimpleInventory railInventory;
	private final int height;

	public RailerModule(CartEntity minecart, ModuleType<?> type, int height) {
		super(minecart, type);
		this.railInventory = new SimpleInventory(height * 3);
		this.height = height;
	}

	@Override
	public void writeToNbt(NbtCompound nbt) {
		nbt.put("Inventory", this.railInventory.toNbtList());
		super.writeToNbt(nbt);
	}

	@Override
	public void readFromNbt(NbtCompound nbt) {
		this.railInventory.readNbtList(nbt.getList("Inventory", NbtElement.COMPOUND_TYPE));
		super.readFromNbt(nbt);
	}

	@Override
	public void work() {
		if (this.checkMovement() && !this.getEntity().world.isClient) {
			this.tryPlaceRail();
		}
	}

	private void tryPlaceRail() {
		ItemStack first = this.getFirst();
		BlockPos railPos = this.getRailPos();
		Vec3d velocityVector = this.getEntity().getVelocity().normalize();
		Direction moveDirection = Direction.fromVector((int) velocityVector.x, (int) velocityVector.y, (int) velocityVector.z);
		if (moveDirection != null) {
			BlockPos newRailPos = railPos.offset(moveDirection);
			if (this.getEntity().getWorld().getBlockState(newRailPos).isAir()) {
				AutomaticItemPlacementContext ctx = new AutomaticItemPlacementContext(this.getEntity().getWorld(), newRailPos, moveDirection, first, moveDirection);
				Item firstItem = first.getItem();
				if (firstItem instanceof BlockItem) {
					BlockState placementState = ((BlockItem) first.getItem()).getBlock().getPlacementState(ctx);
					this.getEntity().getWorld().setBlockState(newRailPos, placementState, Block.NOTIFY_ALL);
					this.getEntity().stopFor(20);
					first.decrement(1);
				}
			}
		}
	}

	@Override
	public void configure(WPlainPanel panel, CartHandler handler, PlayerEntity player) {
		WLabel label = new WLabel(this.getType().getTranslationText());
		panel.add(label, 0, 0);
		WItemSlot slots = WItemSlot.of(this.railInventory, 0, 3, this.height);
		slots.setFilter(stack -> stack.isIn(ItemTags.RAILS));
		panel.add(slots, 0, 15);
	}

	public ItemStack getFirst() {
		ItemStack stack = this.railInventory.getStack(0);
		for (int i = 1; i < this.railInventory.size() && stack.isEmpty(); i++) {
			stack = this.railInventory.getStack(i);
		}
		return stack;
	}
}
