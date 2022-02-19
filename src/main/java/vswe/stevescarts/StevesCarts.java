package vswe.stevescarts;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerBlockEntityEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vswe.stevescarts.advancement.StevesCartsStats;
import vswe.stevescarts.block.StevesCartsBlocks;
import vswe.stevescarts.block.entity.CartAssemblerBlockEntity;
import vswe.stevescarts.block.entity.StevesCartsBlockEntities;
import vswe.stevescarts.entity.ModularMinecartEntity;
import vswe.stevescarts.gen.CompRecipeGen;
import vswe.stevescarts.item.StevesCartsItems;
import vswe.stevescarts.modules.StevesCartsModuleTypes;
import vswe.stevescarts.screen.StevesCartsScreenHandlers;

public class StevesCarts implements ModInitializer {
	public static final Logger LOGGER = LogManager.getLogger("stevescarts");
	public static EntityType<ModularMinecartEntity> MODULAR_MINECART_ENTITY;

	@Override
	public void onInitialize() {
		StevesCartsItems.init();
		StevesCartsModuleTypes.init();
		StevesCartsBlocks.init();
		StevesCartsBlockEntities.init();
		StevesCartsStats.init();
		StevesCartsScreenHandlers.init();
		MODULAR_MINECART_ENTITY = Registry.register(Registry.ENTITY_TYPE, id("cart"), FabricEntityTypeBuilder.<ModularMinecartEntity>create(SpawnGroup.MISC, ModularMinecartEntity::new).dimensions(EntityDimensions.fixed(1, 1)).build());
		ServerBlockEntityEvents.BLOCK_ENTITY_LOAD.register((blockEntity, world) -> {
			if (blockEntity.getType() == StevesCartsBlockEntities.CART_ASSEMBLER) {
				((CartAssemblerBlockEntity) blockEntity).onLoad(world);
			}
		});
		LOGGER.info("Steves Carts is done for now, now to let other mods have their turn..."); // easter egg :>
		CompRecipeGen.conc();
	}

	public static Identifier id(String s) {
		return new Identifier("stevescarts", s);
	}
}
