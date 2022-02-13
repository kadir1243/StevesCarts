package vswe.stevescarts.client.modules.hull.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.util.Identifier;
import vswe.stevescarts.client.modules.model.ModuleModel;

@Environment(EnvType.CLIENT)
public class HullModel extends ModuleModel {
	public HullModel() {
		super();
//		ModelPart bottom = new ModelPart();
//		this.addModel();
	}

	@Override
	public Identifier getTexture() {
		return null; // TODO
	}
}
