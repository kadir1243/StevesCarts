package vswe.stevescarts.client.render.module.model.hull;

import vswe.stevescarts.client.render.module.model.ModuleModel;

import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.util.Identifier;

public class PigHeadModel extends ModuleModel {
	public PigHeadModel(Identifier texture) {
		super(getTexturedModelData().createModel(), texture);
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		modelPartData.addChild("full_head", ModelPartBuilder.create().uv(0, 0).cuboid("head", -4.0f, -4.0f, -8.0f, 8, 8, 8).uv(16, 16).cuboid("snout", -2.0f, 0.0f, -9.0f, 4, 3, 1), ModelTransform.of(-9.0f, -5.0f, 0.0f, 0.0f, 1.5707964f, 0.0f));
		return TexturedModelData.of(modelData, 64, 32);
	}
}
