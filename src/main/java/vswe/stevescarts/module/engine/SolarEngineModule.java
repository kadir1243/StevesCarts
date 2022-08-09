package vswe.stevescarts.module.engine;

import io.github.cottonmc.cotton.gui.networking.NetworkSide;
import io.github.cottonmc.cotton.gui.networking.ScreenNetworking;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.entity.CartEntity;
import vswe.stevescarts.module.ModuleType;
import vswe.stevescarts.module.StevesCartsModules;
import vswe.stevescarts.screen.CartHandler;
import vswe.stevescarts.screen.widget.WLongPropertyLabel;
import vswe.stevescarts.util.LongProperty;
import vswe.stevescarts.util.TrialAnimator;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

public class SolarEngineModule extends EngineModule {
	private final TrialAnimator animator = new TrialAnimator();
	private final LongProperty power = LongProperty.create();
	private final long maxPower;

	public SolarEngineModule(CartEntity minecart, ModuleType<?> type, long maxPower) {
		super(minecart, type);
		this.maxPower = maxPower;
		power.accept(0L);
	}

	public static final Identifier PACKET_SOLAR_POWER_UPDATE = StevesCarts.id("solar_power_update");

	@Override
	public void configure(WPlainPanel panel, CartHandler handler, PlayerEntity player) {
		WLabel label = new WLabel(StevesCartsModules.SOLAR_ENGINE.getTranslationText());
		panel.add(label, 0, 0);
		super.addPriorityButton(handler, panel, 0, 11);
		WLongPropertyLabel powerLabel = new WLongPropertyLabel("screen.stevescarts.cart.power", this.power);
		panel.add(powerLabel, 17, 12);
		panel.setSize(72, 30);
		handler.addTicker(() -> {
			if (this.power.hasChanged()) {
				ScreenNetworking.of(handler, NetworkSide.SERVER).send(PACKET_SOLAR_POWER_UPDATE, buf -> buf.writeVarLong(this.power.getAsLong()));
			}
		});
		ScreenNetworking.of(handler, NetworkSide.CLIENT).receive(PACKET_SOLAR_POWER_UPDATE, buf -> this.power.accept(buf.readVarLong()));
	}

	@Override
	public void writeToNbt(NbtCompound nbt) {
		nbt.putLong("Power", this.power.getAsLong());
		super.writeToNbt(nbt);
	}

	@Override
	public void readFromNbt(NbtCompound nbt) {
		this.power.accept(nbt.getLong("Power"));
		super.readFromNbt(nbt);
	}

	@Override
	public boolean canPropel() {
		return this.animator.getThirdProgress(1.0F) == 1.0F && this.power.getAsLong() > 10L;
	}

	@Override
	public void tick() {
		int skyLight = this.getSkyLight();
		this.animator.setOpen(skyLight > 5);
		this.animator.step();
		if (skyLight > 5 && this.animator.getThirdProgress(1.0F) == 1.0F) {
			this.power.inc((long) (((skyLight - 5) / 5.0) * 10));
			if (this.power.getAsLong() > this.maxPower) {
				this.power.accept(this.maxPower);
			}
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

	public float getTranslation(float tickDelta) {
		return MathHelper.lerp(this.animator.getFirstProgress(tickDelta) - this.animator.getThirdProgress(tickDelta), -4.0F, -13.0F);
	}

	public float getRotation(float tickDelta) {
		return MathHelper.lerp(this.animator.getSecondProgress(tickDelta), 0.0F, 1.5707964f);
	}

	public int getSkyLight() {
		if (this.getEntity() == null) {
			return 0;
		}
		World world = this.getEntity().world;
		if (world == null) {
			return 0;
		}
		return MathHelper.clamp(
				Math.round((world.getLightLevel(LightType.SKY, this.getEntity().getBlockPos()) - world.getAmbientDarkness()) * MathHelper.cos(world.getSkyAngleRadians(1.0f))),
				0,
				15
		);
	}

	public boolean shouldShowActive() {
		return this.animator.getThirdProgress(1.0F) == 1.0F;
	}
}
