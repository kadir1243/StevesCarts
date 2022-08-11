package vswe.stevescarts.module.attachment;

import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import org.jetbrains.annotations.Nullable;
import vswe.stevescarts.entity.CartEntity;
import vswe.stevescarts.module.CartModule;
import vswe.stevescarts.module.Configurable;
import vswe.stevescarts.module.ModuleType;
import vswe.stevescarts.module.Worker;
import vswe.stevescarts.screen.CartHandler;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.SideShapeType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class BridgeBuilderModule extends CartModule implements Worker, Configurable {
	private final SimpleInventory inventory = new SimpleInventory(3);

	public BridgeBuilderModule(@Nullable CartEntity entity, ModuleType<?> type) {
		super(entity, type);
	}

	@Override
	public void writeToNbt(NbtCompound nbt) {
		nbt.put("Inventory", this.inventory.toNbtList());
		super.writeToNbt(nbt);
	}

	@Override
	public void readFromNbt(NbtCompound nbt) {
		this.inventory.readNbtList(nbt.getList("Inventory", NbtElement.COMPOUND_TYPE));
		super.readFromNbt(nbt);
	}

	@Override
	public void configure(WPlainPanel panel, CartHandler handler, PlayerEntity player) {
		WLabel label = new WLabel(this.getType().getTranslationText());
		panel.add(label, 0, 0);
		WItemSlot slots = WItemSlot.of(this.inventory, 0, 3, 1);
		slots.setFilter(stack -> {
			if (stack.getItem() instanceof BlockItem bi) {
				Block block = bi.getBlock();
				if (block instanceof FallingBlock) {
					return false;
				}
				return block.getDefaultState().isSideSolid(this.getEntity().world, BlockPos.ORIGIN, Direction.UP, SideShapeType.RIGID);
			}
			return false;
		});
		panel.add(slots, 0, 15);
	}

	@Override
	public int getPriority() {
		return HIGHER_PRIORITY;
	}

	@Override
	public void work() {
		if (!this.checkMovement() || this.getEntity().world.isClient) {
			return;
		}
		ItemStack first = this.getFirst();
		if (first.isEmpty()) {
			return;
		}
		BlockPos railPos = this.getRailPos();
		Vec3d velocityVector = this.getEntity().getVelocity().normalize();
		Direction moveDirection = Direction.fromVector((int) velocityVector.x, (int) velocityVector.y, (int) velocityVector.z);
		if (moveDirection == null) {
			return;
		}
		BlockPos blockPos = railPos.down().offset(moveDirection);
		BlockState state = ((BlockItem) first.getItem()).getBlock().getDefaultState();
		if (this.getEntity().world.getBlockState(blockPos).isAir()) {
			this.getEntity().world.setBlockState(blockPos, state);
			first.decrement(1);
		}
	}

	private ItemStack getFirst() {
		ItemStack stack = this.inventory.getStack(0);
		for (int i = 1; i < this.inventory.size() && stack.isEmpty(); i++) {
			stack = this.inventory.getStack(i);
		}
		return stack;
	}
}
