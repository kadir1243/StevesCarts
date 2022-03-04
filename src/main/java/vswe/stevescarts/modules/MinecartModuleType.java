package vswe.stevescarts.modules;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Lifecycle;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Supplier;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.tag.Tag;
import net.minecraft.tag.TagKey;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.registry.SimpleRegistry;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.mininglevel.v1.FabricMineableTags;

import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.entity.ModularMinecartEntity;
import vswe.stevescarts.item.ModularMinecartItem;
import vswe.stevescarts.item.modules.ModuleItem;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public final class MinecartModuleType<T extends MinecartModule> {
	public static final Registry<MinecartModuleType<? extends MinecartModule>> REGISTRY = FabricRegistryBuilder.<MinecartModuleType<? extends MinecartModule>, SimpleRegistry<MinecartModuleType<? extends MinecartModule>>>
			from(new SimpleRegistry<>(RegistryKey.ofRegistry(StevesCarts.id("cart_module_type")), Lifecycle.stable(), MinecartModuleType::getRegistryEntry)).buildAndRegister();
	public static final RegistryKey<Registry<MinecartModuleType<? extends MinecartModule>>> REGISTRY_KEY = RegistryKey.ofRegistry(StevesCarts.id("module_type"));
	private final BiFunction<ModularMinecartEntity, MinecartModuleType<T>, T> factory;
	private final Supplier<Item> item;
	private final ModuleCategory category;
	private final Identifier id;
	private final int moduleCost;
	private final EnumSet<ModuleSide> sides;
	private final List<Text> tooltip;
	private final List<Text> advancedTooltip;
	private final Optional<HullData> hullData;
	private final Optional<ToolData> toolData;
	private final TranslatableText translationText;
	private final boolean allowDuplicates;
	private final boolean shouldRenderTop;
	private final boolean hasRenderer;
	private final List<Supplier<MinecartModuleType<?>>> explicitRequirements;
	private final Object2IntMap<TagKey<MinecartModuleType<?>>> tagRequirements;
	private final List<TagKey<MinecartModuleType<?>>> incompatibilities;
	private final RegistryEntry.Reference<MinecartModuleType<? extends MinecartModule>> registryEntry = REGISTRY.createEntry(this);;

	private MinecartModuleType(BiFunction<ModularMinecartEntity, MinecartModuleType<T>, T> factory, Supplier<Item> item, ModuleCategory category, Identifier id, int moduleCost, EnumSet<ModuleSide> sides, List<Text> tooltip, Optional<HullData> hullData, Optional<ToolData> toolData, boolean allowDuplicates, boolean shouldRenderTop, boolean hasRenderer, List<Supplier<MinecartModuleType<?>>> explicitRequirements, Object2IntMap<TagKey<MinecartModuleType<?>>> tagRequirements, List<TagKey<MinecartModuleType<?>>> incompatibilities) {
		this.factory = factory;
		this.item = item;
		this.category = category;
		this.id = id;
		this.moduleCost = moduleCost;
		this.sides = sides;
		this.tooltip = tooltip;
		this.hullData = hullData;
		this.toolData = toolData;
		this.translationText = new TranslatableText("module." + id.getNamespace() + "." + id.getPath());
		this.allowDuplicates = allowDuplicates;
		this.shouldRenderTop = shouldRenderTop;
		this.hasRenderer = hasRenderer;
		this.explicitRequirements = explicitRequirements;
		this.tagRequirements = tagRequirements;
		this.incompatibilities = incompatibilities;
		this.advancedTooltip = this.generateAdvancedTooltip();
	}

	private MinecartModuleType(BiFunction<ModularMinecartEntity, MinecartModuleType<T>, T> factory, Supplier<Item> item, ModuleCategory category, Identifier id, int moduleCost, EnumSet<ModuleSide> sides, List<Text> tooltip, ToolData toolData, List<Supplier<MinecartModuleType<?>>> explicitRequirements, Object2IntMap<TagKey<MinecartModuleType<?>>> tagRequirements, List<TagKey<MinecartModuleType<?>>> incompatibilities) {
		this(factory, item, category, id, moduleCost, sides, tooltip, Optional.empty(), Optional.of(toolData), false, true, true, explicitRequirements, tagRequirements, incompatibilities);
	}

	private MinecartModuleType(BiFunction<ModularMinecartEntity, MinecartModuleType<T>, T> factory, Supplier<Item> item, ModuleCategory category, Identifier id, int moduleCost, EnumSet<ModuleSide> sides, List<Text> tooltip, HullData hullData) {
		this(factory, item, category, id, moduleCost, sides, tooltip, Optional.of(hullData), Optional.empty(), false, true, true, Collections.emptyList(), new Object2IntOpenHashMap<>(), Collections.emptyList());
	}

	private MinecartModuleType(BiFunction<ModularMinecartEntity, MinecartModuleType<T>, T> factory, Supplier<Item> item, ModuleCategory category, Identifier id, int moduleCost, EnumSet<ModuleSide> sides, List<Text> tooltip, boolean allowDuplicates, boolean shouldRenderTop, boolean hasRenderer, List<Supplier<MinecartModuleType<?>>> explicitRequirements, Object2IntMap<TagKey<MinecartModuleType<?>>> tagRequirements, List<TagKey<MinecartModuleType<?>>> incompatibilities) {
		this(factory, item, category, id, moduleCost, sides, tooltip, Optional.empty(), Optional.empty(), allowDuplicates, shouldRenderTop, hasRenderer, explicitRequirements, tagRequirements, incompatibilities);
	}

	public static NbtCompound toNbt(MinecartModule module) {
		NbtCompound nbt = new NbtCompound();
		nbt.putString("type", module.getType().toString());
		module.writeNbt(nbt);
		return nbt;
	}

	public static MinecartModule fromNbt(NbtCompound nbt, @Nullable ModularMinecartEntity cart) {
		MinecartModuleType<?> type = REGISTRY.get(new Identifier(nbt.getString("type")));
		if (type == null) {
			throw new IllegalArgumentException("No module type in NBT");
		}
		MinecartModule module = type.createModule(cart);
		module.readNbt(nbt);
		return module;
	}

	public static boolean isModule(ItemStack stack) {
		return isModule(stack.getItem());
	}

	public static boolean isHull(ItemStack stack) {
		return isHull(stack.getItem());
	}

	public static boolean isEngine(ItemStack stack) {
		return isEngine(stack.getItem());
	}

	public static boolean isTool(ItemStack stack) {
		return isTool(stack.getItem());
	}

	public static boolean isAttachment(ItemStack stack) {
		return isAttachment(stack.getItem());
	}

	public static boolean isStorage(ItemStack stack) {
		return isStorage(stack.getItem());
	}

	public static boolean isAddon(ItemStack stack) {
		return isAddon(stack.getItem());
	}

	public static boolean isModule(Item item) {
		return item instanceof ModuleItem;
	}

	public static boolean isHull(Item item) {
		return isModule(item) && ((ModuleItem) item).isOf(ModuleCategory.HULL);
	}

	public static boolean isEngine(Item item) {
		return isModule(item) && ((ModuleItem) item).isOf(ModuleCategory.ENGINE);
	}

	public static boolean isTool(Item item) {
		return isModule(item) && ((ModuleItem) item).isOf(ModuleCategory.TOOL);
	}

	public static boolean isAttachment(Item item) {
		return isModule(item) && ((ModuleItem) item).isOf(ModuleCategory.ATTACHMENT);
	}

	public static boolean isStorage(Item item) {
		return isModule(item) && ((ModuleItem) item).isOf(ModuleCategory.STORAGE);
	}

	public static boolean isAddon(Item item) {
		return isModule(item) && ((ModuleItem) item).isOf(ModuleCategory.ADDON);
	}

	public static <T extends MinecartModule> Builder<T> builder() {
		return new Builder<>();
	}

	public T createModule(@Nullable ModularMinecartEntity cart) {
		return factory.apply(cart, this);
	}

	public T createModule() {
		return this.createModule(null);
	}

	@Override
	public String toString() {
		return this.id.toString();
	}

	public Identifier getId() {
		return this.id;
	}

	public Item getItem() {
		return this.item.get();
	}

	public ModuleCategory getCategory() {
		return category;
	}

	public boolean isOf(ModuleCategory category) {
		return this.category == category;
	}

	public EnumSet<ModuleSide> getSides() {
		return this.sides;
	}

	public int getModuleCost() {
		return this.moduleCost;
	}

	public List<Text> getTooltip() {
		return this.tooltip;
	}

	public HullData getHullData() {
		return hullData.orElseThrow(() -> new IllegalStateException("Not a hull: " + this));
	}

	public ToolData getToolData() {
		return toolData.orElseThrow(() -> new IllegalStateException("Not a tool: " + this));
	}

	public TranslatableText getTranslationText() {
		return translationText.copy();
	}

	public boolean allowsDuplicates() {
		return allowDuplicates;
	}

	public boolean shouldRenderTop() {
		return shouldRenderTop;
	}

	public boolean hasRenderer() {
		return hasRenderer;
	}

	public boolean isIn(TagKey<MinecartModuleType<? extends MinecartModule>> key) {
		return this.getRegistryEntry().isIn(key);
	}

	public RegistryEntry.Reference<MinecartModuleType<? extends MinecartModule>> getRegistryEntry() {
		return this.registryEntry;
	}

	@Environment(EnvType.CLIENT)
	public void appendTooltip(List<Text> tooltip, TooltipContext context) {
		tooltip.addAll(this.tooltip);
		if (context.isAdvanced()) {
			tooltip.addAll(this.advancedTooltip);
		} else {
			tooltip.add(ModularMinecartItem.PRESS_SHIFT);
		}
	}

	// TODO
	public MinecartModuleType<?> matchesRequirements(List<MinecartModuleType<?>> types) {
//		for (Supplier<MinecartModuleType<?>> req : this.explicitRequirements) {
//			MinecartModuleType<?> type = req.get();
//			if (!types.contains(type)) {
//				return type;
//			}
//		}

		return null;
	}

	public MinecartModuleType<?> checkIncompatibilities(List<MinecartModuleType<?>> types) {
		for (TagKey<MinecartModuleType<?>> incompatibility : this.incompatibilities) {
			for (MinecartModuleType<?> type : types) {
				if (type.isIn(incompatibility)) {
					return type;
				}
			}
		}

		return null;
	}

	// TODO
	public Object2IntMap.Entry<TagKey<MinecartModuleType<?>>> matchesTagRequirements(List<MinecartModuleType<?>> types) {
//		for (Object2IntMap.Entry<TagKey<MinecartModuleType<?>>> req : this.tagRequirements.object2IntEntrySet()) {
//			if (types.stream().filter(type -> type.isIn(req.getKey())).count() < req.getIntValue()) {
//				return req;
//			}
//		}

		return null;
	}

	private List<Text> generateAdvancedTooltip() {
		ImmutableList.Builder<Text> builder = ImmutableList.builder();
		if (this.sides.isEmpty()) {
			builder.add(new TranslatableText("tooltip.stevescarts.module.sides.none").formatted(Formatting.BLUE));
		} else {
			builder.add(new TranslatableText("tooltip.stevescarts.module.sides.%s".formatted(this.sides.size()), (Object[]) this.sides.stream().map(ModuleSide::asText).toArray(Text[]::new)).formatted(Formatting.BLUE));
		}
		if (this.hullData.isEmpty()) {
			builder.add(new TranslatableText("tooltip.stevescarts.module.cost", this.moduleCost).formatted(Formatting.LIGHT_PURPLE));
		}
		return builder.build();
	}

	public static sealed class Builder<T extends MinecartModule> permits Hull, Tool {
		protected final EnumSet<ModuleSide> sides = EnumSet.noneOf(ModuleSide.class);
		protected final ImmutableList.Builder<Text> tooltip = ImmutableList.builder();
		protected BiFunction<Item.Settings, MinecartModuleType<T>, ModuleItem> itemFactory = ModuleItem::new;
		protected ModuleCategory category;
		protected Identifier id;
		protected BiFunction<ModularMinecartEntity, MinecartModuleType<T>, T> factory;
		protected int moduleCost = 0;
		protected Item finalItem = null;
		protected boolean allowDuplicates = false;
		protected boolean shouldRenderTop = true;
		protected boolean hasRenderer = false;
		protected ImmutableList.Builder<Supplier<MinecartModuleType<?>>> explicitRequirements = ImmutableList.builder();
		protected Object2IntMap<TagKey<MinecartModuleType<?>>> tagRequirements = new Object2IntOpenHashMap<>();
		protected ImmutableList.Builder<TagKey<MinecartModuleType<?>>> incompatibilities = ImmutableList.builder();

		protected Builder() {
		}

		protected Builder(Builder<T> builder) {
			this.itemFactory = builder.itemFactory;
			this.category = builder.category;
			this.id = builder.id;
			this.factory = builder.factory;
			this.sides.addAll(builder.sides);
			this.moduleCost = builder.moduleCost;
			this.tooltip.addAll(builder.tooltip.build());
		}

		public Builder<T> item(BiFunction<Item.Settings, MinecartModuleType<T>, ModuleItem> item) {
			this.itemFactory = item;
			return this;
		}

		public Builder<T> category(ModuleCategory category) {
			this.category = category;
			return this;
		}

		public Builder<T> id(Identifier id) {
			this.id = id;
			return this;
		}

		@ApiStatus.Internal
		Builder<T> id(String id) {
			this.id = new Identifier(StevesCarts.id(id).getNamespace(), StevesCarts.id(id).getPath());
			return this;
		}

		public Builder<T> factory(BiFunction<ModularMinecartEntity, MinecartModuleType<T>, T> factory) {
			this.factory = factory;
			return this;
		}

		public Builder<T> allowDuplicates() {
			this.allowDuplicates = true;
			return this;
		}

		public Builder<T> noRenderTop() {
			this.shouldRenderTop = false;
			return this;
		}

		public Builder<T> sides(ModuleSide... sides) {
			this.sides.addAll(Arrays.asList(sides));
			return this;
		}

		public Builder<T> moduleCost(int moduleCost) {
			this.moduleCost = moduleCost;
			return this;
		}

		public Builder<T> tooltip(Text... tooltip) {
			this.tooltip.addAll(Arrays.asList(tooltip));
			return this;
		}

		public Builder<T> require(MinecartModuleType<?>... types) {
			for (MinecartModuleType<?> type : types) {
				this.explicitRequirements.add(() -> type);
			}
			return this;
		}

		public Builder<T> require(Supplier<MinecartModuleType<?>> type) {
			this.explicitRequirements.add(type);
			return this;
		}

		public Builder<T> require(TagKey<MinecartModuleType<?>> tag, int count) {
			this.tagRequirements.put(tag, count);
			return this;
		}

		public Builder<T> require(TagKey<MinecartModuleType<?>> tag) {
			this.tagRequirements.put(tag, 1);
			return this;
		}

		public Builder<T> incompatible(TagKey<MinecartModuleType<?>> tag) {
			this.incompatibilities.add(tag);
			return this;
		}

		public Builder<T> hasRenderer() {
			this.hasRenderer = true;
			return this;
		}

		protected void validate() {
			if (this.id == null) {
				throw new IllegalArgumentException("Module type ID is null");
			} else if (this.factory == null) {
				throw new IllegalArgumentException("Module type factory is null");
			}
		}

		public MinecartModuleType<T> buildAndRegister() {
			this.validate();
			MinecartModuleType<T> type = Registry.register(REGISTRY, this.id, new MinecartModuleType<>(this.factory, () -> this.finalItem, this.category, this.id, this.moduleCost, this.sides, this.tooltip.build(), this.allowDuplicates, this.shouldRenderTop, this.hasRenderer, this.explicitRequirements.build(), this.tagRequirements, this.incompatibilities.build()));
			this.finalItem = Registry.register(Registry.ITEM, type.getId(), this.itemFactory.apply(new Item.Settings(), type));
			return type;
		}

		public Hull<T> hull() {
			this.moduleCost(0);
			this.category(ModuleCategory.HULL);
			return new Hull<>(this);
		}

		public Tool<T> tool() {
			this.category(ModuleCategory.TOOL);
			return new Tool<>(this);
		}
	}

	public static final class Hull<T extends MinecartModule> extends Builder<T> {
		private int modularCapacity = 0;
		private int engineMaxCount = 0;
		private int addonMaxCount = 0;
		private int complexityMax = 0;
		private boolean defaultTooltip = true;

		public Hull(Builder<T> builder) {
			super(builder);
		}

		@Override
		public Hull<T> item(BiFunction<Item.Settings, MinecartModuleType<T>, ModuleItem> item) {
			return (Hull<T>) super.item(item);
		}

		@Override
		public Hull<T> category(ModuleCategory category) {
			return (Hull<T>) super.category(category);
		}

		@Override
		public Hull<T> id(Identifier id) {
			return (Hull<T>) super.id(id);
		}

		@Override
		Hull<T> id(String id) {
			return (Hull<T>) super.id(id);
		}

		@Override
		public Hull<T> factory(BiFunction<ModularMinecartEntity, MinecartModuleType<T>, T> factory) {
			return (Hull<T>) super.factory(factory);
		}

		@Override
		public Hull<T> sides(ModuleSide... sides) {
			return (Hull<T>) super.sides(sides);
		}

		@Override
		public Hull<T> moduleCost(int moduleCost) {
			return (Hull<T>) super.moduleCost(moduleCost);
		}

		@Override
		public Hull<T> tooltip(Text... tooltip) {
			return (Hull<T>) super.tooltip(tooltip);
		}

		public Hull<T> modularCapacity(int modularCapacity) {
			this.modularCapacity = (modularCapacity);
			return this;
		}

		public Hull<T> engineMaxCount(int engineMaxCount) {
			this.engineMaxCount = (engineMaxCount);
			return this;
		}

		public Hull<T> addonMaxCount(int addonMaxCount) {
			this.addonMaxCount = (addonMaxCount);
			return this;
		}

		public Hull<T> complexityMax(int complexityMax) {
			this.complexityMax = (complexityMax);
			return this;
		}

		public Hull<T> noDefaultTooltip() {
			this.defaultTooltip = false;
			return this;
		}

		@Override
		public Hull<T> allowDuplicates() {
			return (Hull<T>) super.allowDuplicates();
		}

		@Override
		public Hull<T> noRenderTop() {
			return (Hull<T>) super.noRenderTop();
		}

		@Override
		public Hull<T> require(MinecartModuleType<?>... types) {
			return (Hull<T>) super.require(types);
		}

		@Override
		public Hull<T> require(Supplier<MinecartModuleType<?>> type) {
			return (Hull<T>) super.require(type);
		}

		@Override
		public Hull<T> require(TagKey<MinecartModuleType<?>> tag, int count) {
			return (Hull<T>) super.require(tag, count);
		}

		@Override
		public Hull<T> require(TagKey<MinecartModuleType<?>> tag) {
			return (Hull<T>) super.require(tag);
		}

		@Override
		public Hull<T> incompatible(TagKey<MinecartModuleType<?>> tag) {
			return (Hull<T>) super.incompatible(tag);
		}

		@Override
		public Hull<T> hasRenderer() {
			return (Hull<T>) super.hasRenderer();
		}

		@Override
		public MinecartModuleType<T> buildAndRegister() {
			if (this.modularCapacity <= 0) {
				throw new IllegalArgumentException("Modular capacity must be greater than 0");
			} else if (this.engineMaxCount <= 0) {
				throw new IllegalArgumentException("Engine max count must be greater than 0");
			} else if (this.addonMaxCount < 0) {
				throw new IllegalArgumentException("Addon max count must be greater than or equal to 0");
			} else if (this.complexityMax <= 0) {
				throw new IllegalArgumentException("Complexity max must be greater than 0");
			}
			if (this.defaultTooltip) {
				this.tooltip(new TranslatableText("tooltip.stevescarts.hull.modular_capacity", this.modularCapacity).formatted(Formatting.YELLOW));
				this.tooltip(new TranslatableText("tooltip.stevescarts.hull.complexity_max", this.complexityMax).formatted(Formatting.DARK_PURPLE));
				this.tooltip(new TranslatableText("tooltip.stevescarts.hull.engine_max_count", this.engineMaxCount).formatted(Formatting.GOLD));
				this.tooltip(new TranslatableText("tooltip.stevescarts.hull.addon_max_count", this.addonMaxCount).formatted(Formatting.GREEN));
			}
			this.validate();
			MinecartModuleType<T> type = new MinecartModuleType<>(this.factory, () -> this.finalItem, this.category, this.id, this.moduleCost, this.sides, this.tooltip.build(), new HullData(this.modularCapacity, this.engineMaxCount, this.addonMaxCount, this.complexityMax));
			Registry.register(REGISTRY, this.id, type);
			this.finalItem = Registry.register(Registry.ITEM, type.getId(), this.itemFactory.apply(new Item.Settings(), type));
			return type;
		}
	}

	public static final class Tool<T extends MinecartModule> extends Builder<T> {
		private boolean unbreakable = false;

		public Tool(Builder<T> builder) {
			super(builder);
		}

		@Override
		public Tool<T> item(BiFunction<Item.Settings, MinecartModuleType<T>, ModuleItem> item) {
			return (Tool<T>) super.item(item);
		}

		@Override
		public Tool<T> category(ModuleCategory category) {
			return (Tool<T>) super.category(category);
		}

		@Override
		public Tool<T> id(Identifier id) {
			return (Tool<T>) super.id(id);
		}

		@Override
		Tool<T> id(String id) {
			return (Tool<T>) super.id(id);
		}

		@Override
		public Tool<T> factory(BiFunction<ModularMinecartEntity, MinecartModuleType<T>, T> factory) {
			return (Tool<T>) super.factory(factory);
		}

		@Override
		public Tool<T> sides(ModuleSide... sides) {
			return (Tool<T>) super.sides(sides);
		}

		@Override
		public Tool<T> moduleCost(int moduleCost) {
			return (Tool<T>) super.moduleCost(moduleCost);
		}

		@Override
		public Tool<T> tooltip(Text... tooltip) {
			return (Tool<T>) super.tooltip(tooltip);
		}

		@Override
		public Tool<T> require(MinecartModuleType<?>... types) {
			return (Tool<T>) super.require(types);
		}

		@Override
		public Tool<T> require(Supplier<MinecartModuleType<?>> type) {
			return (Tool<T>) super.require(type);
		}

		@Override
		public Tool<T> hasRenderer() {
			return (Tool<T>) super.hasRenderer();
		}

		@Override
		public Tool<T> require(TagKey<MinecartModuleType<?>> tag, int count) {
			return (Tool<T>) super.require(tag, count);
		}

		@Override
		public Tool<T> require(TagKey<MinecartModuleType<?>> tag) {
			return (Tool<T>) super.require(tag);
		}

		@Override
		public Tool<T> incompatible(TagKey<MinecartModuleType<?>> tag) {
			return (Tool<T>) super.incompatible(tag);
		}

		@Override
		public Tool<T> allowDuplicates() {
			return (Tool<T>) super.allowDuplicates();
		}

		@Override
		public Tool<T> noRenderTop() {
			return (Tool<T>) super.noRenderTop();
		}

		public Tool<T> unbreakable() {
			this.unbreakable = true;
			return this;
		}

		@Override
		public MinecartModuleType<T> buildAndRegister() {
			this.validate();
			MinecartModuleType<T> type = Registry.register(REGISTRY, this.id, new MinecartModuleType<>(this.factory, () -> this.finalItem, this.category, this.id, this.moduleCost, this.sides, this.tooltip.build(), new ToolData(this.unbreakable), this.explicitRequirements.build(), this.tagRequirements, this.incompatibilities.build()));
			this.finalItem = Registry.register(Registry.ITEM, this.id, this.itemFactory.apply(new Item.Settings(), type));
			return type;
		}
	}

	public record HullData(int modularCapacity, int engineMaxCount, int addonMaxCount, int complexityMax) {
	}

	public record ToolData(boolean unbreakable) {
	}
}
