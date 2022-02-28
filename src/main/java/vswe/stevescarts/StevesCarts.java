package vswe.stevescarts;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vswe.stevescarts.advancement.StevesCartsStats;
import vswe.stevescarts.block.StevesCartsBlocks;
import vswe.stevescarts.block.entity.StevesCartsBlockEntities;
import vswe.stevescarts.entity.StevesCartsEntities;
import vswe.stevescarts.item.StevesCartsItems;
import vswe.stevescarts.modules.StevesCartsModuleTypes;
import vswe.stevescarts.screen.StevesCartsScreenHandlers;

public class StevesCarts implements ModInitializer {
	public static final Logger LOGGER = LogManager.getLogger("stevescarts");

	public static Identifier id(String s) {
		return new Identifier("stevescarts", s);
	}

	@Override
	public void onInitialize() {
		StevesCartsItems.init();
		StevesCartsModuleTypes.init();
		StevesCartsBlocks.init();
		StevesCartsBlockEntities.init();
		StevesCartsStats.init();
		StevesCartsScreenHandlers.init();
		StevesCartsEntities.init();
		LOGGER.info("Steves Carts is done for now, now to let other mods have their turn..."); // easter egg :>
	}
}
