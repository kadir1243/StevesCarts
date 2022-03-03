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
	private final ModelPart base;
	private final ModelPart lid;
	private final ModelPart lock;

	public ExtractingChestsModel(Identifier texture) {
		super(getTexturedModelData().createModel(), texture);
		this.base = this.getRoot().getChild("base");
		this.lid = this.getRoot().getChild("lid");
		this.lock = this.getRoot().getChild("lock");
	}

	@Override
	public void animateModel(MinecartModule module, float limbAngle, float limbDistance, float tickDelta) {
		ExtractingChestsModule ecm = ((ExtractingChestsModule) module);
		this.lid.pitch = this.lock.pitch = ecm.getRotationOffset(tickDelta) * -1.5707964f;
		this.base.pivotZ = ecm.getTranslationOffset(tickDelta);
		this.lid.pivotZ = this.lock.pivotZ = this.base.pivotZ + 16.0F;
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		modelPartData.addChild("base", ModelPartBuilder.create().uv(0, 17).cuboid(8.0f, 3.0f, 2.0f, 16, 6, 14), ModelTransform.pivot(-16.0f, -5.5f, -14.0f));
		modelPartData.addChild("lid", ModelPartBuilder.create().uv(0, 0).cuboid(8.0f, -3.0f, -14.0f, 16, 3, 14), ModelTransform.pivot(-16.0f, -1.5f, 2.0f));
		modelPartData.addChild("lock", ModelPartBuilder.create().uv(0, 37).cuboid(1.0f, 1.5f, 0.5f, 2, 3, 1), ModelTransform.pivot(-2.0f, -4.5f, -13.5f));
		return TexturedModelData.of(modelData, 64, 64);
	}
}
