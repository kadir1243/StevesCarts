package vswe.stevescarts.modules;

import org.jetbrains.annotations.Nullable;

import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;

import vswe.stevescarts.entity.ModularMinecartEntity;

public abstract class MinecartModule {
	private final MinecartModuleType<?> type;
	protected ModularMinecartEntity minecart;
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

	public void initDataTracker(DataTracker dataTracker) {
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
	public Vec3f getPositionOffset() {
		return Vec3f.ZERO;
	}

	public void tick() {
	}

	public void onScreenOpen() {
	}

	public void onScreenClose() {
	}

	public void onActivate() {

	}

	public boolean shouldMove() {
		return true;
	}

	/**
	 * @implNote Should only be implemented by modules that are engines
	 */
	public boolean canPropel() {
		return false;
	}

	/**
	 * @implNote Should only be implemented by modules that are engines
	 */
	public void onPropel() {
	}

	protected BlockPos getRailPos() {
		int x = (int) Math.floor(this.minecart.getX());
		int y = (int) Math.floor(this.minecart.getY());
		int z = (int) Math.floor(this.minecart.getZ());
		BlockPos railPos = new BlockPos(x, y, z);
		BlockPos downPos = railPos.down();
		return this.minecart.world.getBlockState(downPos).isIn(BlockTags.RAILS) ? downPos : railPos;
	}

	protected boolean checkMovement() {
		return this.minecart.getVelocity().horizontalLengthSquared() > 0.0001D && !this.minecart.isStopped();
	}
}
