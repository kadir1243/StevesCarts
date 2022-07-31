package vswe.stevescarts.item;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class CartItem extends Item {
	public static final Text PRESS_SHIFT = Text.translatable("tooltip.stevescarts.press_shift").formatted(Formatting.GRAY);

	public CartItem(Settings settings) {
		super(settings);
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
