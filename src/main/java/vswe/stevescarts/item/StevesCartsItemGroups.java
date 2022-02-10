package vswe.stevescarts.item;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import vswe.stevescarts.StevesCarts;

public class StevesCartsItemGroups {
	public static final ItemGroup COMPONENTS = FabricItemGroupBuilder.create(StevesCarts.id("components_group")).icon(() -> new ItemStack(StevesCartsItems.IRON_WHEELS)).build();
}
