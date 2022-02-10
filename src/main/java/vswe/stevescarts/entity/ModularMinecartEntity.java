package vswe.stevescarts.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.world.World;
import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.modules.MinecartModule;
import vswe.stevescarts.modules.MinecartModuleType;
import vswe.stevescarts.modules.hull.HullModule;

import java.util.ArrayList;
import java.util.List;

// TODO
public class ModularMinecartEntity extends AbstractMinecartEntity {
	public List<MinecartModule> modules = new ArrayList<>();

	public ModularMinecartEntity(EntityType<?> entityType, World world) {
		super(entityType, world);
	}

	public ModularMinecartEntity(World world, double x, double y, double z, MinecartModuleType<? extends HullModule> hull) {
		super(StevesCarts.MODULAR_MINECART_ENTITY, world, x, y, z);
		HullModule hullModule = hull.createModule(this);
		this.modules.add(hullModule);
	}

	@Override
	public Packet<?> createSpawnPacket() {
		return super.createSpawnPacket();
	}

	@Override
	public Type getMinecartType() {
		return null;
	}

	@Override
	public ItemStack getPickBlockStack() {
		return ItemStack.EMPTY; // TODO
	}
}
