package vswe.stevescarts.client.modules.model.storage;

import net.minecraft.client.model.*;
import net.minecraft.util.Identifier;
import vswe.stevescarts.client.modules.model.ModuleModel;

public class TopChestModel extends ModuleModel {
	public TopChestModel(Identifier texture) {
		super(getTexturedModelData().createModel(), texture);
		this.root.setPivot(-2.5f, -3.0f, 2.0f);
		this.root.setAngles(0.0f, 4.712389f, 0.0f);
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		modelPartData.addChild("base", ModelPartBuilder.create().uv(0, 19).cuboid(6.0f, 2.0f, 8.0f, 12, 4, 16), ModelTransform.pivot(-14.0f, -5.5f, -18.5f));
		modelPartData.addChild("lid", ModelPartBuilder.create().uv(0, 0).cuboid(6.0f, -3.0f, -16.0f, 12, 3, 16), ModelTransform.pivot(-14.0f, -2.5f, 5.5f));
		modelPartData.addChild("lock", ModelPartBuilder.create().uv(0, 0).cuboid(1.0f, 1.5f, 0.5f, 2, 3, 1), ModelTransform.pivot(10.0f, -3.0f, -17.5f));
		return TexturedModelData.of(modelData, 64, 64);
	}
}
