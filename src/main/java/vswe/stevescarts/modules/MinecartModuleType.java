package vswe.stevescarts.modules;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Lifecycle;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.registry.SimpleRegistry;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.entity.ModularMinecartEntity;
import vswe.stevescarts.item.modules.ModuleItem;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Supplier;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public final class MinecartModuleType<T extends MinecartModule> {
	public static final Registry<MinecartModuleType<? extends MinecartModule>> REGISTRY = FabricRegistryBuilder.<MinecartModuleType<? extends MinecartModule>, SimpleRegistry<MinecartModuleType<? extends MinecartModule>>>
			from(new SimpleRegistry<>(RegistryKey.ofRegistry(StevesCarts.id("module_type")), Lifecycle.stable())).buildAndRegister();
	public static final RegistryKey<Registry<MinecartModuleType<? extends MinecartModule>>> REGISTRY_KEY = RegistryKey.ofRegistry(StevesCarts.id("module_type"));
	private final BiFunction<ModularMinecartEntity, MinecartModuleType<T>, T> factory;
	private final Supplier<Item> item;
	private final ModuleCategory category;
	private final Identifier id;
	private final int moduleCost;
	private final EnumSet<ModuleSide> sides;
	private final List<Text> tooltip;
	private final Optional<HullData> hullData;
	private final Optional<ToolData> toolData;

	private MinecartModuleType(BiFunction<ModularMinecartEntity, MinecartModuleType<T>, T> factory, Supplier<Item> item, ModuleCategory category, Identifier id, int moduleCost, EnumSet<ModuleSide> sides, List<Text> tooltip, Optional<HullData> hullData, Optional<ToolData> toolData) {
		this.factory = factory;
		this.item = item;
		this.category = category;
		this.id = id;
		this.moduleCost = moduleCost;
		this.sides = sides;
		this.tooltip = tooltip;
		this.hullData = hullData;
		this.toolData = toolData;
	}

	private MinecartModuleType(BiFunction<ModularMinecartEntity, MinecartModuleType<T>, T> factory, Supplier<Item> item, ModuleCategory category, Identifier id, int moduleCost, EnumSet<ModuleSide> sides, List<Text> tooltip, ToolData toolData) {
		this(factory, item, category, id, moduleCost, sides, tooltip, Optional.empty(), Optional.of(toolData));
	}

	private MinecartModuleType(BiFunction<ModularMinecartEntity, MinecartModuleType<T>, T> factory, Supplier<Item> item, ModuleCategory category, Identifier id, int moduleCost, EnumSet<ModuleSide> sides, List<Text> tooltip, HullData hullData) {
		this(factory, item, category, id, moduleCost, sides, tooltip, Optional.of(hullData), Optional.empty());
	}

	private MinecartModuleType(BiFunction<ModularMinecartEntity, MinecartModuleType<T>, T> factory, Supplier<Item> item, ModuleCategory category, Identifier id, int moduleCost, EnumSet<ModuleSide> sides, List<Text> tooltip) {
		this(factory, item, category, id, moduleCost, sides, tooltip, Optional.empty(), Optional.empty());
	}

	public T createModule(ModularMinecartEntity cart) {
		return factory.apply(cart, this);
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

	public void appendTooltip(List<Text> tooltip, TooltipContext context) {
		tooltip.addAll(this.tooltip);
		if (context.isAdvanced()) {
			// TODO
		}
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
		return type.createModule(cart);
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

	public static boolean isModule(Item item) {
		return item instanceof ModuleItem;
	}

	public static boolean isHull(Item item) {
		return isModule(item) && ((ModuleItem) item).isOf(ModuleCategory.HULL);
	}

	public static boolean isEngine(Item item) {
		return isModule(item) && ((ModuleItem) item).isOf(ModuleCategory.ENGINE);
	}

	public static <T extends MinecartModule> Builder<T> builder() {
		return new Builder<>();
	}

	public static sealed class Builder<T extends MinecartModule> permits Hull, Tool {
		protected BiFunction<Item.Settings, MinecartModuleType<T>, ModuleItem> itemFactory = ModuleItem::new;
		protected ModuleCategory category;
		protected Identifier id;
		protected BiFunction<ModularMinecartEntity, MinecartModuleType<T>, T> factory;
		protected final EnumSet<ModuleSide> sides = EnumSet.noneOf(ModuleSide.class);
		protected int moduleCost = 0;
		protected final ImmutableList.Builder<Text> tooltip = ImmutableList.builder();
		protected Item finalItem = null;

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

		protected void validate() {
			if (this.id == null) {
				throw new IllegalArgumentException("Module type ID is null");
			} else if (this.factory == null) {
				throw new IllegalArgumentException("Module type factory is null");
			}
		}

		public MinecartModuleType<T> buildAndRegister() {
			this.validate();
			MinecartModuleType<T> type = Registry.register(REGISTRY, this.id, new MinecartModuleType<>(this.factory, () -> this.finalItem, this.category, this.id, this.moduleCost, this.sides, this.tooltip.build()));
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
			MinecartModuleType<T> type = Registry.register(REGISTRY, this.id, new MinecartModuleType<>(this.factory, () -> this.finalItem, this.category, this.id, this.moduleCost, this.sides, this.tooltip.build(), new HullData(this.modularCapacity, this.engineMaxCount, this.addonMaxCount, this.complexityMax)));
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

		public Tool<T> unbreakable() {
			this.unbreakable = true;
			return this;
		}

		@Override
		public MinecartModuleType<T> buildAndRegister() {
			this.validate();
			MinecartModuleType<T> type = Registry.register(REGISTRY, this.id, new MinecartModuleType<>(this.factory, () -> this.finalItem, this.category, this.id, this.moduleCost, this.sides, this.tooltip.build(), new ToolData(this.unbreakable)));
			this.finalItem = Registry.register(Registry.ITEM, this.id, this.itemFactory.apply(new Item.Settings(), type));
			return type;
		}
	}

	public record HullData(int modularCapacity, int engineMaxCount, int addonMaxCount, int complexityMax) {}

	public record ToolData(boolean unbreakable) {}
}
