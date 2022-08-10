package vswe.stevescarts.module.attachment;

import io.github.cottonmc.cotton.gui.networking.NetworkSide;
import io.github.cottonmc.cotton.gui.networking.ScreenNetworking;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.entity.CartEntity;
import vswe.stevescarts.module.CartModule;
import vswe.stevescarts.module.Configurable;
import vswe.stevescarts.module.ModuleType;
import vswe.stevescarts.screen.CartHandler;
import vswe.stevescarts.screen.widget.WLightThreshold;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TorchBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;

public class TorchPlacerModule extends CartModule implements Configurable {
	private static final BlockState TORCH_STATE = Blocks.TORCH.getDefaultState();
	private static final ItemVariant TORCH_ITEM = ItemVariant.of(Items.TORCH);
	private final SimpleInventory torchInventory;
	private int threshold = 7;

	public TorchPlacerModule(CartEntity minecart, ModuleType<?> type) {
		super(minecart, type);
		this.torchInventory = new SimpleInventory(3);
	}

	@Override
	public void writeToNbt(NbtCompound nbt) {
		nbt.put("Inventory", this.torchInventory.toNbtList());
		nbt.putInt("Threshold", this.threshold);
		super.writeToNbt(nbt);
	}

	@Override
	public void readFromNbt(NbtCompound nbt) {
		this.torchInventory.readNbtList(nbt.getList("Inventory", NbtElement.COMPOUND_TYPE));
		this.threshold = nbt.getInt("Threshold");
		super.readFromNbt(nbt);
	}

	@Override
	public void tick() {
		super.tick();
		if (!this.getEntity().world.isClient) {
			int x = (int) Math.floor(this.getEntity().getX());
			int y = (int) Math.floor(this.getEntity().getY());
			int z = (int) Math.floor(this.getEntity().getZ());
			BlockPos.Mutable railPos = new BlockPos.Mutable(x, y, z);

			if (this.getEntity().world.getBlockState(railPos.down()).isIn(BlockTags.RAILS)) {
				railPos.move(Direction.DOWN);
			}

			InventoryStorage storage = InventoryStorage.of(this.torchInventory, null);

			for (int i = 0; i < 4; i++) {
				BlockPos pos = railPos.offset(Direction.fromHorizontal(i));
				if (this.getEntity().world.getLightLevel(pos) > this.threshold
						|| !this.getEntity().world.getBlockState(pos).isAir()) {
					continue;
				}
				if (TorchBlock.sideCoversSmallSquare(this.getEntity().world, pos.down(), Direction.UP)) {
					try (Transaction transaction = Transaction.openOuter()) {
						transaction.addCloseCallback((transaction1, result) -> {
							if (result == Transaction.Result.COMMITTED) {
								this.getEntity().world.setBlockState(pos, TORCH_STATE, Block.NOTIFY_ALL);
							}
						});
						long extract = storage.extract(TORCH_ITEM, 1, transaction);
						if (extract > 0) {
							transaction.commit();
						}
					}
				}
			}
		}
	}

	@Override
	public void configure(WPlainPanel panel, CartHandler handler, PlayerEntity player) {
		WLabel label = new WLabel(this.getType().getTranslationText());
		panel.add(label, 0, 0);
		WItemSlot slots = WItemSlot.of(this.torchInventory, 0, 3, 1);
		slots.setFilter(stack -> stack.isOf(Items.TORCH));
		panel.add(slots, 0, 15);
		WLightThreshold threshold = new WLightThreshold(() -> this.getEntity().world.getLightLevel(this.getEntity().getBlockPos()), this.threshold, (i) -> {
			this.threshold = i;
			ScreenNetworking.of(handler, NetworkSide.CLIENT).send(PACKET_THRESHOLD_UPDATE, buf -> buf.writeByte((byte) i));
		});
		panel.add(threshold, 0, 35, 46, 9);
		panel.setSize(60, 45);
		ScreenNetworking.of(handler, NetworkSide.SERVER).receive(PACKET_THRESHOLD_UPDATE, buf -> this.threshold = buf.readByte());
	}

	public static final Identifier PACKET_THRESHOLD_UPDATE = StevesCarts.id("threshold_update");

	public boolean hasTorch(int index) {
		return this.torchInventory.getStack(index).isOf(Items.TORCH);
	}
}
