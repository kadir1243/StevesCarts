package vswe.stevescarts.client.modules.model.storage;

import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.util.Identifier;

import vswe.stevescarts.client.modules.model.ModuleModel;

public class FrontTankModel extends ModuleModel {
	public FrontTankModel(Identifier texture) {
		super(getTexturedModelData().createModel(), texture);
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		for (int i = 0; i < 2; ++i) {
			modelPartData.addChild("side" + i, ModelPartBuilder.create().uv(0, 15).cuboid(-4.0f, -3.0f, -0.5f, 8, 6, 1), ModelTransform.pivot(-14.0f, 0.0f, -6.5f + i * 13));
			modelPartData.addChild("topbot" + i, ModelPartBuilder.create().uv(0, 0).cuboid(-4.0f, -7.0f, -0.5f, 8, 14, 1), ModelTransform.of(-14.0f, 3.5f - i * 7, 0.0f, 1.5707964f, 0.0F, 0.0F));
			modelPartData.addChild("frontback" + i, ModelPartBuilder.create().uv(0, 22).cuboid(-6.0f, -3.0f, -0.5f, 12, 6, 1), ModelTransform.of(-17.5f + i * 7, 0.0f, 0.0f, 0.0F, 1.5707964f, 0.0F));
		}
		return TexturedModelData.of(modelData, 32, 32);
	}
}
