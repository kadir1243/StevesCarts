package vswe.stevescarts.modules.engine;

import io.github.cottonmc.cotton.gui.networking.NetworkSide;
import io.github.cottonmc.cotton.gui.networking.ScreenNetworking;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.LightType;
import vswe.stevescarts.entity.ModularMinecartEntity;
import vswe.stevescarts.modules.MinecartModuleType;
import vswe.stevescarts.modules.StevesCartsModuleTypes;
import vswe.stevescarts.screen.ModularCartHandler;
import vswe.stevescarts.screen.StevesCartsScreenHandlers;
import vswe.stevescarts.screen.widget.WLongPropertyLabel;
import vswe.stevescarts.util.LongProperty;
import vswe.stevescarts.util.animator.TrialAnimator;

public class SolarEngineModule extends EngineModule {
	private final TrialAnimator animator = new TrialAnimator();
	private final LongProperty power = LongProperty.create();

	public SolarEngineModule(ModularMinecartEntity minecart, MinecartModuleType<?> type) {
		super(minecart, type);
		power.accept(0L);
	}

	@Override
	public void configure(WPlainPanel panel, ModularCartHandler handler, PlayerEntity player) {
		WLabel label = new WLabel(StevesCartsModuleTypes.SOLAR_ENGINE.getTranslationText());
		panel.add(label, 0, 0);
		super.addPriorityButton(handler, panel, 0, 11);
		WLongPropertyLabel powerLabel = new WLongPropertyLabel("screen.stevescarts.cart.power", this.power);
		panel.add(powerLabel, 17, 12);
		panel.setSize(72, 30);
		handler.addTicker(() -> {
			if (this.power.hasChanged()) {
				ScreenNetworking.of(handler, NetworkSide.SERVER).send(StevesCartsScreenHandlers.PACKET_SOLAR_POWER_UPDATE, buf -> buf.writeVarLong(this.power.getAsLong()));
			}
		});
		ScreenNetworking.of(handler, NetworkSide.CLIENT).receive(StevesCartsScreenHandlers.PACKET_SOLAR_POWER_UPDATE, buf -> this.power.accept(buf.readVarLong()));
	}

	@Override
	public NbtCompound writeNbt(NbtCompound nbt) {
		nbt.putLong("Power", this.power.getAsLong());
		return super.writeNbt(nbt);
	}

	@Override
	public void readNbt(NbtCompound nbt) {
		this.power.accept(nbt.getLong("Power"));
		super.readNbt(nbt);
	}

	@Override
	public boolean canPropel() {
		return this.animator.getThirdProgress(1.0F) == 1.0F && this.power.getAsLong() > 10L;
	}

	@Override
	public void tick() {
		int skyLight = this.minecart.world.getLightLevel(LightType.SKY, this.minecart.getBlockPos());
		this.animator.setOpen(skyLight > 5);
		this.animator.step();
		if (skyLight > 5 && this.animator.getThirdProgress(1.0F) == 1.0F) {
			this.power.inc((long) (((skyLight - 5) / 10.0) * 10));
		}
	}

	@Override
	public void onPropel() {
		this.power.inc(-10L);
	}

	@Override
	protected String getDiscriminator() {
		return "solar";
	}

	public float getLiftProgress(float tickDelta) {
		return MathHelper.lerp(this.animator.getFirstProgress(tickDelta) - this.animator.getThirdProgress(tickDelta), -4.0F, -13.0F);
	}
}
