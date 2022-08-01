package vswe.stevescarts.module;

import java.util.EnumSet;
import java.util.function.Consumer;
import java.util.function.Function;

import org.jetbrains.annotations.Nullable;
import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.entity.CartEntity;

import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;

public class ModuleType<T extends CartModule> {
	public static final Registry<ModuleType<?>> REGISTRY = (Registry<ModuleType<?>>) (Object) FabricRegistryBuilder.createSimple(ModuleType.class, StevesCarts.id("module_type")).buildAndRegister();

	private final RegistryEntry.Reference<ModuleType<?>> registryEntry = REGISTRY.createEntry(this);
	private final Function<CartEntity, T> factory;
	private final Identifier id;
	private final ModuleItem item;
	private final int moduleCost;
	private final EnumSet<ModuleSide> sides;
	private final String translationKey;
	private final Text translationKeyText;
	private final ModuleGroup group;
	private final boolean hasRenderer;
	private final boolean duplicates;
	private final boolean noHullTop;

	public ModuleType(Function<CartEntity, T> factory, Identifier id, int moduleCost, EnumSet<ModuleSide> sides, ModuleGroup group, boolean hasRenderer, boolean duplicates, boolean noHullTop) {
		this.factory = factory;
		this.id = id;
		this.moduleCost = moduleCost;
		this.sides = sides;
		this.translationKey = "module." + id.getNamespace() + "." + id.getPath();
		this.group = group;
		this.hasRenderer = hasRenderer;
		this.duplicates = duplicates;
		this.noHullTop = noHullTop;
		this.translationKeyText = Text.translatable(this.translationKey);
		this.item = new ModuleItem(new Item.Settings(), this);
		Registry.register(Registry.ITEM, id, this.item);
	}

	public RegistryEntry.Reference<ModuleType<?>> getRegistryEntry() {
		return registryEntry;
	}

	public Identifier getId() {
		return id;
	}

	public ModuleItem getItem() {
		return item;
	}

	public int getModuleCost() {
		return moduleCost;
	}

	public EnumSet<ModuleSide> getSides() {
		return sides;
	}

	public String getTranslationKey() {
		return translationKey;
	}

	public Text getTranslationKeyText() {
		return translationKeyText;
	}

	public ModuleGroup getGroup() {
		return group;
	}

	public boolean hasRenderer() {
		return hasRenderer;
	}

	public boolean allowsDuplicates() {
		return duplicates;
	}

	public boolean shouldRemoveTop() {
		return noHullTop;
	}

	public Function<CartEntity, T> getFactory() {
		return factory;
	}

	public boolean isHull() {
		return this.group == ModuleGroup.HULL;
	}

	public HullModuleType<T> asHull() {
		return (HullModuleType<T>) this;
	}

	public static NbtCompound toNbt(CartModule module) {
		NbtCompound compound = new NbtCompound();
		module.writeToNbt(compound);
		compound.putString("id", module.getType().getId().toString());
		return compound;
	}

	public static CartModule fromNbt(NbtCompound compound, @Nullable CartEntity entity) {
		Identifier id = new Identifier(compound.getString("id"));
		ModuleType<?> type = REGISTRY.get(id);
		if (type == null) {
			throw new IllegalArgumentException("Module type " + id + " not found");
		}
		CartModule module = type.getFactory().apply(entity);
		module.readFromNbt(compound);
		return module;
	}
}
