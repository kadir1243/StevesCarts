package vswe.stevescarts.client.modules.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Environment(EnvType.CLIENT)
public abstract class ModuleModel extends Model {
	protected List<ModelPart> models = new ArrayList<>();

	public ModuleModel(Function<Identifier, RenderLayer> layerFactory) {
		super(layerFactory);
	}

	public ModuleModel() {
		super(RenderLayer::getEntityCutoutNoCull);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
		matrices.push();
		MinecraftClient.getInstance().getTextureManager().bindTexture(getTexture());
		for (ModelPart model : models) {
			model.render(matrices, vertices, light, overlay, red, green, blue, alpha);
		}
		matrices.pop();
	}

	public void addModel(ModelPart model) {
		models.add(model);
	}

	public abstract Identifier getTexture();
}
