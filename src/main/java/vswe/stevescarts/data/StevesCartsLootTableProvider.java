package vswe.stevescarts.data;

import vswe.stevescarts.StevesCarts;

import net.minecraft.util.registry.Registry;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;

public class StevesCartsLootTableProvider extends FabricBlockLootTableProvider {
	public StevesCartsLootTableProvider(FabricDataGenerator dataGenerator) {
		super(dataGenerator);
	}

	@Override
	protected void generateBlockLootTables() {
		Registry.BLOCK
				.getIds()
				.stream()
				.filter(id -> id.getNamespace().equals(StevesCarts.MODID))
				.map(Registry.BLOCK::get)
				.forEach(this::addDrop);
	}
}
