package vswe.stevescarts.item;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import vswe.stevescarts.StevesCarts;

public class StevesCartsItemGroups {
	public static final ItemGroup COMPONENTS = FabricItemGroupBuilder.create(StevesCarts.id("components_group")).icon(StevesCartsItems.IRON_WHEELS::getDefaultStack).build();
}
