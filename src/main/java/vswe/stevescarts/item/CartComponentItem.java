package vswe.stevescarts.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;

public class CartComponentItem extends Item {
	public CartComponentItem(Settings settings) {
		super(settings);

		ItemGroupEvents.MODIFY_ENTRIES_ALL.register((group, entries) -> {
			if (group == StevesCartsItems.COMPONENTS) {
				entries.add(this);
			}
		});
	}
}
