package vswe.stevescarts.data;

import vswe.stevescarts.item.CartComponentItem;

import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.util.registry.Registry;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;

@Environment(EnvType.CLIENT)
public class StevesCartsModelProvider extends FabricModelProvider {
	public StevesCartsModelProvider(FabricDataGenerator dataGenerator) {
		super(dataGenerator);
	}

	@Override
	public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

	}

	@Override
	public void generateItemModels(ItemModelGenerator itemModelGenerator) {
		Registry.ITEM.stream().filter(CartComponentItem.class::isInstance).forEach(item -> {
			itemModelGenerator.register(item, Models.GENERATED);
		});
	}
}
