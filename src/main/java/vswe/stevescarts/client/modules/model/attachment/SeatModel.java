package vswe.stevescarts.client.modules.model.attachment;

import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import vswe.stevescarts.client.modules.model.ModuleModel;
import vswe.stevescarts.modules.MinecartModule;

public class SeatModel extends ModuleModel {
	public SeatModel(Identifier texture) {
		super(getTexturedModelData().createModel(), texture);
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		modelPartData.addChild("sit", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0f, -2.0f, -2.0f, 8, 4, 4), ModelTransform.pivot(0.0f, 1.0f, 0.0f));
		modelPartData.addChild("back", ModelPartBuilder.create().uv(0, 8).cuboid(-4.0f, -2.0f, -1.0f, 8, 12, 2), ModelTransform.pivot(0.0f, -7.0f, 3.0f));
		return TexturedModelData.of(modelData, 32, 32);
	}

	@Override
	public void animateModel(MinecartModule module, float limbAngle, float limbDistance, float tickDelta) {
		super.animateModel(module, limbAngle, limbDistance, tickDelta);
		Entity firstPassenger = module.getMinecart().getFirstPassenger();
		this.root.yaw = 3.1415926F;
		if (firstPassenger != null) {
			this.root.yaw += (float) Math.toRadians(firstPassenger.getYaw(tickDelta));
		} else {
			this.root.yaw /= 2.0f;
		}
	}
}
