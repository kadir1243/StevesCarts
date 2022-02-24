package vswe.stevescarts.modules.engine;

import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import vswe.stevescarts.entity.ModularMinecartEntity;
import vswe.stevescarts.modules.MinecartModuleType;
import vswe.stevescarts.screen.ModularCartHandler;

// TODO
public class CoalEngineModule extends EngineModule {
	private final int fuelSlots;
	private final float fuelMultiplier;

	protected CoalEngineModule(ModularMinecartEntity minecart, MinecartModuleType<?> type, int fuelSlots, float fuelMultiplier) {
		super(minecart, type);
		this.fuelSlots = fuelSlots;
		this.fuelMultiplier = fuelMultiplier;
	}

	@Override
	public NbtCompound writeNbt(NbtCompound nbt) {
		return super.writeNbt(nbt);
	}

	@Override
	public void readNbt(NbtCompound nbt) {
		super.readNbt(nbt);
	}

	@Override
	public boolean shouldPropel() {
		return false;
	}

	@Override
	public void onPropel() {

	}

	@Override
	public void configure(WPlainPanel panel, ModularCartHandler handler, PlayerEntity player) {

	}
}
