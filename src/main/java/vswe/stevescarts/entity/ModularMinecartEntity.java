package vswe.stevescarts.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.MinecartEntity;
import net.minecraft.world.World;

// TODO
public class ModularMinecartEntity extends MinecartEntity {
	public ModularMinecartEntity(EntityType<?> entityType, World world) {
		super(entityType, world);
	}

	public ModularMinecartEntity(World world, double x, double y, double z) {
		super(world, x, y, z);
	}
}
