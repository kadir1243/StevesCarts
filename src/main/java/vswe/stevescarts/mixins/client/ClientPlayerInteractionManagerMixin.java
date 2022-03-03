package vswe.stevescarts.mixins.client;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;

import vswe.stevescarts.entity.StevesCartsEntities;

@SuppressWarnings("ConstantConditions")
@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {
	@Shadow @Final private MinecraftClient client;

	@Inject(at = @At("HEAD"), method = "hasRidingInventory", cancellable = true)
	private void minecartCheck(CallbackInfoReturnable<Boolean> cir) {
		if (this.client.player.hasVehicle() && this.client.player.getVehicle().getType() == StevesCartsEntities.MODULAR_MINECART_ENTITY) {
			cir.setReturnValue(Boolean.TRUE);
		}
	}
}
