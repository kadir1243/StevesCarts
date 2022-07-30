package vswe.stevescarts.item;

import net.minecraft.item.Item;

public class CartComponentItem extends Item {
	public CartComponentItem(Settings settings) {
		super(settings.group(StevesCartsItems.COMPONENTS));
	}
}
