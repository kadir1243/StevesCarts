package vswe.stevescarts.data;

import java.util.function.Consumer;

import vswe.stevescarts.item.StevesCartsItems;
import vswe.stevescarts.module.ModuleType;
import vswe.stevescarts.module.StevesCartsModules;

import net.minecraft.data.server.RecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.tag.ItemTags;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;

public class StevesCartsRecipeProvider extends FabricRecipeProvider {
	public StevesCartsRecipeProvider(FabricDataGenerator dataGenerator) {
		super(dataGenerator);
	}

	@Override
	protected void generateRecipes(Consumer<RecipeJsonProvider> exporter) {
		createHull(exporter, StevesCartsModules.WOODEN_HULL, ItemTags.PLANKS, StevesCartsItems.WOODEN_WHEELS);
		createHull(exporter, StevesCartsModules.STANDARD_HULL, Tags.IRON_INGOTS, StevesCartsItems.IRON_WHEELS);
		createHull(exporter, StevesCartsModules.REINFORCED_HULL, StevesCartsItems.REINFORCED_METAL, StevesCartsItems.REINFORCED_WHEELS);
		createHull(exporter, StevesCartsModules.MECHANICAL_PIG, Items.PORKCHOP, StevesCartsItems.IRON_WHEELS);
		createHull(exporter, StevesCartsModules.GALGADORIAN_HULL, StevesCartsItems.GALGADORIAN_METAL, StevesCartsItems.GALGADORIAN_WHEELS);
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
