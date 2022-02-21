package vswe.stevescarts.mixins;

import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ServerPlayerEntity.class)
public interface ServerPlayerEntityAccessor {
	@Invoker
	void invokeIncrementScreenHandlerSyncId();

	@Invoker
	void invokeOnScreenHandlerOpened(ScreenHandler screenHandler);

	@Accessor
	int getScreenHandlerSyncId();
}
