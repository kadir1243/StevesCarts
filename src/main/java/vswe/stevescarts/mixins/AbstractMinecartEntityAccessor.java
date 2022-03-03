package vswe.stevescarts.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.entity.vehicle.AbstractMinecartEntity;

@Mixin(AbstractMinecartEntity.class)
public interface AbstractMinecartEntityAccessor {
	@Accessor
	boolean isYawFlipped();

	@Accessor
	void setYawFlipped(boolean yawFlipped);
}
