package vswe.stevescarts.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.stat.StatFormatter;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;

@Mixin(Stats.class)
public interface StatsAccessor {
	@Invoker
	static Identifier invokeRegister(String id, StatFormatter formatter) {
		throw new UnsupportedOperationException();
	}
}
