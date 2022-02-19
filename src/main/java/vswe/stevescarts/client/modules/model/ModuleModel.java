package vswe.stevescarts.client.modules.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import vswe.stevescarts.modules.MinecartModule;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Environment(EnvType.CLIENT)
public abstract class ModuleModel extends Model {
	protected final Identifier texture;
	protected final ModelPart root;

	public ModuleModel(Function<Identifier, RenderLayer> layerFactory, Identifier texture, ModelPart root) {
		super(layerFactory);
		this.texture = texture;
		this.root = root;
	}

	public ModuleModel(ModelPart root, Identifier texture) {
		this(RenderLayer::getEntityCutoutNoCull, texture, root);
	}

	public final void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, float red, float green, float blue, float alpha) {
		this.root.render(matrices, vertexConsumers.getBuffer(this.getLayer(this.texture)), light, overlay, red, green, blue, alpha);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
		this.root.render(matrices, vertices, light, overlay, red, green, blue, alpha);
	}

	public void animateModel(MinecartModule module, float limbAngle, float limbDistance, float tickDelta) {
	}

	public ModelPart getRoot() {
		return root;
	}
}
