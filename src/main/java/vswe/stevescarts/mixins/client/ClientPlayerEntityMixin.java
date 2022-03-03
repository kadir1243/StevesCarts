package vswe.stevescarts.mixins.client;

import com.mojang.authlib.GameProfile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;

import vswe.stevescarts.entity.StevesCartsEntities;

@Environment(EnvType.CLIENT)
@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends PlayerEntity {
	public ClientPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile profile) {
		super(world, pos, yaw, profile);
	}

	@Inject(at = @At("HEAD"), method = "openRidingInventory", cancellable = true)
	private void openMinecartInventory(CallbackInfo ci) {
		if (this.getVehicle().getType() == StevesCartsEntities.MODULAR_MINECART_ENTITY) {
			ClientPlayNetworking.send(StevesCartsEntities.PACKET_CART_INVENTORY, PacketByteBufs.empty());
			ci.cancel();
		}
	}
}
