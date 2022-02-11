package vswe.stevescarts.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;
import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.item.StevesCartsItemGroups;

public class StevesCartsBlocks {
	public static final Block CART_ASSEMBLER = registerBlockAndItem("cart_assembler", new Block(FabricBlockSettings.of(Material.STONE).strength(1.5F)));
	public static final Block CARGO_MANAGER = registerBlockAndItem("cargo_manager", new Block(FabricBlockSettings.of(Material.STONE).strength(1.5F)));
	public static final Block LIQUID_MANAGER = registerBlockAndItem("liquid_manager", new Block(FabricBlockSettings.of(Material.STONE).strength(1.5F)));
	public static final Block EXTERNAL_DISTRIBUTOR = registerBlockAndItem("external_distributor", new Block(FabricBlockSettings.of(Material.STONE).strength(1.5F)));
	public static final Block MODULE_TOGGLER = registerBlockAndItem("module_toggler", new Block(FabricBlockSettings.of(Material.STONE).strength(1.5F)));
	public static final Block DETECTOR_MANAGER = registerBlockAndItem("detector_manager", new Block(FabricBlockSettings.of(Material.STONE).strength(1.5F)));
	public static final Block DETECTOR_UNIT = registerBlockAndItem("detector_unit", new Block(FabricBlockSettings.of(Material.STONE).strength(1.5F)));
	public static final Block DETECTOR_STATION = registerBlockAndItem("detector_station", new Block(FabricBlockSettings.of(Material.STONE).strength(1.5F)));
	public static final Block DETECTOR_JUNCTION = registerBlockAndItem("detector_junction", new Block(FabricBlockSettings.of(Material.STONE).strength(1.5F)));
	public static final Block DETECTOR_REDSTONE_UNIT = registerBlockAndItem("detector_redstone_unit", new Block(FabricBlockSettings.of(Material.STONE).strength(1.5F)));
	public static final Block JUNCTION_RAIL = registerBlockAndItem("junction_rail", new Block(FabricBlockSettings.of(Material.STONE).strength(1.5F)));
	public static final Block ADVANCED_DETECTOR_RAIL = registerBlockAndItem("advanced_detector_rail", new Block(FabricBlockSettings.of(Material.STONE).strength(1.5F)));
	public static final Block REINFORCED_METAL_BLOCK = registerBlockAndItem("reinforced_metal_block", new Block(FabricBlockSettings.of(Material.STONE).requiresTool().strength(1.5F)));
	public static final Block GALGADORIAN_BLOCK = registerBlockAndItem("galgadorian_block", new Block(FabricBlockSettings.of(Material.STONE).requiresTool().strength(1.5F)));
	public static final Block ENHANCED_GALGADORIAN_BLOCK = registerBlockAndItem("enhanced_galgadorian_block", new Block(FabricBlockSettings.of(Material.STONE).requiresTool().strength(1.5F)));

	public static void init() {
	}

	public static Block registerBlockAndItem(String name, Block block) {
		Registry.register(Registry.BLOCK, StevesCarts.id(name), block);
		Registry.register(Registry.ITEM, StevesCarts.id(name), new BlockItem(block, new Item.Settings().group(StevesCartsItemGroups.BLOCKS)));
		return block;
	}
}
