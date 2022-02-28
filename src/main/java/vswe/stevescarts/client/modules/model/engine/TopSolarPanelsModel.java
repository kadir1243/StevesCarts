package vswe.stevescarts.client.modules.model.engine;

import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.util.Identifier;
import vswe.stevescarts.client.modules.model.ModuleModel;
import vswe.stevescarts.modules.MinecartModule;
import vswe.stevescarts.modules.engine.SolarEngineModule;

public class SolarPanelBaseModel extends ModuleModel {
	private final ModelPart moving;
	private final ModelPart top;

	public SolarPanelBaseModel(Identifier texture) {
		super(getTexturedModelData().createModel(), texture);
		this.moving = this.getRoot().getChild("moving");
		this.top = this.getRoot().getChild("top");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		modelPartData.addChild("base", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0f, -5.0f, -1.0f, 2, 10, 2), ModelTransform.pivot(0.0f, -4.5f, 0.0f));
		modelPartData.addChild("moving", ModelPartBuilder.create().uv(8, 0).cuboid(-2.0f, -3.5f, -2.0f, 4, 7, 4), ModelTransform.pivot(0.0f, 0.0f, 0.0f));
		modelPartData.addChild("top", ModelPartBuilder.create().uv(0, 12).cuboid(-6.0f, -1.5f, -2.0f, 12, 3, 4), ModelTransform.pivot(0.0f, -5.0f, 0.0f));
		return TexturedModelData.of(modelData, 32, 32);
	}

	@Override
	public void animateModel(MinecartModule module, float limbAngle, float limbDistance, float tickDelta) {
		this.moving.pivotY = ((SolarEngineModule) module).getLiftProgress(tickDelta);
		this.top.pivotY = this.moving.pivotY - 5.0F;
	}
}
