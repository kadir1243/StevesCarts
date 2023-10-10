package vswe.stevescarts.module.engine;

import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageUtil;
import vswe.stevescarts.entity.CartEntity;
import vswe.stevescarts.module.ModuleType;
import vswe.stevescarts.module.StevesCartsModules;
import vswe.stevescarts.screen.CartHandler;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;

public class ThermalEngineModule extends EngineModule {
	public static final FluidVariant LAVA = FluidVariant.of(Fluids.LAVA);

	public ThermalEngineModule(CartEntity minecart, ModuleType<?> type) {
		super(minecart, type);
	}

	@Override
	public void configure(WPlainPanel panel, CartHandler handler, PlayerEntity player) { // TODO
		WLabel label = new WLabel(StevesCartsModules.THERMAL_ENGINE.getTranslationText());
		panel.add(label, 0, 0);
		super.addPriorityButton(handler, panel, 0, 11);
		panel.setSize(72, 30);
	}

	@Override
	public boolean canPropel() {
		return checkSimulate(LAVA, 15);
	}

	@Override
	public void onPropel() {
		consume(LAVA, 15);
	}

	protected boolean checkSimulate(FluidVariant variant, long amount) {
		return StorageUtil.simulateExtract(this.getEntity().getFluidStorage(), variant, amount, null) == amount;
	}

	protected void consume(FluidVariant variant, long amount) {
		Transaction transaction = Transaction.openOuter();
		this.getEntity().getFluidStorage().extract(variant, amount, transaction);
		transaction.commit();
	}

	@Override
	protected String getDiscriminator() {
		return "thermal";
	}
}
