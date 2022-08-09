package vswe.stevescarts.client.render.module.model.engine;

import vswe.stevescarts.client.render.module.model.ModuleModel;
import vswe.stevescarts.module.CartModule;
import vswe.stevescarts.module.engine.SolarEngineModule;

import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.util.Identifier;

public class TopSolarPanelsModel extends ModuleModel {
	private final ModelPart[] panels;

	public TopSolarPanelsModel(Identifier texture, int count) {
		super(getTexturedModelData(count).createModel(), texture);
		this.panels = new ModelPart[count];
		for (int i = 0; i < count; i++) {
			this.panels[i] = this.getRoot().getChild("panel" + i);
		}
	}

	public static TexturedModelData getTexturedModelData(int count) {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		for (int i = 0; i < count; i++) {
			float rotation;
			float f;
			switch (i) {
				case 0 -> {
					rotation = 0.0f;
					f = -1.5f;
				}
				case 1 -> {
					rotation = 3.1415927f;
					f = -1.5f;
				}
				case 2 -> {
					rotation = 4.712389f;
					f = -6.0f;
				}
				case 3 -> {
					rotation = 1.5707964f;
					f = -6.0f;
				}
				default -> throw new IllegalArgumentException("Invalid index: " + i);
			}
			modelPartData.addChild("panel" + i, ModelPartBuilder.create().uv(0, 0).cuboid(-6.0f, 0.0f, -2.0f, 12, 13, 2), ModelTransform.of((float) (Math.sin(rotation) * f), -5.0f, (float) (Math.cos(rotation) * f), 0.0f, rotation, 0.0f));
		}
		return TexturedModelData.of(modelData, 32, 16);
	}

	@Override
	public void animateModel(CartModule module, float limbAngle, float limbDistance, float tickDelta) {
		this.root.pivotY = ((SolarEngineModule) module).getTranslation(tickDelta);
		for (ModelPart panel : panels) {
			panel.pitch = -((SolarEngineModule) module).getRotation(tickDelta);
		}
	}
}
