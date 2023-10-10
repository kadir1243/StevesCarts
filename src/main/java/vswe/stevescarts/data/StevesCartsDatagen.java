package vswe.stevescarts.data;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class StevesCartsDatagen implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator dataGenerator) {
		FabricDataGenerator.Pack pack = dataGenerator.createPack();
		pack.addProvider(StevesCartsModelProvider::new);
		pack.addProvider(StevesCartsRecipeProvider::new);
		pack.addProvider(StevesCartsLootTableProvider::new);
	}
}
