package vswe.stevescarts.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.collection.DefaultedList;

@Mixin(ScreenHandler.class)
public interface ScreenHandlerAccessor {
	@Invoker
	Slot callAddSlot(Slot slot);

	@Accessor
	DefaultedList<ItemStack> getTrackedStacks();

	@Accessor
	DefaultedList<ItemStack> getPreviousTrackedStacks();
}
