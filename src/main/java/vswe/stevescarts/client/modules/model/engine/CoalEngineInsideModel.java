package vswe.stevescarts.client.modules.model.engine;

import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import vswe.stevescarts.StevesCarts;

public class CoalEngineInsideModel extends AbstractCoalEngineModel {
	public static final Identifier[] FIRE_TEXTURES = Util.make(new Identifier[4], textures -> {
		for (int i = 0; i < textures.length; i++) {
			textures[i] = StevesCarts.id("textures/modules/engine/engine_fire" + (i + 1) + ".png");
		}
	});
	private final Identifier[] textures;
	private final Identifier otherTexture;

	public CoalEngineInsideModel(Identifier[] textures, Identifier otherTexture) {
		super(getTexturedModelData().createModel(), otherTexture);
		this.textures = textures;
		this.otherTexture = otherTexture;
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		modelPartData.addChild("back", ModelPartBuilder.create().uv(0, 0).cuboid(-3.5f, -2.0f, 0.0f, 7, 4, 0), ModelTransform.pivot(0.0f, -0.5f, 0.3f));
		return TexturedModelData.of(modelData, 8, 4);
	}

	public final void render(int stage, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, float red, float green, float blue, float alpha) {
		if (stage < 0) {
			super.render(matrices, vertexConsumers.getBuffer(this.getLayer(this.otherTexture)), light, overlay, red, green, blue, alpha);
		} else {
			super.render(matrices, vertexConsumers.getBuffer(this.getLayer(this.textures[stage])), light, overlay, red, green, blue, alpha);
		}
	}
}
