package vswe.stevescarts.client.modules.hull.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.MinecartEntityModel;
import net.minecraft.util.Identifier;
import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.client.modules.model.ModuleModel;

@Environment(EnvType.CLIENT)
public class HullModel extends ModuleModel {
	private final Identifier texture;

	public HullModel(Identifier texture) {
		super(getTexturedModelData().createModel());
		this.texture = texture;
	}

	@Override
	public Identifier getTexture() {
		return texture;
	}

	public static TexturedModelData getTexturedModelData() {
		return MinecartEntityModel.getTexturedModelData();
	}
}
