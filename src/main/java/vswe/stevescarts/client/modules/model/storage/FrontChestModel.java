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
import vswe.stevescarts.modules.storage.chest.FrontChestModule;

public class FrontChestModel extends ModuleModel {
	private final ModelPart lid;
	private final ModelPart lock;

	public FrontChestModel(Identifier texture) {
		super(getTexturedModelData().createModel(), texture);
		this.root.setPivot(-3.5f, 0.0f, 0.0f);
		this.root.setAngles(0.0f, 1.5707964f, 0.0f);
		this.lid = this.root.getChild("lid");
		this.lock = this.root.getChild("lock");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		modelPartData.addChild("base", ModelPartBuilder.create().uv(0, 11).cuboid(7.0f, 3.0f, 4.0f, 14, 6, 8), ModelTransform.pivot(-14.0f, -5.5f, -18.5f));
		modelPartData.addChild("lid", ModelPartBuilder.create().uv(0, 0).cuboid(7.0f, -3.0f, -8.0f, 14, 3, 8), ModelTransform.pivot(-14.0f, -1.5f, -6.5f));
		modelPartData.addChild("lock", ModelPartBuilder.create().uv(0, 0).cuboid(1.0f, 1.5f, 0.5f, 2, 3, 1), ModelTransform.pivot(-12.0f, -3.0f, -9.5f));
		return TexturedModelData.of(modelData, 64, 32);
	}

	@Override
	public void animateModel(MinecartModule module, float limbAngle, float limbDistance, float tickDelta) {
		super.animateModel(module, limbAngle, limbDistance, tickDelta);
		this.lid.pitch = this.lock.pitch = ((FrontChestModule) module).getOpenProgress(tickDelta) * 1.5707964f;
	}
}
