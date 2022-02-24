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
	private int fireIndex = 0;

	public CoalEngineModule(ModularMinecartEntity minecart, MinecartModuleType<?> type, int fuelSlots, float fuelMultiplier) {
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
	public boolean canPropel() {
		return false;
	}

	@Override
	public void onPropel() {

	}

	@Override
	public void configure(WPlainPanel panel, ModularCartHandler handler, PlayerEntity player) {

	}

	@Override
	public void tick() {
		this.fireIndex++;
		if (this.fireIndex >= 4) {
			this.fireIndex = 0;
		}
	}

	public int getFireIndex() {
		return this.fireIndex;
	}
}
