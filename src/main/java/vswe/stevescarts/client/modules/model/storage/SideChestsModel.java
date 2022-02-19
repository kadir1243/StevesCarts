package vswe.stevescarts.client.modules.model.storage;

import net.minecraft.client.model.*;
import net.minecraft.util.Identifier;
import vswe.stevescarts.client.modules.model.ModuleModel;

public class SideChestsModel extends ModuleModel {
	public SideChestsModel(Identifier texture) {
		super(getTexturedModelData().createModel(), texture);
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		modelPartData.addChild("base", ModelPartBuilder.create().uv(0, 7).cuboid(8.0f, 3.0f, 2.0f, 16, 6, 4), ModelTransform.pivot(-16.0f, -5.5f, -14.0f));
		modelPartData.addChild("lid", ModelPartBuilder.create().uv(0, 0).cuboid(8.0f, -3.0f, -4.0f, 16, 3, 4), ModelTransform.pivot(-16.0f, -1.5f, -8.0f));
		modelPartData.addChild("lock", ModelPartBuilder.create().uv(0, 17).cuboid(1.0f, 1.5f, 0.5f, 2, 3, 1), ModelTransform.pivot(14.0f, -3.0f, -5.5f));
		return TexturedModelData.of(modelData, 64, 32);
	}
}
