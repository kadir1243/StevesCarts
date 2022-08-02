package vswe.stevescarts.module;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jetbrains.annotations.Nullable;
import vswe.stevescarts.entity.CartEntity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;

public class ModuleStorage {
	public static NbtList getList(ItemStack stack) {
		return stack.getOrCreateNbt().getList("modules", NbtElement.COMPOUND_TYPE);
	}

	public static void setList(ItemStack stack, NbtList list) {
		stack.getOrCreateNbt().put("modules", list);
	}

	public static List<CartModule> read(ItemStack stack) {
		return read(getList(stack), null);
	}

	public static void write(ItemStack stack, Collection<CartModule> modules) {
		setList(stack, write(new NbtList(), modules));
	}

	public static List<CartModule> read(NbtCompound nbt, @Nullable CartEntity entity) {
		return read(nbt.getList("modules", NbtElement.COMPOUND_TYPE), entity);
	}

	public static NbtCompound write(NbtCompound nbt, Collection<CartModule> modules) {
		NbtList list = new NbtList();
		write(list, modules);
		nbt.put("modules", list);
		return nbt;
	}

	public static List<CartModule> read(NbtList nbt, @Nullable CartEntity entity) {
		List<CartModule> modules = new ArrayList<>();
		for (int i = 0; i < nbt.size(); i++) {
			modules.add(ModuleType.fromNbt(nbt.getCompound(i), entity));
		}
		return modules;
	}

	public static NbtList write(NbtList nbt, Collection<CartModule> modules) {
		for (CartModule module : modules) {
			nbt.add(ModuleType.toNbt(module));
		}
		return nbt;
	}
}
