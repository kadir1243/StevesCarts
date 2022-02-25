package vswe.stevescarts.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import vswe.stevescarts.entity.ModularMinecartEntity;
import vswe.stevescarts.modules.MinecartModule;
import vswe.stevescarts.modules.ModuleStorage;

import java.util.Collection;
import java.util.List;

public class ModularMinecartItem extends Item {
	public static final TranslatableText PRESS_SHIFT = (TranslatableText) new TranslatableText("tooltip.stevescarts.press_shift").formatted(Formatting.GRAY);

	public ModularMinecartItem(Settings settings) {
		super(settings.maxCount(1));
	}

	public static ItemStack create(Collection<MinecartModule> modules) {
		ItemStack stack = StevesCartsItems.MODULAR_CART.getDefaultStack();
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
			ModularMinecartEntity entity = new ModularMinecartEntity(world, (double) blockPos.getX() + 0.5, (double) blockPos.getY() + 0.0625 + d, (double) blockPos.getZ() + 0.5, ModuleStorage.read(itemStack));
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

	@Environment(EnvType.CLIENT)
	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		if (!(Screen.hasShiftDown() || context.isAdvanced())) {
			tooltip.add(PRESS_SHIFT);
			return;
		}
		ModuleStorage.read(stack).forEach(module -> tooltip.add(module.getType().getTranslationText().formatted(Formatting.GRAY)));
	}
}
