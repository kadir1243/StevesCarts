package vswe.stevescarts.modules.attachment;

import io.github.cottonmc.cotton.gui.networking.NetworkSide;
import io.github.cottonmc.cotton.gui.networking.ScreenNetworking;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import vswe.stevescarts.entity.ModularMinecartEntity;
import vswe.stevescarts.modules.Configurable;
import vswe.stevescarts.modules.MinecartModule;
import vswe.stevescarts.modules.MinecartModuleType;
import vswe.stevescarts.screen.ModularCartHandler;
import vswe.stevescarts.screen.StevesCartsScreenHandlers;
import vswe.stevescarts.screen.widget.WLightThreshold;

@SuppressWarnings("UnstableApiUsage")
public class TorchPlacerModule extends MinecartModule implements Configurable {
	private static final BlockState TORCH_STATE = Blocks.TORCH.getDefaultState();
	private static final ItemVariant TORCH_ITEM = ItemVariant.of(Items.TORCH);
	private final SimpleInventory torchInventory;
	private int threshold = 7;

	public TorchPlacerModule(ModularMinecartEntity minecart, MinecartModuleType<?> type) {
		super(minecart, type);
		this.torchInventory = new SimpleInventory(3);
	}

	@Override
	public NbtCompound writeNbt(NbtCompound nbt) {
		nbt.put("Inventory", this.torchInventory.toNbtList());
		nbt.putInt("Threshold", this.threshold);
		return super.writeNbt(nbt);
	}

	@Override
	public void readNbt(NbtCompound nbt) {
		this.torchInventory.readNbtList(nbt.getList("Inventory", NbtElement.COMPOUND_TYPE));
		this.threshold = nbt.getInt("Threshold");
		super.readNbt(nbt);
	}

	@Override
	public void tick() {
		super.tick();
		if (!this.minecart.world.isClient) {
			int x = (int) Math.floor(this.minecart.getX());
			int y = (int) Math.floor(this.minecart.getY());
			int z = (int) Math.floor(this.minecart.getZ());
			BlockPos.Mutable railPos = new BlockPos.Mutable(x, y, z);

			if (this.minecart.world.getBlockState(railPos.down()).isIn(BlockTags.RAILS)) {
				railPos.move(Direction.DOWN);
			}

			InventoryStorage storage = InventoryStorage.of(this.torchInventory, null);

			for (int i = 0; i < 4; i++) {
				BlockPos pos = railPos.offset(Direction.fromHorizontal(i));
				if (this.minecart.world.getLightLevel(pos) > this.threshold
						|| !this.minecart.world.getBlockState(pos).isAir()) {
					continue;
				}
				if (TorchBlock.sideCoversSmallSquare(this.minecart.world, pos.down(), Direction.UP)) {
					try (Transaction transaction = Transaction.openOuter()) {
						transaction.addCloseCallback((transaction1, result) -> {
							if (result == Transaction.Result.COMMITTED) {
								this.minecart.world.setBlockState(pos, TORCH_STATE, Block.NOTIFY_ALL);
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
	public void configure(WPlainPanel panel, ModularCartHandler handler, PlayerEntity player) {
		WLabel label = new WLabel(this.getType().getTranslationText());
		panel.add(label, 0, 0);
		WItemSlot slots = WItemSlot.of(this.torchInventory, 0, 3, 1);
		slots.setFilter(stack -> stack.isOf(Items.TORCH));
		panel.add(slots, 0, 15);
		WLightThreshold threshold = new WLightThreshold(() -> this.minecart.world.getLightLevel(this.minecart.getBlockPos()), this.threshold, (i) -> {
			this.threshold = i;
			ScreenNetworking.of(handler, NetworkSide.CLIENT).send(StevesCartsScreenHandlers.PACKET_THRESHOLD_UPDATE, buf -> buf.writeByte((byte) i));
		});
		panel.add(threshold, 0, 35, 46, 9);
		panel.setSize(60, 45);
		ScreenNetworking.of(handler, NetworkSide.SERVER).receive(StevesCartsScreenHandlers.PACKET_THRESHOLD_UPDATE, buf -> this.threshold = buf.readByte());
	}

	public boolean hasTorch(int index) {
		return this.torchInventory.getStack(index).isOf(Items.TORCH);
	}
}
