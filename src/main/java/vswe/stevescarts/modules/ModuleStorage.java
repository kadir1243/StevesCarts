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

	public static List<MinecartModule> read(ItemStack stack) {
		return read(stack.getOrCreateNbt(), null);
	}

	public static void write(ItemStack stack, Collection<MinecartModule> modules) {
		NbtCompound nbt = stack.getOrCreateNbt();
		write(nbt, modules);
	}

	public static List<MinecartModule> read(NbtCompound nbt, @Nullable ModularMinecartEntity entity) {
		NbtList list = nbt.getList("modules", NbtElement.COMPOUND_TYPE);
		List<MinecartModule> modules = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			modules.add(MinecartModuleType.fromNbt(list.getCompound(i), entity));
		}
		return modules;
	}

	public static void write(NbtCompound nbt, Collection<MinecartModule> modules) {
		NbtList list = new NbtList();
		for (MinecartModule module : modules) {
			list.add(MinecartModuleType.toNbt(module));
		}
		nbt.put("modules", list);
	}
}
