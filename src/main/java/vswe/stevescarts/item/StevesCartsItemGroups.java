package vswe.stevescarts.item;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.block.StevesCartsBlocks;

public class StevesCartsItemGroups {
	public static final ItemGroup COMPONENTS = FabricItemGroupBuilder.create(StevesCarts.id("components_group")).icon(() -> new ItemStack(StevesCartsItems.REINFORCED_WHEELS)).build();
	public static final ItemGroup BLOCKS = FabricItemGroupBuilder.create(StevesCarts.id("blocks_group")).icon(() -> new ItemStack(StevesCartsBlocks.CART_ASSEMBLER)).build();
	public static final ItemGroup MODULES = FabricItemGroupBuilder.create(StevesCarts.id("modules_group")).icon(() -> new ItemStack(StevesCartsBlocks.CART_ASSEMBLER)).build();
}
