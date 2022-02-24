package vswe.stevescarts.modules;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import vswe.stevescarts.entity.ModularMinecartEntity;

public abstract class MinecartModule {
	protected ModularMinecartEntity minecart;
	private final MinecartModuleType<?> type;
	private int id;

	protected MinecartModule(ModularMinecartEntity minecart, MinecartModuleType<?> type) {
		this.minecart = minecart;
		this.type = type;
	}

	public ModularMinecartEntity getMinecart() {
		return this.minecart;
	}

	public void setMinecart(@Nullable ModularMinecartEntity minecart) {
		this.minecart = minecart;
	}

	public MinecartModuleType<?> getType() {
		return this.type;
	}

	public NbtCompound writeNbt(NbtCompound nbt) {
		nbt.putInt("id", this.getId());
		return nbt;
	}

	public void readNbt(NbtCompound nbt) {
		this.setId(nbt.getInt("id"));
	}

	public void init() {
	}

	public void preInit() {
	}

	public final int getId() {
		return id;
	}

	public final void setId(int id) {
		this.id = id;
	}

	/**
	 * @return The offset of the module from the posiiton of the minecart.
	 * @implNote Used in rendering the module
	 */
	public Vec3d getPositionOffset() {
		return Vec3d.ZERO;
	}

	public void tick() {
	}

	public void onScreenOpen() {
	}

	public void onScreenClose() {
	}

	public boolean shouldPropel() {
		return false;
	}

	public boolean hasPropellant() {
		return false;
	}
}
