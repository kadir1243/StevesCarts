package vswe.stevescarts.item;

import net.minecraft.block.AbstractRailBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.RailShape;
import net.minecraft.entity.Entity;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import vswe.stevescarts.entity.ModularMinecartEntity;
import vswe.stevescarts.modules.MinecartModule;
import vswe.stevescarts.modules.MinecartModuleType;
import vswe.stevescarts.modules.ModuleStorage;
import vswe.stevescarts.modules.hull.HullModule;

import java.util.Collection;
import java.util.List;

public class ModularMinecartItem extends Item {
	public ModularMinecartItem(Settings settings) {
		super(settings.maxCount(1));
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		BlockPos blockPos;
		World world = context.getWorld();
		BlockState blockState = world.getBlockState(blockPos = context.getBlockPos());
		if (!blockState.isIn(BlockTags.RAILS)) {
			return ActionResult.FAIL;
		}
		ItemStack itemStack = context.getStack();
		if (!world.isClient) {
			RailShape railShape = blockState.getBlock() instanceof AbstractRailBlock ? blockState.get(((AbstractRailBlock)blockState.getBlock()).getShapeProperty()) : RailShape.NORTH_SOUTH;
			double d = 0.0;
			if (railShape.isAscending()) {
				d = 0.5;
			}
			ModularMinecartEntity entity = new ModularMinecartEntity(world, (double)blockPos.getX() + 0.5, (double)blockPos.getY() + 0.0625 + d, (double)blockPos.getZ() + 0.5, ModuleStorage.read(itemStack));
			entity.forceUpdate();
			if (itemStack.hasCustomName()) {
				entity.setCustomName(itemStack.getName());
			}
			world.spawnEntity(entity);
			world.emitGameEvent(context.getPlayer(), GameEvent.ENTITY_PLACE, blockPos);
		}
		itemStack.decrement(1);
		return ActionResult.success(world.isClient);
	}

	public static ItemStack create(Collection<MinecartModule> modules) {
		ItemStack stack = StevesCartsItems.MODULAR_CART.getDefaultStack();
		ModuleStorage.write(stack, modules);
		return stack;
	}
}
