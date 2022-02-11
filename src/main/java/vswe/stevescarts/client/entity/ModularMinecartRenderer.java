package vswe.stevescarts.client.entity;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import vswe.stevescarts.client.StevesCartsClient;
import vswe.stevescarts.client.modules.ModuleRenderDispatcher;
import vswe.stevescarts.entity.ModularMinecartEntity;
import vswe.stevescarts.modules.MinecartModule;

public class ModularMinecartRenderer extends EntityRenderer<ModularMinecartEntity> {
	public ModularMinecartRenderer(EntityRendererFactory.Context ctx) {
		super(ctx);
	}

	@Override
	public Identifier getTexture(ModularMinecartEntity entity) {
		return null;
	}

    // TODO
    @Override
    public void render(ModularMinecartEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
		for (MinecartModule module : entity.getModuleList()) {
			StevesCartsClient.getModuleRenderDispatcher().render(module, yaw, tickDelta, matrices, vertexConsumers, light);
		}
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

	public static void renderAsItem(ItemStack stack, ModelTransformation.Mode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {

	}
}
