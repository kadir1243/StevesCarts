package vswe.stevescarts.module;

import org.jetbrains.annotations.Nullable;
import vswe.stevescarts.entity.CartEntity;

import net.minecraft.nbt.NbtCompound;

public abstract class CartModule {
	private CartEntity entity;
	private int id = -1;

	public CartModule(@Nullable CartEntity entity) {
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

	public abstract ModuleType<?> getType();

	public void setEntity(CartEntity entity) {
		this.entity = entity;
	}

	public CartEntity getEntity() {
		return entity;
	}
}
