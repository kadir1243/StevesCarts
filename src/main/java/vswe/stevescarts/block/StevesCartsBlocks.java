package vswe.stevescarts.block;

import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;

import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.item.StevesCartsItemGroups;

public class StevesCartsBlocks {
	public static final UpgradeBlock BATTERIES_UPGRADE = registerBlockAndItem("batteries_upgrade", new UpgradeBlock(FabricBlockSettings.of(Material.METAL).strength(5.0F, 6.0F)));
	public static final UpgradeBlock POWER_CRYSTAL_UPGRADE = registerBlockAndItem("power_crystal_upgrade", new UpgradeBlock(FabricBlockSettings.of(Material.METAL).strength(5.0F, 6.0F)));
	public static final UpgradeBlock MODULE_KNOWLEDGE_UPGRADE = registerBlockAndItem("module_knowledge_upgrade", new UpgradeBlock(FabricBlockSettings.of(Material.METAL).strength(5.0F, 6.0F)));
	public static final UpgradeBlock INDUSTRIAL_ESPIONAGE_UPGRADE = registerBlockAndItem("industrial_espionage_upgrade", new UpgradeBlock(FabricBlockSettings.of(Material.METAL).strength(5.0F, 6.0F)));
	public static final UpgradeBlock EXPERIENCED_ASSEMBLER_UPGRADE = registerBlockAndItem("experienced_assembler_upgrade", new UpgradeBlock(FabricBlockSettings.of(Material.METAL).strength(5.0F, 6.0F)));
	public static final UpgradeBlock NEW_ERA_UPGRADE = registerBlockAndItem("new_era_upgrade", new UpgradeBlock(FabricBlockSettings.of(Material.METAL).strength(5.0F, 6.0F)));
	public static final UpgradeBlock CO2_FRIENDLY_UPGRADE = registerBlockAndItem("co2_friendly_upgrade", new UpgradeBlock(FabricBlockSettings.of(Material.METAL).strength(5.0F, 6.0F)));
	public static final UpgradeBlock GENERIC_ENGINE_UPGRADE = registerBlockAndItem("generic_engine_upgrade", new UpgradeBlock(FabricBlockSettings.of(Material.METAL).strength(5.0F, 6.0F)));
	public static final UpgradeBlock MODULE_INPUT_UPGRADE = registerBlockAndItem("module_input_upgrade", new UpgradeBlock(FabricBlockSettings.of(Material.METAL).strength(5.0F, 6.0F)));
	public static final UpgradeBlock PRODUCTION_INPUT_UPGRADE = registerBlockAndItem("production_input_upgrade", new UpgradeBlock(FabricBlockSettings.of(Material.METAL).strength(5.0F, 6.0F)));
	public static final UpgradeBlock CART_DEPLOYER_UPGRADE = registerBlockAndItem("cart_deployer_upgrade", new UpgradeBlock(FabricBlockSettings.of(Material.METAL).strength(5.0F, 6.0F)));
	public static final UpgradeBlock CART_MODIFIER_UPGRADE = registerBlockAndItem("cart_modifier_upgrade", new UpgradeBlock(FabricBlockSettings.of(Material.METAL).strength(5.0F, 6.0F)));
	public static final UpgradeBlock CART_CRANE_UPGRADE = registerBlockAndItem("cart_crane_upgrade", new UpgradeBlock(FabricBlockSettings.of(Material.METAL).strength(5.0F, 6.0F)));
	public static final UpgradeBlock REDSTONE_CONTROL_UPGRADE = registerBlockAndItem("redstone_control_upgrade", new UpgradeBlock(FabricBlockSettings.of(Material.METAL).strength(5.0F, 6.0F)));
	public static final UpgradeBlock CREATIVE_MODE_UPGRADE = registerBlockAndItem("creative_mode_upgrade", new UpgradeBlock(FabricBlockSettings.of(Material.METAL).strength(5.0F, 6.0F)));
	public static final UpgradeBlock QUICK_DEMOLISHER_UPGRADE = registerBlockAndItem("quick_demolisher_upgrade", new UpgradeBlock(FabricBlockSettings.of(Material.METAL).strength(5.0F, 6.0F)));
	public static final UpgradeBlock ENTROPY_UPGRADE = registerBlockAndItem("entropy_upgrade", new UpgradeBlock(FabricBlockSettings.of(Material.METAL).strength(5.0F, 6.0F)));
	public static final UpgradeBlock MANAGER_BRIDGE_UPGRADE = registerBlockAndItem("manager_bridge_upgrade", new UpgradeBlock(FabricBlockSettings.of(Material.METAL).strength(5.0F, 6.0F)));
	public static final UpgradeBlock THERMAL_ENGINE_UPGRADE = registerBlockAndItem("thermal_engine_upgrade", new UpgradeBlock(FabricBlockSettings.of(Material.METAL).strength(5.0F, 6.0F)));
	public static final UpgradeBlock SOLAR_PANEL_UPGRADE = registerBlockAndItem("solar_panel_upgrade", new UpgradeBlock(FabricBlockSettings.of(Material.METAL).strength(5.0F, 6.0F)));

	public static final Block CART_ASSEMBLER = registerBlockAndItem("cart_assembler", new CartAssemblerBlock(FabricBlockSettings.of(Material.STONE).strength(1.5F)));
	public static final Block CARGO_MANAGER = registerBlockAndItem("cargo_manager", new Block(FabricBlockSettings.of(Material.STONE).strength(1.5F)));
	public static final Block FLUID_MANAGER = registerBlockAndItem("fluid_manager", new Block(FabricBlockSettings.of(Material.STONE).strength(1.5F)));
	public static final Block EXTERNAL_DISTRIBUTOR = registerBlockAndItem("external_distributor", new Block(FabricBlockSettings.of(Material.STONE).strength(1.5F)));
	public static final Block MODULE_TOGGLER = registerBlockAndItem("module_toggler", new Block(FabricBlockSettings.of(Material.STONE).strength(1.5F)));
	public static final Block DETECTOR_MANAGER = registerBlockAndItem("detector_manager", new Block(FabricBlockSettings.of(Material.STONE).strength(1.5F)));
	public static final Block DETECTOR_UNIT = registerBlockAndItem("detector_unit", new Block(FabricBlockSettings.of(Material.STONE).strength(1.5F)));
	public static final Block DETECTOR_STATION = registerBlockAndItem("detector_station", new Block(FabricBlockSettings.of(Material.STONE).strength(1.5F)));
	public static final Block DETECTOR_JUNCTION = registerBlockAndItem("detector_junction", new Block(FabricBlockSettings.of(Material.STONE).strength(1.5F)));
	public static final Block DETECTOR_REDSTONE_UNIT = registerBlockAndItem("detector_redstone_unit", new Block(FabricBlockSettings.of(Material.STONE).strength(1.5F)));
	public static final Block JUNCTION_RAIL = registerBlockAndItem("junction_rail", new JunctionRailBlock(FabricBlockSettings.of(Material.METAL).noCollision().strength(1.5F)));
	public static final Block ADVANCED_DETECTOR_RAIL = registerBlockAndItem("advanced_detector_rail", new AdvancedDetectorRailBlock(FabricBlockSettings.of(Material.METAL).noCollision().strength(1.5F)));
	public static final Block REINFORCED_METAL_BLOCK = registerBlockAndItem("reinforced_metal_block", new Block(FabricBlockSettings.of(Material.METAL).requiresTool().strength(1.5F)));
	public static final Block GALGADORIAN_BLOCK = registerBlockAndItem("galgadorian_block", new Block(FabricBlockSettings.of(Material.METAL).requiresTool().strength(1.5F)));
	public static final Block ENHANCED_GALGADORIAN_BLOCK = registerBlockAndItem("enhanced_galgadorian_block", new Block(FabricBlockSettings.of(Material.METAL).requiresTool().strength(1.5F)));

	public static void init() {
	}

	public static <T extends Block> T registerBlockAndItem(String name, T block) {
		Registry.register(Registry.BLOCK, StevesCarts.id(name), block);
		Registry.register(Registry.ITEM, StevesCarts.id(name), new BlockItem(block, new Item.Settings().group(StevesCartsItemGroups.BLOCKS)));
		return block;
	}
}
