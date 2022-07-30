package vswe.stevescarts.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;

import vswe.stevescarts.StevesCarts;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;

public class StevesCartsItems {
	public static final ItemGroup COMPONENTS = FabricItemGroupBuilder.build(StevesCarts.id("components"), () -> StevesCartsItems.WOODEN_WHEELS.getDefaultStack());
	// Components
	public static final CartComponentItem WOODEN_WHEELS = registerSimpleComponent("wooden_wheels");
	public static final CartComponentItem IRON_WHEELS = registerSimpleComponent("iron_wheels");
	public static final CartComponentItem RED_PIGMENT = registerSimpleComponent("red_pigment");
	public static final CartComponentItem GREEN_PIGMENT = registerSimpleComponent("green_pigment");
	public static final CartComponentItem BLUE_PIGMENT = registerSimpleComponent("blue_pigment");
	public static final CartComponentItem GLASS_O_MAGIC = registerSimpleComponent("glass_o_magic");
	public static final CartComponentItem DYNAMITE = registerSimpleComponent("dynamite");
	public static final CartComponentItem SIMPLE_PCB = registerSimpleComponent("simple_pcb");
	public static final CartComponentItem GRAPHICAL_INTERFACE = registerSimpleComponent("graphical_interface");
	public static final CartComponentItem RAW_HANDLE = registerSimpleComponent("raw_handle");
	public static final CartComponentItem REFINED_HANDLE = registerSimpleComponent("refined_handle");
	public static final CartComponentItem SPEED_HANDLE = registerSimpleComponent("speed_handle");
	public static final CartComponentItem WHEEL = registerSimpleComponent("wheel");
	public static final CartComponentItem SAW_BLADE = registerSimpleComponent("saw_blade");
	public static final CartComponentItem ADVANCED_PCB = registerSimpleComponent("advanced_pcb");
	public static final CartComponentItem WOOD_CUTTING_CORE = registerSimpleComponent("wood_cutting_core");
	public static final CartComponentItem RAW_HARDENER = registerSimpleComponent("raw_hardener");
	public static final CartComponentItem REFINED_HARDENER = registerSimpleComponent("refined_hardener");
	public static final CartComponentItem HARDENED_MESH = registerSimpleComponent("hardened_mesh");
	public static final CartComponentItem STABILIZED_METAL = registerSimpleComponent("stabilized_metal");
	public static final CartComponentItem REINFORCED_METAL = registerSimpleComponent("reinforced_metal");
	public static final CartComponentItem REINFORCED_WHEELS = registerSimpleComponent("reinforced_wheels");
	public static final CartComponentItem PIPE = registerSimpleComponent("pipe");
	public static final CartComponentItem SHOOTING_STATION = registerSimpleComponent("shooting_station");
	public static final CartComponentItem ENTITY_SCANNER = registerSimpleComponent("entity_scanner");
	public static final CartComponentItem ENTITY_ANALYZER = registerSimpleComponent("entity_analyzer");
	public static final CartComponentItem EMPTY_DISK = registerSimpleComponent("empty_disk");
	public static final CartComponentItem TRI_TORCH = registerSimpleComponent("tri_torch");
	public static final CartComponentItem CHEST_PANE = registerSimpleComponent("chest_pane");
	public static final CartComponentItem LARGE_CHEST_PANE = registerSimpleComponent("large_chest_pane");
	public static final CartComponentItem HUGE_CHEST_PANE = registerSimpleComponent("huge_chest_pane");
	public static final CartComponentItem CHEST_LOCK = registerSimpleComponent("chest_lock");
	public static final CartComponentItem IRON_PANE = registerSimpleComponent("iron_pane");
	public static final CartComponentItem LARGE_IRON_PANE = registerSimpleComponent("large_iron_pane");
	public static final CartComponentItem HUGE_IRON_PANE = registerSimpleComponent("huge_iron_pane");
	public static final CartComponentItem DYNAMIC_PANE = registerSimpleComponent("dynamic_pane");
	public static final CartComponentItem LARGE_DYNAMIC_PANE = registerSimpleComponent("large_dynamic_pane");
	public static final CartComponentItem HUGE_DYNAMIC_PANE = registerSimpleComponent("huge_dynamic_pane");
	public static final CartComponentItem CLEANING_FAN = registerSimpleComponent("cleaning_fan");
	public static final CartComponentItem CLEANING_CORE = registerSimpleComponent("cleaning_core");
	public static final CartComponentItem CLEANING_TUBE = registerSimpleComponent("cleaning_tube");
	public static final CartComponentItem FUSE = registerSimpleComponent("fuse");
	public static final CartComponentItem SOLAR_PANEL = registerSimpleComponent("solar_panel");
	public static final CartComponentItem EYE_OF_GALGADOR = registerSimpleComponent("eye_of_galgador");
	public static final CartComponentItem LUMP_OF_GALGADOR = registerSimpleComponent("lump_of_galgador");
	public static final CartComponentItem GALGADORIAN_METAL = registerSimpleComponent("galgadorian_metal");
	public static final CartComponentItem LARGE_LUMP_OF_GALGADOR = registerSimpleComponent("large_lump_of_galgador");
	public static final CartComponentItem ENHANCED_GALGADORIAN_METAL = registerSimpleComponent("enhanced_galgadorian_metal");
	public static final CartComponentItem STOLEN_PRESENT = registerSimpleComponent("stolen_present");
	public static final CartComponentItem GREEN_WRAPPING_PAPER = registerSimpleComponent("green_wrapping_paper");
	public static final CartComponentItem RED_WRAPPING_PAPER = registerSimpleComponent("red_wrapping_paper");
	public static final CartComponentItem WARM_HAT = registerSimpleComponent("warm_hat");
	public static final CartComponentItem RED_GIFT_RIBBON = registerSimpleComponent("red_gift_ribbon");
	public static final CartComponentItem YELLOW_GIFT_RIBBON = registerSimpleComponent("yellow_gift_ribbon");
	public static final CartComponentItem SOCK = registerSimpleComponent("sock");
	public static final CartComponentItem STUFFED_SOCK = registerSimpleComponent("stuffed_sock");
	public static final CartComponentItem ADVANCED_SOLAR_PANEL = registerSimpleComponent("advanced_solar_panel");
	public static final CartComponentItem BLANK_UPGRADE = registerSimpleComponent("blank_upgrade");
	public static final CartComponentItem TANK_VALVE = registerSimpleComponent("tank_valve");
	public static final CartComponentItem TANK_PANE = registerSimpleComponent("tank_pane");
	public static final CartComponentItem LARGE_TANK_PANE = registerSimpleComponent("large_tank_pane");
	public static final CartComponentItem HUGE_TANK_PANE = registerSimpleComponent("huge_tank_pane");
	public static final CartComponentItem FLUID_CLEANING_CORE = registerSimpleComponent("fluid_cleaning_core");
	public static final CartComponentItem FLUID_CLEANING_TUBE = registerSimpleComponent("fluid_cleaning_tube");
	public static final CartComponentItem EXPLOSIVE_EASTER_EGG = registerSimpleComponent("explosive_easter_egg");
	public static final CartComponentItem BURNING_EASTER_EGG = registerSimpleComponent("burning_easter_egg");
	public static final CartComponentItem GLISTERING_EASTER_EGG = registerSimpleComponent("glistering_easter_egg");
	public static final CartComponentItem CHOCOLATE_EASTER_EGG = registerSimpleComponent("chocolate_easter_egg");
	public static final CartComponentItem PAINTED_EASTER_EGG = registerSimpleComponent("painted_easter_egg");
	public static final CartComponentItem BASKET = registerSimpleComponent("basket");
	public static final CartComponentItem OAK_LOG = registerSimpleComponent("oak_log");
	public static final CartComponentItem OAK_TWIG = registerSimpleComponent("oak_twig");
	public static final CartComponentItem SPRUCE_LOG = registerSimpleComponent("spruce_log");
	public static final CartComponentItem SPRUCE_TWIG = registerSimpleComponent("spruce_twig");
	public static final CartComponentItem BIRCH_LOG = registerSimpleComponent("birch_log");
	public static final CartComponentItem BIRCH_TWIG = registerSimpleComponent("birch_twig");
	public static final CartComponentItem JUNGLE_LOG = registerSimpleComponent("jungle_log");
	public static final CartComponentItem JUNGLE_TWIG = registerSimpleComponent("jungle_twig");
	public static final CartComponentItem HARDENED_SAW_BLADE = registerSimpleComponent("hardened_saw_blade");
	public static final CartComponentItem GALGADORIAN_SAW_BLADE = registerSimpleComponent("galgadorian_saw_blade");
	public static final CartComponentItem GALGADORIAN_WHEELS = registerSimpleComponent("galgadorian_wheels");
	public static final CartComponentItem IRON_BLADE = registerSimpleComponent("iron_blade");
	public static final CartComponentItem BLADE_ARM = registerSimpleComponent("blade_arm");

	private static <T extends Item> T register(String name, T item) {
		return Registry.register(Registry.ITEM, StevesCarts.id(name), item);
	}

	private static CartComponentItem registerSimpleComponent(String name) {
		return register(name, new CartComponentItem(new Item.Settings()));
	}

	public static void init() {
	}
}
