package vswe.stevescarts.mixins;

import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractMinecartEntity.class)
public interface AbstractMinecartEntityAccessor {
	@Accessor
	void setYawFlipped(boolean yawFlipped);

	@Accessor
	boolean isYawFlipped();
}
