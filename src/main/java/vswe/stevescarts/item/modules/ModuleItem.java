package vswe.stevescarts.item.modules;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import vswe.stevescarts.modules.MinecartModuleType;
import vswe.stevescarts.modules.ModuleCategory;

import java.util.List;

public class ModuleItem extends Item {
	private final MinecartModuleType<?> type;

	public ModuleItem(Settings settings, MinecartModuleType<?> type) {
		super(settings);
		this.type = type;
	}

	public boolean isIn(ModuleCategory category) {
		return this.type.getCategory() == category;
	}

	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		tooltip.addAll(this.type.getTooltip());
	}
}
