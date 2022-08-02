package vswe.stevescarts.item;

import java.util.Collection;
import java.util.List;

import org.jetbrains.annotations.Nullable;
import vswe.stevescarts.entity.CartEntity;
import vswe.stevescarts.entity.StevesCartsEntities;
import vswe.stevescarts.module.CartModule;
import vswe.stevescarts.module.ModuleStorage;

import net.minecraft.block.AbstractRailBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.RailShape;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.tag.BlockTags;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class CartItem extends Item {
	public static final Text PRESS_SHIFT = Text.translatable("tooltip.stevescarts.press_shift").formatted(Formatting.GRAY);

	public CartItem(Settings settings) {
		super(settings);
	}

	public static ItemStack createStack(Collection<CartModule> modules) {
		ItemStack stack = StevesCartsItems.CART.getDefaultStack();
		ModuleStorage.write(stack, modules);
		return stack;
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
			RailShape railShape = blockState.getBlock() instanceof AbstractRailBlock ? blockState.get(((AbstractRailBlock) blockState.getBlock()).getShapeProperty()) : RailShape.NORTH_SOUTH;
			double d = 0.0;
			if (railShape.isAscending()) {
				d = 0.5;
			}
			CartEntity entity = new CartEntity(StevesCartsEntities.CART, world);
			entity.setPos((double) blockPos.getX() + 0.5, (double) blockPos.getY() + 0.0625 + d, (double) blockPos.getZ() + 0.5);
			entity.createModuleData(ModuleStorage.read(itemStack));
			if (itemStack.hasCustomName()) {
				entity.setCustomName(itemStack.getName());
			}
			world.spawnEntity(entity);
			world.emitGameEvent(context.getPlayer(), GameEvent.ENTITY_PLACE, blockPos);
		}
		itemStack.decrement(1);
		return ActionResult.success(world.isClient);
	}

	@Environment(EnvType.CLIENT)
	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		if (!(Screen.hasShiftDown() || context.isAdvanced())) {
			tooltip.add(PRESS_SHIFT);
			return;
		}
		// TODO
	}
}
