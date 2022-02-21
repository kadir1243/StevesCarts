package vswe.stevescarts.item.modules;

import com.google.common.base.Suppliers;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import vswe.stevescarts.item.StevesCartsItemGroups;
import vswe.stevescarts.modules.MinecartModuleType;
import vswe.stevescarts.modules.ModuleCategory;

import java.util.List;
import java.util.function.Supplier;

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

	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		this.type.appendTooltip(tooltip);
	}

	public MinecartModuleType<?> getType() {
		return type;
	}

	@Override
	protected String getOrCreateTranslationKey() {
		return this.translationKey.get();
	}
}
