package vswe.stevescarts.modules.attachment;

import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import vswe.stevescarts.entity.ModularMinecartEntity;
import vswe.stevescarts.mixins.DispenserBlockAccessor;
import vswe.stevescarts.modules.Configurable;
import vswe.stevescarts.modules.MinecartModule;
import vswe.stevescarts.modules.MinecartModuleType;
import vswe.stevescarts.screen.ModularCartHandler;

public class FireworkDisplayModule extends MinecartModule implements Configurable {
	private final SimpleInventory inventory = new SimpleInventory(15);
	public static final BlockState DISPENSER_BLOCKSTATE = Blocks.DISPENSER.getDefaultState().with(DispenserBlock.FACING, Direction.UP);

	public FireworkDisplayModule(ModularMinecartEntity minecart, MinecartModuleType<?> type) {
		super(minecart, type);
	}

	@Override
	public NbtCompound writeNbt(NbtCompound nbt) {
		nbt.put("Fireworks", this.inventory.toNbtList());
		return super.writeNbt(nbt);
	}

	@Override
	public void readNbt(NbtCompound nbt) {
		this.inventory.readNbtList(nbt.getList("Fireworks", NbtElement.COMPOUND_TYPE));
		super.readNbt(nbt);
	}

	@Override
	public void onActivate() {
		int nextIndex = -1;
		for (int i = 0; i < this.inventory.size(); i++) {
			if (!this.inventory.getStack(i).isEmpty()) {
				nextIndex = i;
			}
		}

		if (nextIndex != -1) {
			ItemStack stack = this.inventory.getStack(nextIndex);
			DispenserBehavior behavior = ((DispenserBlockAccessor) Blocks.DISPENSER).invokeGetBehaviorForItem(stack);
			behavior.dispense(this.new FakeBlockPointer(), stack);
		}
	}

	@Override
	public void configure(WPlainPanel panel, ModularCartHandler handler, PlayerEntity player) {
		WLabel label = new WLabel(this.getType().getTranslationText());
		panel.add(label, 0, 0);
		WItemSlot slots = WItemSlot.of(this.inventory, 0, 5, 3);
		slots.setFilter(stack -> stack.isOf(Items.FIREWORK_ROCKET));
		panel.add(slots, 0, 10);
		panel.layout();
	}

	private class FakeBlockPointer implements BlockPointer {
		@Override
		public double getX() {
			return FireworkDisplayModule.this.getMinecart().getX();
		}

		@Override
		public double getY() {
			return FireworkDisplayModule.this.getMinecart().getY();
		}

		@Override
		public double getZ() {
			return FireworkDisplayModule.this.getMinecart().getZ();
		}

		@Override
		public BlockPos getPos() {
			return new BlockPos(this.getX(), this.getY(), this.getZ());
		}

		@Override
		public BlockState getBlockState() {
			return DISPENSER_BLOCKSTATE;
		}

		@Override
		public <T extends BlockEntity> T getBlockEntity() {
			return null;
		}

		@Override
		public ServerWorld getWorld() {
			return (ServerWorld) FireworkDisplayModule.this.getMinecart().getWorld();
		}
	}
}
