package vswe.stevescarts.client.modules.model.hull;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.MinecartEntityModel;
import net.minecraft.util.Identifier;
import vswe.stevescarts.client.modules.model.ModuleModel;

@Environment(EnvType.CLIENT)
public class HullTopModel extends ModuleModel {
	public HullTopModel(Identifier texture) {
		super(getTexturedModelData().createModel(), texture);
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		modelPartData.addChild("top", ModelPartBuilder.create().uv(0, 0).cuboid(-8.0f, -6.0f, -1.0f, 16, 12, 2), ModelTransform.of(0.0f, -4.0f, 0.0f, 1.5707964f, 0.0f, 0.0f));
		return TexturedModelData.of(modelData, 64, 16);
	}
}
