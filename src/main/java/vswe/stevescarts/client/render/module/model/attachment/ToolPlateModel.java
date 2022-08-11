package vswe.stevescarts.client.render.module.model.attachment;

import vswe.stevescarts.client.render.module.model.ModuleModel;

import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.util.Identifier;

public class ToolPlateModel extends ModuleModel {
	public ToolPlateModel(Identifier texture) {
		super(getTexturedModelData().createModel(), texture);
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		modelPartData.addChild("plate", ModelPartBuilder.create().uv(0, 0).cuboid(-5.0f, -7.0f, -2.0f, 10, 6, 1), ModelTransform.of(-9.0f, 4.0f, 0.0f, 0, 1.5707964f, 0));
		return TexturedModelData.of(modelData, 32, 8);
	}
}
