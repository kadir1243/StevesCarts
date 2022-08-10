package vswe.stevescarts.client.render.module.model.attachment;

import vswe.stevescarts.client.render.module.model.ModuleModel;
import vswe.stevescarts.module.CartModule;
import vswe.stevescarts.module.attachment.TorchPlacerModule;

import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.util.Identifier;

public class TorchPlacerModel extends ModuleModel {
	private final ModelPart torch0;
	private final ModelPart torch1;
	private final ModelPart torch2;

	public TorchPlacerModel(Identifier texture) {
		super(getTexturedModelData().createModel(), texture);
		this.torch0 = this.getRoot().getChild("torch0");
		this.torch1 = this.getRoot().getChild("torch1");
		this.torch2 = this.getRoot().getChild("torch2");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		modelPartData.addChild("base", ModelPartBuilder.create().uv(0, 0).cuboid(-7.0f, -2.0f, -1.0f, 14, 4, 2), ModelTransform.pivot(0.0f, -2.0f, -9.0f));
		for (int i = -1; i <= 1; ++i) {
			modelPartData.addChild("holder" + (i + 1), ModelPartBuilder.create().uv(0, 6).cuboid(-1.0f, -1.0f, -0.5f, 2, 2, 1), ModelTransform.pivot(i * 4, -2.0f, -10.5f));
			modelPartData.addChild("torch" + (i + 1), ModelPartBuilder.create().uv(0, 9).cuboid(-1.0f, -5.0f, -1.0f, 2, 10, 2), ModelTransform.pivot(i * 4, -2.0f, -12.0f));
		}
		return TexturedModelData.of(modelData, 32, 32);
	}

	@Override
	public void animateModel(CartModule module, float limbAngle, float limbDistance, float tickDelta) {
		if (module.getEntity() == null) {
			this.torch0.visible = true;
			this.torch1.visible = true;
			this.torch2.visible = true;
		}
		TorchPlacerModule t = ((TorchPlacerModule) module);
		this.torch0.visible = t.hasTorch(0);
		this.torch1.visible = t.hasTorch(1);
		this.torch2.visible = t.hasTorch(2);
	}
}
