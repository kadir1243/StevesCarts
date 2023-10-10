package vswe.stevescarts.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import vswe.stevescarts.item.StevesCartsItems;
import vswe.stevescarts.module.ModuleType;

import java.util.function.Consumer;

import static vswe.stevescarts.item.StevesCartsItems.*;
import static vswe.stevescarts.module.StevesCartsModules.*;

public class StevesCartsRecipeProvider extends FabricRecipeProvider {
	public StevesCartsRecipeProvider(FabricDataOutput dataGenerator) {
		super(dataGenerator);
	}

	@Override
	public void generate(Consumer<RecipeJsonProvider> exporter) {
		createHull(exporter, WOODEN_HULL, ItemTags.PLANKS, WOODEN_WHEELS);
		createHull(exporter, STANDARD_HULL, Tags.IRON_INGOTS, IRON_WHEELS);
		createHull(exporter, REINFORCED_HULL, StevesCartsItems.REINFORCED_METAL, REINFORCED_WHEELS);
		createHull(exporter, MECHANICAL_PIG, Items.PORKCHOP, IRON_WHEELS);
		createHull(exporter, GALGADORIAN_HULL, StevesCartsItems.GALGADORIAN_METAL, GALGADORIAN_WHEELS);
		ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, FRONT_CHEST).pattern("X#X").pattern("XCX").pattern("###").input('X', CHEST_PANE).input('#', LARGE_CHEST_PANE).input('C', CHEST_LOCK).criterion("has_base_item", RecipeProvider.conditionsFromItem(Items.CHEST)).offerTo(exporter);
		ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, TOP_CHEST).pattern("###").pattern("XCX").pattern("###").input('X', CHEST_PANE).input('#', LARGE_CHEST_PANE).input('C', CHEST_LOCK).criterion("has_base_item", RecipeProvider.conditionsFromItem(Items.CHEST)).offerTo(exporter);
		ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, SIDE_CHESTS).pattern("#X#").pattern("LCL").pattern("#X#").input('X', CHEST_PANE).input('L', LARGE_CHEST_PANE).input('#', HUGE_CHEST_PANE).input('C', CHEST_LOCK).criterion("has_base_item", RecipeProvider.conditionsFromItem(Items.CHEST)).offerTo(exporter);
		ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, EXTRACTING_CHESTS).pattern("###").pattern("LCL").pattern("XDX").input('X', HUGE_DYNAMIC_PANE).input('D', LARGE_DYNAMIC_PANE).input('L', LARGE_IRON_PANE).input('#', HUGE_IRON_PANE).input('C', CHEST_LOCK).criterion("has_base_item", RecipeProvider.conditionsFromItem(Items.CHEST)).offerTo(exporter);
		ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, TOP_TANK).pattern("###").pattern("XCX").pattern("###").input('X', TANK_PANE).input('#', HUGE_TANK_PANE).input('C', TANK_VALVE).criterion("has_base_item", RecipeProvider.conditionsFromItem(Items.GLASS)).offerTo(exporter);
		ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, FRONT_TANK).pattern("X#X").pattern("XCX").pattern("###").input('X', TANK_PANE).input('#', LARGE_TANK_PANE).input('C', TANK_VALVE).criterion("has_base_item", RecipeProvider.conditionsFromItem(Items.GLASS)).offerTo(exporter);
		ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, SIDE_TANKS).pattern("#X#").pattern("LCL").pattern("#X#").input('X', TANK_PANE).input('L', LARGE_TANK_PANE).input('#', HUGE_TANK_PANE).input('C', TANK_VALVE).criterion("has_base_item", RecipeProvider.conditionsFromItem(Items.GLASS)).offerTo(exporter);
		ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, OPEN_TANK).pattern("X X").pattern("XCX").pattern("###").input('X', TANK_PANE).input('#', HUGE_TANK_PANE).input('C', TANK_VALVE).criterion("has_base_item", RecipeProvider.conditionsFromItem(Items.GLASS)).offerTo(exporter);
		ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ADVANCED_TANK).pattern("XXX").pattern("XCX").pattern("XXX").input('X', HUGE_TANK_PANE).input('C', TANK_VALVE).criterion("has_base_item", RecipeProvider.conditionsFromItem(Items.GLASS)).offerTo(exporter);
		ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, SEAT).pattern(" #").pattern(" #").pattern("X#").input('X', ItemTags.WOODEN_SLABS).input('#', ItemTags.PLANKS).criterion("has_base_item", RecipeProvider.conditionsFromTag(ItemTags.WOODEN_SLABS)).offerTo(exporter);
		ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, TINY_COAL_ENGINE).pattern("#X#").pattern(" P ").input('#', Tags.IRON_INGOTS).input('P', Items.PISTON).input('X', Items.FURNACE).criterion("has_base_item", RecipeProvider.conditionsFromItem(Items.FURNACE)).offerTo(exporter);
		ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, COAL_ENGINE).pattern("###").pattern("#X#").pattern(" P ").input('#', Tags.IRON_INGOTS).input('P', Items.PISTON).input('X', Items.FURNACE).criterion("has_base_item", RecipeProvider.conditionsFromItem(Items.FURNACE)).offerTo(exporter);
		ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, SOLAR_ENGINE).pattern("#I#").pattern("IXI").pattern(" P ").input('I', Tags.IRON_INGOTS).input('#', SOLAR_PANEL).input('P', Items.PISTON).input('X', SIMPLE_PCB).criterion("has_base_item", RecipeProvider.conditionsFromItem(SOLAR_PANEL)).offerTo(exporter);
		ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ADVANCED_SOLAR_ENGINE).pattern("I#I").pattern("#X#").pattern("P#P").input('I', Tags.IRON_INGOTS).input('#', ADVANCED_SOLAR_PANEL).input('P', Items.PISTON).input('X', ADVANCED_PCB).criterion("has_base_item", RecipeProvider.conditionsFromItem(ADVANCED_SOLAR_PANEL)).offerTo(exporter);
		ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, THERMAL_ENGINE).pattern("OOO").pattern("#X#").pattern("P P").input('O', Items.NETHER_BRICK).input('#', Items.OBSIDIAN).input('X', Items.FURNACE).input('P', Items.PISTON).criterion("has_base_item", RecipeProvider.conditionsFromItem(Items.OBSIDIAN)).offerTo(exporter);
		ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ADVANCED_THERMAL_ENGINE).pattern("OOO").pattern("#X#").pattern("P P").input('O', Items.NETHER_BRICK).input('#', REINFORCED_METAL).input('X', THERMAL_ENGINE).input('P', Items.PISTON).criterion("has_base_item", RecipeProvider.conditionsFromItem(Items.OBSIDIAN)).offerTo(exporter);
		ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, BRAKE).pattern("  R").pattern("#S ").pattern("X# ").input('R', Tags.RED_DYES).input('S', REFINED_HANDLE).input('#', Tags.IRON_INGOTS).input('X', Tags.REDSTONE_DUSTS).criterion("has_base_item", RecipeProvider.conditionsFromItem(REFINED_HANDLE)).offerTo(exporter);
		ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, INVISIBILITY_CORE).pattern(" X ").pattern("XEX").pattern(" # ").input('E', Items.ENDER_EYE).input('#', Items.GOLDEN_CARROT).input('X', GLASS_O_MAGIC).criterion("has_base_item", RecipeProvider.conditionsFromItem(REFINED_HANDLE)).offerTo(exporter);
		ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, FIREWORK_DISPLAY).pattern("#D#").pattern("CFC").pattern("XSX").input('X', SIMPLE_PCB).input('F', FUSE).input('#', ItemTags.WOODEN_FENCES).input('D', Items.DISPENSER).input('S', Items.FLINT_AND_STEEL).input('C', Items.CRAFTING_TABLE).criterion("has_base_item", RecipeProvider.conditionsFromItem(Items.FIREWORK_ROCKET)).offerTo(exporter);
		ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, TORCH_PLACER).pattern("X X").pattern("# #").pattern("###").input('X', TRI_TORCH).input('#', Tags.IRON_INGOTS).criterion("has_base_item", RecipeProvider.conditionsFromItem(Items.TORCH)).offerTo(exporter);
		ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, RAILER).pattern("###").pattern("XRX").pattern("###").input('X', Tags.IRON_INGOTS).input('#', Tags.COBBLESTONE).input('R', Items.RAIL).criterion("has_base_item", RecipeProvider.conditionsFromItem(Items.RAIL)).offerTo(exporter);
		ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, LARGE_RAILER).pattern("###").pattern("XRX").pattern("###").input('#', Tags.IRON_INGOTS).input('X', RAILER).input('R', Items.RAIL).criterion("has_base_item", RecipeProvider.conditionsFromItem(RAILER)).offerTo(exporter);
		ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, BRIDGE_BUILDER).pattern(" R ").pattern("X#X").pattern(" P ").input('R', Tags.REDSTONE_DUSTS).input('X', Items.BRICKS).input('P', Items.PISTON).input('#', SIMPLE_PCB).criterion("has_base_item", RecipeProvider.conditionsFromItem(RAILER)).offerTo(exporter);
		ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, HYDRATOR).pattern("I#I").pattern(" X ").input('I', Tags.IRON_INGOTS).input('#', Items.GLASS_BOTTLE).input('X', ItemTags.WOODEN_FENCES).criterion("has_base_item", RecipeProvider.conditionsFromItem(Items.GLASS_BOTTLE)).offerTo(exporter);
	}

	public void createHull(Consumer<RecipeJsonProvider> exporter, ModuleType<?> output, TagKey<Item> body, Item wheels) {
		ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, output)
				.input('#', body)
				.input('X', wheels)
				.pattern("# #")
				.pattern("###")
				.pattern("X X")
				.criterion("has_base_item", RecipeProvider.conditionsFromTag(body))
				.offerTo(exporter);
	}

	public void createHull(Consumer<RecipeJsonProvider> exporter, ModuleType<?> output, Item body, Item wheels) {
		ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, output)
				.input('#', body)
				.input('X', wheels)
				.pattern("# #")
				.pattern("###")
				.pattern("X X")
				.criterion("has_base_item", RecipeProvider.conditionsFromItem(body))
				.offerTo(exporter);
	}

	public static class Tags {
		public static final TagKey<Item> IRON_INGOTS = c("iron_ingots");
		public static final TagKey<Item> REDSTONE_DUSTS = c("redstone_dusts");
		public static final TagKey<Item> RED_DYES = c("red_dyes");
		public static final TagKey<Item> COBBLESTONE = c("cobblestone");

		private static TagKey<Item> c(String name) {
			return TagKey.of(RegistryKeys.ITEM, new Identifier("c", name));
		}
	}
}
