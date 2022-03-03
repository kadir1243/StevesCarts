package vswe.stevescarts.item.modules;

import com.google.common.base.Suppliers;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import vswe.stevescarts.item.StevesCartsItemGroups;
import vswe.stevescarts.modules.MinecartModuleType;
import vswe.stevescarts.modules.ModuleCategory;

public class ModuleItem extends Item {
	protected final MinecartModuleType<?> type;
	private final Supplier<String> translationKey;

	public ModuleItem(Settings settings, MinecartModuleType<?> type) {
		super(settings.group(StevesCartsItemGroups.MODULES).maxCount(1));
		this.type = type;
		this.translationKey = Suppliers.memoize(() -> this.type.getTranslationText().getKey());
	}

	public boolean isOf(ModuleCategory category) {
		return this.type.getCategory() == category;
	}

	@Environment(EnvType.CLIENT)
	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		TooltipContext newContext = context.isAdvanced() || Screen.hasShiftDown() ? TooltipContext.Default.ADVANCED : TooltipContext.Default.NORMAL;
		this.type.appendTooltip(tooltip, newContext);
	}

	public MinecartModuleType<?> getType() {
		return type;
	}

	@Override
	protected String getOrCreateTranslationKey() {
		return this.translationKey.get();
	}
}
