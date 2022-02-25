package vswe.stevescarts.client.modules.model.engine;

import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.util.Identifier;

public class CoalEngineFrameModel extends AbstractCoalEngineModel {
	public CoalEngineFrameModel(Identifier texture) {
		super(getTexturedModelData().createModel(), texture);
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		modelPartData.addChild("left", ModelPartBuilder.create().uv(0, 0).cuboid(-0.5f, -2.5f, -0.5f, 1, 5, 1), ModelTransform.pivot(-4.0f, 0.0f, 0.0f));
		modelPartData.addChild("right", ModelPartBuilder.create().uv(0, 0).cuboid(-0.5f, -2.5f, -0.5f, 1, 5, 1), ModelTransform.pivot(4.0f, 0.0f, 0.0f));
		modelPartData.addChild("top", ModelPartBuilder.create().uv(4, 0).cuboid(-0.5f, -3.5f, -0.5f, 1, 7, 1), ModelTransform.of(0.0f, -3.0f, 0.0f, 0.0f, 0.0f, 1.5707964f));
		modelPartData.addChild("bot", ModelPartBuilder.create().uv(4, 0).cuboid(-0.5f, -3.5f, -0.5f, 1, 7, 1), ModelTransform.of(0.0f, 2.0f, 0.0f, 0.0f, 0.0f, 1.5707964f));
		return TexturedModelData.of(modelData, 8, 8);
	}
}
