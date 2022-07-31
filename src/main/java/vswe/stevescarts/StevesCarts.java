package vswe.stevescarts;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vswe.stevescarts.block.StevesCartsBlocks;
import vswe.stevescarts.item.StevesCartsItems;

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
		LOGGER.info("Steves Carts is done for now, now to let other mods have their turn..."); // easter egg :>
	}
}
