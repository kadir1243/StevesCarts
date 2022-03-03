package vswe.stevescarts.client.modules.model.storage;

import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.util.Identifier;

import vswe.stevescarts.client.modules.model.ModuleModel;

public class SideTanksModel extends ModuleModel {
	public SideTanksModel(Identifier texture) {
		super(getTexturedModelData().createModel(), texture);
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		for (int i = 0; i < 2; ++i) {
			for (int j = 0; j < 2; ++j) {
				modelPartData.addChild("side" + i + j, ModelPartBuilder.create().uv(0, 0).cuboid(-6.0f, -3.0f, -0.5f, 12, 6, 1), ModelTransform.pivot(-2.0f, -0.5f, -10.5f + i * 22 - 3.0f + j * 5));
				modelPartData.addChild("topbot" + i + j, ModelPartBuilder.create().uv(0, 7).cuboid(-6.0f, -2.0f, -0.5f, 12, 4, 1), ModelTransform.of(-2.0f, -3.0f + j * 5, -11.0f + i * 22, 1.5707964f, 0.0F, 0.0F));
			}
			modelPartData.addChild("front" + i, ModelPartBuilder.create().uv(26, 0).cuboid(-2.0f, -2.0f, -0.5f, 4, 4, 1), ModelTransform.of(-7.5f, -0.5f, -11.0f + i * 22, 0.0F, 1.5707964f, 0.0F));
			modelPartData.addChild("back" + i, ModelPartBuilder.create().uv(36, 0).cuboid(-2.0f, -2.0f, -0.5f, 4, 4, 1), ModelTransform.of(4.5f, -0.5f, -11.0f + i * 22, 0.0F, 1.5707964f, 0.0F));
			modelPartData.addChild("tube1" + i, ModelPartBuilder.create().uv(26, 5).cuboid(-1.0f, -1.0f, -1.0f, 2, 2, 2), ModelTransform.pivot(5.5f, -0.5f, -11.0f + i * 22));
			modelPartData.addChild("tube2" + i, ModelPartBuilder.create().uv(26, 5).cuboid(-2.0f, -1.0f, -1.0f, 4, 2, 2), ModelTransform.of(7.5f, -0.5f, -10.0f + i * 20, 0.0F, 1.5707964f, 0.0F));
			modelPartData.addChild("connection" + i, ModelPartBuilder.create().uv(36, 0).cuboid(-2.0f, -2.0f, -0.5f, 4, 4, 1), ModelTransform.pivot(7.5f, -0.5f, -8.5f + i * 17));
		}
		return TexturedModelData.of(modelData, 64, 16);
	}
}
