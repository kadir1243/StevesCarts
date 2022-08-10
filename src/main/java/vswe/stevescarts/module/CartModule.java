package vswe.stevescarts.module;

import org.jetbrains.annotations.Nullable;
import vswe.stevescarts.entity.CartEntity;

import net.minecraft.nbt.NbtCompound;

public abstract class CartModule {
	private final ModuleType<?> type;
	private CartEntity entity;
	private int id = -1;

	public CartModule(@Nullable CartEntity entity, ModuleType<?> type) {
		this.type = type;
		this.entity = entity;
	}

	public void readFromNbt(NbtCompound nbt) {
	}

	public void writeToNbt(NbtCompound nbt) {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isInitialized() {
		return id != -1;
	}

	public final ModuleType<?> getType() {
		return type;
	}

	public void setEntity(CartEntity entity) {
		this.entity = entity;
	}

	public CartEntity getEntity() {
		return entity;
	}

	public void onScreenClose() {
	}

	public void onScreenOpen() {
	}

	public void tick() {
	}

	public boolean canPropel() {
		return false;
	}

	public void onPropel() {
	}

	public boolean shouldMove() {
		return true;
	}

	public void onActivate() {
	}
}
