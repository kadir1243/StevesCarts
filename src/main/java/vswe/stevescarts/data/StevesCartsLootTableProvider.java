package vswe.stevescarts.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.registry.Registries;
import vswe.stevescarts.StevesCarts;

public class StevesCartsLootTableProvider extends FabricBlockLootTableProvider {
	public StevesCartsLootTableProvider(FabricDataOutput dataGenerator) {
		super(dataGenerator);
	}

	@Override
	public void generate() {
		Registries.BLOCK
				.getIds()
				.stream()
				.filter(id -> id.getNamespace().equals(StevesCarts.MODID))
				.map(Registries.BLOCK::get)
				.forEach(this::addDrop);
	}
}
