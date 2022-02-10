package vswe.stevescarts.item.component;

import net.minecraft.item.Item;
import vswe.stevescarts.item.StevesCartsItemGroups;

public class CartComponentItem extends Item {
	public CartComponentItem(Settings settings) {
		super(settings.group(StevesCartsItemGroups.COMPONENTS));
	}
}
