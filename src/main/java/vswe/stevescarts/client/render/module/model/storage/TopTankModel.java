package vswe.stevescarts.client.render.module.model.storage;

import vswe.stevescarts.client.render.module.model.ModuleModel;

import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.util.Identifier;

public class TopTankModel extends ModuleModel {
	public TopTankModel(Identifier texture) {
		super(getTexturedModelData().createModel(), texture);
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		for (int i = 0; i < 2; ++i) {
			modelPartData.addChild("side" + i, ModelPartBuilder.create().uv(0, 13).cuboid(-8.0f, -2.5f, -0.5f, 16, 5, 1), ModelTransform.pivot(0.0f, -8.5f, -5.5f + i * 11));
			modelPartData.addChild("topbot" + i, ModelPartBuilder.create().uv(0, 0).cuboid(-8.0f, -6.0f, -0.5f, 16, 12, 1), ModelTransform.of(0.0f, -5.5f - i * 6, 0.0f, 1.5707964f, 0.0F, 0.0F));
			modelPartData.addChild("frontback" + i, ModelPartBuilder.create().uv(0, 19).cuboid(-5.0f, -2.5f, -0.5f, 10, 5, 1), ModelTransform.of(-7.5f + i * 15, -8.5f, 0.0f, 0.0F, 1.5707964f, 0.0F));
		}
		return TexturedModelData.of(modelData, 64, 32);
	}
}
