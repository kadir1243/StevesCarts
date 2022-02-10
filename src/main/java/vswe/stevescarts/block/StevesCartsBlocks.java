package vswe.stevescarts.block;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;
import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.item.StevesCartsItemGroups;

public class StevesCartsBlocks {
	public static void init() {
	}

	public static Block registerBlockAndItem(String name, Block block) {
		Registry.register(Registry.BLOCK, StevesCarts.id(name), block);
		Registry.register(Registry.ITEM, StevesCarts.id(name), new BlockItem(block, new Item.Settings().group(StevesCartsItemGroups.BLOCKS)));
		return block;
	}
}
