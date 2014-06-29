package vswe.stevesvehicles.module.common.addon.mobdetector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import vswe.stevesvehicles.vehicle.VehicleBase;
import vswe.stevesvehicles.old.Helpers.Localization;

public class ModuleAnimal extends ModuleEntityDetector {
	public ModuleAnimal(VehicleBase vehicleBase) {
		super(vehicleBase);
	}

	public String getName() {
		return Localization.MODULES.ADDONS.DETECTOR_ANIMALS.translate();
	}
	public boolean isValidTarget(Entity target) {
		return
		target instanceof EntityAnimal
		&&
		(
			!(target instanceof EntityTameable)
			||
			!((EntityTameable)target).isTamed()
		)
		;
	}
}