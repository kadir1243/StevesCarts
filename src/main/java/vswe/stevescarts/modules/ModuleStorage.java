package vswe.stevescarts.modules;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import org.jetbrains.annotations.Nullable;
import vswe.stevescarts.entity.ModularMinecartEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ModuleStorage {
	public static NbtList getList(ItemStack stack) {
		return stack.getOrCreateNbt().getList("modules", NbtElement.COMPOUND_TYPE);
	}

	public static void setList(ItemStack stack, NbtList list) {
		stack.getOrCreateNbt().put("modules", list);
	}

	public static List<MinecartModule> read(ItemStack stack) {
		return read(getList(stack), null);
	}

	public static void write(ItemStack stack, Collection<MinecartModule> modules) {
		setList(stack, write(new NbtList(), modules));
	}

	public static List<MinecartModule> read(NbtCompound nbt, @Nullable ModularMinecartEntity entity) {
		return read(nbt.getList("modules", NbtElement.COMPOUND_TYPE), entity);
	}

	public static NbtCompound write(NbtCompound nbt, Collection<MinecartModule> modules) {
		NbtList list = new NbtList();
		write(list, modules);
		nbt.put("modules", list);
		return nbt;
	}

	public static List<MinecartModule> read(NbtList nbt, @Nullable ModularMinecartEntity entity) {
		List<MinecartModule> modules = new ArrayList<>();
		for (int i = 0; i < nbt.size(); i++) {
			modules.add(MinecartModuleType.fromNbt(nbt.getCompound(i), entity));
		}
		return modules;
	}

	public static NbtList write(NbtList nbt, Collection<MinecartModule> modules) {
		for (MinecartModule module : modules) {
			nbt.add(MinecartModuleType.toNbt(module));
		}
		return nbt;
	}
}
