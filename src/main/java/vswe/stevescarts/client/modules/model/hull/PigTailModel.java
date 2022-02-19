package vswe.stevescarts.client.modules.model.hull;

import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.util.Identifier;
import vswe.stevescarts.client.modules.model.ModuleModel;

public class PigTailModel extends ModuleModel {
	public PigTailModel(Identifier texture) {
		super(getTexturedModelData().createModel(), texture);
		this.root.setTransform(ModelTransform.of(10.0f, -4.0f, 0.0f, 0.0f, 1.5707964f, 0.0f));
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		modelPartData.addChild("tail1", ModelPartBuilder.create().uv(0, 0).cuboid(-1.5f, -0.5f, -0.0f, 3, 1, 1), ModelTransform.pivot(0.0f, 0.0f, 0.0f));
		modelPartData.addChild("tail2", ModelPartBuilder.create().uv(0, 0).cuboid(-0.5f, -1.5f, -0.0f, 1, 3, 1), ModelTransform.pivot(2.0f, -2.0f, 0.0f));
		modelPartData.addChild("tail3", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0f, -0.5f, -0.0f, 2, 1, 1), ModelTransform.pivot(0.5f, -4.0f, 0.0f));
		modelPartData.addChild("tail4", ModelPartBuilder.create().uv(0, 0).cuboid(-0.5f, -0.5f, -0.0f, 1, 1, 1), ModelTransform.pivot(-1.0f, -3.0f, 0.0f));
		modelPartData.addChild("tail5", ModelPartBuilder.create().uv(0, 0).cuboid(-0.5f, -0.5f, -0.0f, 1, 1, 1), ModelTransform.pivot(0.0f, -2.0f, 0.0f));
		return TexturedModelData.of(modelData, 64, 32);
	}
}
