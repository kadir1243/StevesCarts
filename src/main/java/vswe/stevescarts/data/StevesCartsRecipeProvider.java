package vswe.stevescarts.data;

import java.util.function.Consumer;

import vswe.stevescarts.item.StevesCartsItems;
import vswe.stevescarts.module.ModuleType;

import net.minecraft.data.server.RecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tag.ItemTags;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;

import static vswe.stevescarts.item.StevesCartsItems.CHEST_LOCK;
import static vswe.stevescarts.item.StevesCartsItems.CHEST_PANE;
import static vswe.stevescarts.item.StevesCartsItems.GALGADORIAN_WHEELS;
import static vswe.stevescarts.item.StevesCartsItems.HUGE_CHEST_PANE;
import static vswe.stevescarts.item.StevesCartsItems.HUGE_DYNAMIC_PANE;
import static vswe.stevescarts.item.StevesCartsItems.HUGE_IRON_PANE;
import static vswe.stevescarts.item.StevesCartsItems.IRON_WHEELS;
import static vswe.stevescarts.item.StevesCartsItems.LARGE_CHEST_PANE;
import static vswe.stevescarts.item.StevesCartsItems.LARGE_DYNAMIC_PANE;
import static vswe.stevescarts.item.StevesCartsItems.LARGE_IRON_PANE;
import static vswe.stevescarts.item.StevesCartsItems.REINFORCED_WHEELS;
import static vswe.stevescarts.item.StevesCartsItems.WOODEN_WHEELS;
import static vswe.stevescarts.module.StevesCartsModules.EXTRACTING_CHESTS;
import static vswe.stevescarts.module.StevesCartsModules.FRONT_CHEST;
import static vswe.stevescarts.module.StevesCartsModules.GALGADORIAN_HULL;
import static vswe.stevescarts.module.StevesCartsModules.MECHANICAL_PIG;
import static vswe.stevescarts.module.StevesCartsModules.REINFORCED_HULL;
import static vswe.stevescarts.module.StevesCartsModules.SIDE_CHESTS;
import static vswe.stevescarts.module.StevesCartsModules.STANDARD_HULL;
import static vswe.stevescarts.module.StevesCartsModules.TOP_CHEST;
import static vswe.stevescarts.module.StevesCartsModules.WOODEN_HULL;

public class StevesCartsRecipeProvider extends FabricRecipeProvider {
	public StevesCartsRecipeProvider(FabricDataGenerator dataGenerator) {
		super(dataGenerator);
	}

	@Override
	protected void generateRecipes(Consumer<RecipeJsonProvider> exporter) {
		createHull(exporter, WOODEN_HULL, ItemTags.PLANKS, WOODEN_WHEELS);
		createHull(exporter, STANDARD_HULL, Tags.IRON_INGOTS, IRON_WHEELS);
		createHull(exporter, REINFORCED_HULL, StevesCartsItems.REINFORCED_METAL, REINFORCED_WHEELS);
		createHull(exporter, MECHANICAL_PIG, Items.PORKCHOP, IRON_WHEELS);
		createHull(exporter, GALGADORIAN_HULL, StevesCartsItems.GALGADORIAN_METAL, GALGADORIAN_WHEELS);
		ShapedRecipeJsonBuilder.create(FRONT_CHEST).pattern("X#X").pattern("XCX").pattern("###").input('X', CHEST_PANE).input('#', LARGE_CHEST_PANE).input('C', CHEST_LOCK).criterion("has_base_item", RecipeProvider.conditionsFromItem(Items.CHEST)).offerTo(exporter);
		ShapedRecipeJsonBuilder.create(TOP_CHEST).pattern("###").pattern("XCX").pattern("###").input('X', CHEST_PANE).input('#', LARGE_CHEST_PANE).input('C', CHEST_LOCK).criterion("has_base_item", RecipeProvider.conditionsFromItem(Items.CHEST)).offerTo(exporter);
		ShapedRecipeJsonBuilder.create(SIDE_CHESTS).pattern("#X#").pattern("LCL").pattern("#X#").input('X', CHEST_PANE).input('L', LARGE_CHEST_PANE).input('#', HUGE_CHEST_PANE).input('C', CHEST_LOCK).criterion("has_base_item", RecipeProvider.conditionsFromItem(Items.CHEST)).offerTo(exporter);
		ShapedRecipeJsonBuilder.create(EXTRACTING_CHESTS).pattern("###").pattern("LCL").pattern("XDX").input('X', HUGE_DYNAMIC_PANE).input('D', LARGE_DYNAMIC_PANE).input('L', LARGE_IRON_PANE).input('#', HUGE_IRON_PANE).input('C', CHEST_LOCK).criterion("has_base_item", RecipeProvider.conditionsFromItem(Items.CHEST)).offerTo(exporter);
	}

	public void createHull(Consumer<RecipeJsonProvider> exporter, ModuleType<?> output, TagKey<Item> body, Item wheels) {
		ShapedRecipeJsonBuilder.create(output)
				.input('#', body)
				.input('X', wheels)
				.pattern("# #")
				.pattern("###")
				.pattern("X X")
				.criterion("has_base_item", RecipeProvider.conditionsFromTag(body))
				.offerTo(exporter);
	}

	public void createHull(Consumer<RecipeJsonProvider> exporter, ModuleType<?> output, Item body, Item wheels) {
		ShapedRecipeJsonBuilder.create(output)
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

		private static TagKey<Item> c(String name) {
			return TagKey.of(Registry.ITEM_KEY, new Identifier("c", name));
		}
	}
}
