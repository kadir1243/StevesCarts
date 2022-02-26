package vswe.stevescarts.client.modules.model.storage;

import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.util.Identifier;
import vswe.stevescarts.client.modules.model.ModuleModel;
import vswe.stevescarts.modules.MinecartModule;
import vswe.stevescarts.modules.storage.ExtractingChestsModule;

public class ExtractingChestsModel extends ModuleModel {
	private final ModelPart base1;
	private final ModelPart base2;
	private final ModelPart lid1;
	private final ModelPart lid2;

	public ExtractingChestsModel(Identifier texture) {
		super(getTexturedModelData().createModel(), texture);
		this.base1 = this.getRoot().getChild("base1");
		this.base2 = this.getRoot().getChild("base2");
		this.lid1 = this.getRoot().getChild("lid1");
		this.lid2 = this.getRoot().getChild("lid2");
	}

	@Override
	public void animateModel(MinecartModule module, float limbAngle, float limbDistance, float tickDelta) {
		ExtractingChestsModule ecm = ((ExtractingChestsModule) module);
		this.lid1.pitch = this.lid2.pitch = ecm.getRotationOffset(tickDelta) * -1.5707964f;
		this.base1.pivotZ = this.base2.pivotZ = ecm.getTranslationOffset(tickDelta);
		this.lid1.pivotZ = this.lid2.pivotZ = this.base1.pivotZ + 16.0F;
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		for (boolean opposite : new boolean[] { true, false }) {
			modelPartData.addChild("base" + (opposite ? 2 : 1), ModelPartBuilder.create().uv(0, 17).cuboid(8.0f, 3.0f, 2.0f, 16, 6, 14), ModelTransform.of(-16.0f, -5.5f, -14.0f, 0, opposite ? 3.1415927f : 0, 0));
			modelPartData.addChild("lid" + (opposite ? 2 : 1), ModelPartBuilder.create().uv(0, 0).cuboid(8.0f, -3.0f, -14.0f, 16, 3, 14), ModelTransform.of(-16.0f, -1.5f, 2.0f, 0, opposite ? 3.1415927f : 0, 0));
			modelPartData.addChild("lock" + (opposite ? 2 : 1), ModelPartBuilder.create().uv(0, 37).cuboid(1.0f, 1.5f, 0.5f, 2, 3, 1), ModelTransform.of(14.0f, -3.0f, -15.5f, 0, opposite ? 3.1415927f : 0, 0));
		}
		return TexturedModelData.of(modelData, 64, 32);
	}
}
