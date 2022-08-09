package vswe.stevescarts.module;

import java.util.EnumSet;
import java.util.function.BiFunction;

import org.jetbrains.annotations.Nullable;
import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.entity.CartEntity;
import vswe.stevescarts.item.StevesCartsItems;
import vswe.stevescarts.module.hull.HullModuleType;

import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;

public class ModuleType<T extends CartModule> implements ItemConvertible {
	public static final Registry<ModuleType<?>> REGISTRY = (Registry<ModuleType<?>>) (Object) FabricRegistryBuilder.createSimple(ModuleType.class, StevesCarts.id("module_type")).buildAndRegister();

	// TODO
	private final RegistryEntry.Reference<ModuleType<?>> registryEntry = null;
	private final BiFunction<CartEntity, ModuleType<T>, T> factory;
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

	public ModuleType(BiFunction<CartEntity, ModuleType<T>, T> factory, Identifier id, int moduleCost, EnumSet<ModuleSide> sides, ModuleGroup group, boolean hasRenderer, boolean duplicates, boolean noHullTop) {
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
		this.item = new ModuleItem(new Item.Settings().group(StevesCartsItems.MODULES).maxCount(1), this);
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

	public Text getTranslationText() {
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

	public BiFunction<CartEntity, ModuleType<T>, T> getFactory() {
		return factory;
	}

	public T create(CartEntity entity) {
		return factory.apply(entity, this);
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
		CartModule module = type.create(entity);
		module.readFromNbt(compound);
		return module;
	}

	public static boolean isModule(ItemStack stack) {
		return isModule(stack.getItem());
	}

	public static boolean belongsToGroup(ItemStack stack, ModuleGroup group) {
		return isModule(stack) && ((ModuleItem) stack.getItem()).getType().getGroup() == group;
	}

	public static boolean isModule(Item item) {
		return item instanceof ModuleItem;
	}

	@Override
	public Item asItem() {
		return this.item;
	}
}
