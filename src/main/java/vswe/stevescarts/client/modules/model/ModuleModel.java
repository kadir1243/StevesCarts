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
	private final ModelPart root;
	protected List<ModelPart> models = new ArrayList<>();

	public ModuleModel(Function<Identifier, RenderLayer> layerFactory, ModelPart root) {
		super(layerFactory);
		this.root = root;
	}

	public ModuleModel(ModelPart root) {
		this(RenderLayer::getEntityCutoutNoCull, root);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
		matrices.push();
		MinecraftClient.getInstance().getTextureManager().bindTexture(getTexture());
		this.root.render(matrices, vertices, light, overlay, red, green, blue, alpha);
		matrices.pop();
	}

	public abstract Identifier getTexture();
}
