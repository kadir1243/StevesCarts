package vswe.stevescarts.modules;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Vec3d;
import vswe.stevescarts.entity.ModularMinecartEntity;

public abstract class MinecartModule {
	protected final ModularMinecartEntity minecart;
	private final MinecartModuleType<?> type;

	protected MinecartModule(ModularMinecartEntity minecart, MinecartModuleType<?> type) {
		this.minecart = minecart;
		this.type = type;
	}

	public ModularMinecartEntity getMinecart() {
		return this.minecart;
	}

	public MinecartModuleType<?> getType() {
		return this.type;
	}

	public NbtCompound writeNbt(NbtCompound nbt) {
		return nbt;
	}

	public void readNbt(NbtCompound nbt) {
	}

	public void init() {
	}

	public void preInit() {
	}

	/**
	 * @return The offset of the module from the posiiton of the minecart.
	 * @implNote Used in rendering the module
	 */
	public Vec3d getPositionOffset() {
		return Vec3d.ZERO;
	}
}
