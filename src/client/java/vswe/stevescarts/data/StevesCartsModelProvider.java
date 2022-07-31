package vswe.stevescarts.data;

import java.util.Arrays;
import java.util.Optional;

import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.block.StevesCartsBlocks;
import vswe.stevescarts.block.UpgradeBlock;
import vswe.stevescarts.item.CartComponentItem;

import net.minecraft.block.Block;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.BlockStateSupplier;
import net.minecraft.data.client.BlockStateVariant;
import net.minecraft.data.client.BlockStateVariantMap;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.ModelIds;
import net.minecraft.data.client.Models;
import net.minecraft.data.client.TextureKey;
import net.minecraft.data.client.TextureMap;
import net.minecraft.data.client.VariantSettings;
import net.minecraft.data.client.VariantsBlockStateSupplier;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;

@Environment(EnvType.CLIENT)
public class StevesCartsModelProvider extends FabricModelProvider {
	public static final Model UPGRADE = new Model(Optional.of(StevesCarts.id("block/upgrade")), Optional.empty(), TextureKey.LAYER0, TextureKey.FRONT);
	public static final Model UPGRADE_HORIZONTAL = new Model(Optional.of(StevesCarts.id("block/upgrade_horizontal")), Optional.empty(), TextureKey.LAYER0, TextureKey.FRONT);
	public static final Model UPGRADE_HORIZONTAL_DOWN = new Model(Optional.of(StevesCarts.id("block/upgrade_horizontal_down")), Optional.empty(), TextureKey.LAYER0, TextureKey.FRONT);
	public static final Model UPGRADE_IDLE = new Model(Optional.of(StevesCarts.id("block/upgrade_idle")), Optional.empty(), TextureKey.LAYER0, TextureKey.FRONT);

	public StevesCartsModelProvider(FabricDataGenerator dataGenerator) {
		super(dataGenerator);
	}

	@Override
	public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
		blockStateModelGenerator.registerTurnableRail(StevesCartsBlocks.ADVANCED_DETECTOR_RAIL);
		blockStateModelGenerator.registerCubeWithCustomTextures(StevesCartsBlocks.CARGO_MANAGER, null, (block, ignored) -> new TextureMap().put(TextureKey.PARTICLE, StevesCarts.id("block/cargo_manager_top")).put(TextureKey.UP, StevesCarts.id("block/cargo_manager_top")).put(TextureKey.DOWN, StevesCarts.id("block/cargo_manager_bot")).put(TextureKey.SOUTH, StevesCarts.id("block/cargo_manager_blue")).put(TextureKey.NORTH, StevesCarts.id("block/cargo_manager_yellow")).put(TextureKey.EAST, StevesCarts.id("block/cargo_manager_red")).put(TextureKey.WEST, StevesCarts.id("block/cargo_manager_green")));
		blockStateModelGenerator.registerCubeWithCustomTextures(StevesCartsBlocks.CART_ASSEMBLER, null, (block, ignored) -> new TextureMap().put(TextureKey.PARTICLE, StevesCarts.id("block/cart_assembler_top")).put(TextureKey.UP, StevesCarts.id("block/cart_assembler_top")).put(TextureKey.DOWN, StevesCarts.id("block/cargo_manager_bot")).put(TextureKey.SOUTH, StevesCarts.id("block/cart_assembler_side_1")).put(TextureKey.NORTH, StevesCarts.id("block/cart_assembler_side_1")).put(TextureKey.EAST, StevesCarts.id("block/cart_assembler_side_1")).put(TextureKey.WEST, StevesCarts.id("block/cart_assembler_side_1")));
		blockStateModelGenerator.registerCubeWithCustomTextures(StevesCartsBlocks.DETECTOR_JUNCTION, null, (block, ignored) -> new TextureMap().put(TextureKey.PARTICLE, StevesCarts.id("block/detector_junction_top")).put(TextureKey.UP, StevesCarts.id("block/detector_junction_top")).put(TextureKey.DOWN, StevesCarts.id("block/detector_manager_bot")).put(TextureKey.SOUTH, StevesCarts.id("block/detector_junction_blue")).put(TextureKey.NORTH, StevesCarts.id("block/detector_junction_yellow")).put(TextureKey.EAST, StevesCarts.id("block/detector_junction_red")).put(TextureKey.WEST, StevesCarts.id("block/detector_junction_green")));
		blockStateModelGenerator.registerCubeWithCustomTextures(StevesCartsBlocks.DETECTOR_MANAGER, null, (block, ignored) -> new TextureMap().put(TextureKey.PARTICLE, StevesCarts.id("block/detector_manager_top")).put(TextureKey.UP, StevesCarts.id("block/detector_manager_top")).put(TextureKey.DOWN, StevesCarts.id("block/detector_manager_bot")).put(TextureKey.SOUTH, StevesCarts.id("block/detector_manager_blue")).put(TextureKey.NORTH, StevesCarts.id("block/detector_manager_yellow")).put(TextureKey.EAST, StevesCarts.id("block/detector_manager_red")).put(TextureKey.WEST, StevesCarts.id("block/detector_manager_green")));
		blockStateModelGenerator.registerCubeWithCustomTextures(StevesCartsBlocks.DETECTOR_REDSTONE_UNIT, null, (block, ignored) -> new TextureMap().put(TextureKey.PARTICLE, StevesCarts.id("block/detector_redstone_bot")).put(TextureKey.UP, StevesCarts.id("block/detector_redstone_bot")).put(TextureKey.DOWN, StevesCarts.id("block/detector_redstone_bot")).put(TextureKey.SOUTH, StevesCarts.id("block/detector_redstone_blue")).put(TextureKey.NORTH, StevesCarts.id("block/detector_redstone_yellow")).put(TextureKey.EAST, StevesCarts.id("block/detector_redstone_red")).put(TextureKey.WEST, StevesCarts.id("block/detector_redstone_green")));
		blockStateModelGenerator.registerCubeWithCustomTextures(StevesCartsBlocks.DETECTOR_STATION, null, (block, ignored) -> new TextureMap().put(TextureKey.PARTICLE, StevesCarts.id("block/detector_station_top")).put(TextureKey.UP, StevesCarts.id("block/detector_station_top")).put(TextureKey.DOWN, StevesCarts.id("block/detector_manager_bot")).put(TextureKey.SOUTH, StevesCarts.id("block/detector_station_blue")).put(TextureKey.NORTH, StevesCarts.id("block/detector_station_yellow")).put(TextureKey.EAST, StevesCarts.id("block/detector_station_red")).put(TextureKey.WEST, StevesCarts.id("block/detector_station_green")));
		blockStateModelGenerator.registerCubeWithCustomTextures(StevesCartsBlocks.DETECTOR_UNIT, null, (block, ignored) -> new TextureMap().put(TextureKey.PARTICLE, StevesCarts.id("block/detector_station_top")).put(TextureKey.UP, StevesCarts.id("block/detector_station_top")).put(TextureKey.DOWN, StevesCarts.id("block/detector_manager_bot")).put(TextureKey.SOUTH, StevesCarts.id("block/detector_unit_blue")).put(TextureKey.NORTH, StevesCarts.id("block/detector_unit_yellow")).put(TextureKey.EAST, StevesCarts.id("block/detector_unit_red")).put(TextureKey.WEST, StevesCarts.id("block/detector_unit_green")));
		blockStateModelGenerator.registerSimpleCubeAll(StevesCartsBlocks.ENHANCED_GALGADORIAN_BLOCK);
		blockStateModelGenerator.registerCubeWithCustomTextures(StevesCartsBlocks.EXTERNAL_DISTRIBUTOR, null, (block, ignored) -> new TextureMap().put(TextureKey.PARTICLE, StevesCarts.id("block/cargo_distributor_blue")).put(TextureKey.UP, StevesCarts.id("block/cargo_distributor_orange")).put(TextureKey.DOWN, StevesCarts.id("block/cargo_distributor_purple")).put(TextureKey.SOUTH, StevesCarts.id("block/cargo_distributor_blue")).put(TextureKey.NORTH, StevesCarts.id("block/cargo_distributor_yellow")).put(TextureKey.EAST, StevesCarts.id("block/cargo_distributor_red")).put(TextureKey.WEST, StevesCarts.id("block/cargo_distributor_green")));
		blockStateModelGenerator.registerCubeWithCustomTextures(StevesCartsBlocks.FLUID_MANAGER, null, (block, ignored) -> new TextureMap().put(TextureKey.PARTICLE, StevesCarts.id("block/fluid_manager_top")).put(TextureKey.UP, StevesCarts.id("block/fluid_manager_top")).put(TextureKey.DOWN, StevesCarts.id("block/fluid_manager_bot")).put(TextureKey.SOUTH, StevesCarts.id("block/fluid_manager_blue")).put(TextureKey.NORTH, StevesCarts.id("block/fluid_manager_yellow")).put(TextureKey.EAST, StevesCarts.id("block/fluid_manager_red")).put(TextureKey.WEST, StevesCarts.id("block/fluid_manager_green")));
		blockStateModelGenerator.registerSimpleCubeAll(StevesCartsBlocks.GALGADORIAN_BLOCK);
		blockStateModelGenerator.registerTurnableRail(StevesCartsBlocks.JUNCTION_RAIL);
		blockStateModelGenerator.registerCubeWithCustomTextures(StevesCartsBlocks.MODULE_TOGGLER, null, (block, ignored) -> new TextureMap().put(TextureKey.PARTICLE, StevesCarts.id("block/module_toggler_top")).put(TextureKey.UP, StevesCarts.id("block/module_toggler_top")).put(TextureKey.DOWN, StevesCarts.id("block/module_toggler_bot")).put(TextureKey.SOUTH, StevesCarts.id("block/module_toggler_side")).put(TextureKey.NORTH, StevesCarts.id("block/module_toggler_side")).put(TextureKey.EAST, StevesCarts.id("block/module_toggler_side")).put(TextureKey.WEST, StevesCarts.id("block/module_toggler_side")));
		blockStateModelGenerator.registerSimpleCubeAll(StevesCartsBlocks.REINFORCED_METAL_BLOCK);

		Arrays.stream(StevesCartsBlocks.class.getFields()).filter(field -> Block.class.isAssignableFrom(field.getType())).forEach(field -> {
			Block block;
			try {
				block = (Block) field.get(null);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
			if (block instanceof UpgradeBlock upgradeBlock) {
				TextureMap textures = createUpgradeBlockTextureMap(upgradeBlock);
				Identifier upgrade = UPGRADE.upload(upgradeBlock, textures, blockStateModelGenerator.modelCollector);
				Identifier upgradeHorizontal = UPGRADE_HORIZONTAL.upload(upgradeBlock, "_horizontal", textures, blockStateModelGenerator.modelCollector);
				Identifier upgradeHorizontalDown = UPGRADE_HORIZONTAL_DOWN.upload(upgradeBlock, "_horizontal_down", textures, blockStateModelGenerator.modelCollector);
				Identifier upgradeIdle = UPGRADE_IDLE.upload(upgradeBlock, "_idle", textures, blockStateModelGenerator.modelCollector);
				BlockStateSupplier supplier = createUpgradeBlockState(upgradeBlock, upgrade, upgradeHorizontal, upgradeHorizontalDown, upgradeIdle);
				blockStateModelGenerator.blockStateCollector.accept(supplier);
				Models.GENERATED.upload(ModelIds.getItemModelId(upgradeBlock.asItem()), getUpgradeItemTextureMap(upgradeBlock), blockStateModelGenerator.modelCollector);
			}
		});
	}

	@Override
	public void generateItemModels(ItemModelGenerator itemModelGenerator) {
		Registry.ITEM.stream().filter(CartComponentItem.class::isInstance).forEach(item -> {
			itemModelGenerator.register(item, Models.GENERATED);
		});
	}

	public TextureMap createUpgradeBlockTextureMap(UpgradeBlock block) {
		TextureMap t = new TextureMap();
		t.put(TextureKey.LAYER0, StevesCarts.id("block/" + Registry.BLOCK.getId(block).getPath() + "_icon"));
		t.put(TextureKey.FRONT, StevesCarts.id("block/upgrade_side_0"));
		return t;
	}

	public static VariantsBlockStateSupplier createUpgradeBlockState(UpgradeBlock upgradeBlock, Identifier upgrade, Identifier upgradeHorizontal, Identifier upgradeHorizontalDown, Identifier upgradeIdle) {
		return VariantsBlockStateSupplier.create(upgradeBlock).coordinate(BlockStateVariantMap.create(Properties.FACING, UpgradeBlock.CONNECTED)
				.register(Direction.UP, Boolean.FALSE, BlockStateVariant.create()
						.put(VariantSettings.Y, VariantSettings.Rotation.R0)
						.put(VariantSettings.MODEL, upgradeIdle)
				)
				.register(Direction.DOWN, Boolean.FALSE, BlockStateVariant.create()
						.put(VariantSettings.Y, VariantSettings.Rotation.R0)
						.put(VariantSettings.MODEL, upgradeIdle)
				)
				.register(Direction.NORTH, Boolean.FALSE, BlockStateVariant.create()
						.put(VariantSettings.Y, VariantSettings.Rotation.R0)
						.put(VariantSettings.MODEL, upgradeIdle)
				)
				.register(Direction.SOUTH, Boolean.FALSE, BlockStateVariant.create()
						.put(VariantSettings.Y, VariantSettings.Rotation.R0)
						.put(VariantSettings.MODEL, upgradeIdle)
				)
				.register(Direction.EAST, Boolean.FALSE, BlockStateVariant.create()
						.put(VariantSettings.Y, VariantSettings.Rotation.R0)
						.put(VariantSettings.MODEL, upgradeIdle)
				)
				.register(Direction.WEST, Boolean.FALSE, BlockStateVariant.create()
						.put(VariantSettings.Y, VariantSettings.Rotation.R0)
						.put(VariantSettings.MODEL, upgradeIdle)
				)
				.register(Direction.SOUTH, Boolean.TRUE, BlockStateVariant.create()
						.put(VariantSettings.Y, VariantSettings.Rotation.R90)
						.put(VariantSettings.MODEL, upgrade)
				)
				.register(Direction.EAST, Boolean.TRUE, BlockStateVariant.create()
						.put(VariantSettings.MODEL, upgrade)
				)
				.register(Direction.NORTH, Boolean.TRUE, BlockStateVariant.create()
						.put(VariantSettings.Y, VariantSettings.Rotation.R270)
						.put(VariantSettings.MODEL, upgrade)
				)
				.register(Direction.DOWN, Boolean.TRUE, BlockStateVariant.create()
						.put(VariantSettings.MODEL, upgradeHorizontalDown)
				)
				.register(Direction.UP, Boolean.TRUE, BlockStateVariant.create()
						.put(VariantSettings.MODEL, upgradeHorizontal)
				)
				.register(Direction.WEST, Boolean.TRUE, BlockStateVariant.create()
						.put(VariantSettings.Y, VariantSettings.Rotation.R180)
						.put(VariantSettings.MODEL, upgrade)
				));
	}

	public static TextureMap getUpgradeItemTextureMap(UpgradeBlock block) {
		Identifier blockId = Registry.BLOCK.getId(block);
		return TextureMap.layer0(StevesCarts.id("block/" + blockId.getPath() + "_icon"));
	}
}
