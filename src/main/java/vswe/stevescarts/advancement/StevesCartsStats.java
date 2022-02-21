package vswe.stevescarts.advancement;

import net.minecraft.stat.StatFormatter;
import net.minecraft.util.Identifier;
import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.mixins.StatsAccessor;

public class StevesCartsStats {
	public static final Identifier INTERACT_WITH_CART_ASSEMBLER = StatsAccessor.invokeRegister(StevesCarts.id("interact_with_cart_assembler").toString(), StatFormatter.DEFAULT);

	public static void init() {
	}
}
