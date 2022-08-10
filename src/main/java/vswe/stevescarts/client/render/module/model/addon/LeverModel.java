package vswe.stevescarts.client.render.module.model.addon;

import vswe.stevescarts.client.render.module.model.ModuleModel;
import vswe.stevescarts.module.CartModule;
import vswe.stevescarts.module.addon.Toggleable;

import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.util.Identifier;

public class LeverModel extends ModuleModel {
	public LeverModel(Identifier texture) {
		super(getTexturedModelData().createModel(), texture);
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		modelPartData.addChild("base", ModelPartBuilder.create().uv(0, 0).cuboid(-2.5f, -1.5f, -0.5f, 5, 3, 1), ModelTransform.pivot(0.0f, 2.0f, 8.5f));
		modelPartData.addChild("level", ModelPartBuilder.create().uv(0, 4).cuboid(-0.5f, -12.0f, -0.5f, 1, 11, 1), ModelTransform.pivot(0.0f, 2.0f, 8.5f));
		modelPartData.addChild("back", ModelPartBuilder.create().uv(4, 4).cuboid(-1.0f, -13.0f, -1.0f, 2, 2, 2), ModelTransform.pivot(0.0f, 2.0f, 8.5f));
		return TexturedModelData.of(modelData, 32, 32);
	}

	@Override
	public void animateModel(CartModule module, float limbAngle, float limbDistance, float tickDelta) {
		this.root.roll = 0.3926991f - ((Toggleable) module).getState() * 3.1415927f / 4.0f;
	}
}
