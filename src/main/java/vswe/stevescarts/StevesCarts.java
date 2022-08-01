package vswe.stevescarts;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vswe.stevescarts.block.StevesCartsBlocks;
import vswe.stevescarts.block.entity.StevesCartsBlockEntities;
import vswe.stevescarts.entity.StevesCartsEntities;
import vswe.stevescarts.item.StevesCartsItems;
import vswe.stevescarts.module.StevesCartsModules;
import vswe.stevescarts.screen.StevesCartsScreenHandlers;

import net.minecraft.util.Identifier;

public class StevesCarts implements ModInitializer {
	public static final String MODID = "stevescarts";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	public static Identifier id(String s) {
		return new Identifier(MODID, s);
	}

	@Override
	public void onInitialize() {
		StevesCartsItems.init();
		StevesCartsBlocks.init();
		StevesCartsBlockEntities.init();
		StevesCartsScreenHandlers.init();
		StevesCartsEntities.init();
		StevesCartsModules.init();
		LOGGER.info("Steves Carts is done for now, now to let other mods have their turn..."); // easter egg :>
	}
}
