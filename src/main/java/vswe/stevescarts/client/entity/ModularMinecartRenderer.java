package vswe.stevescarts.client.entity;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.profiler.Profiler;
import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.client.StevesCartsClient;
import vswe.stevescarts.entity.ModularMinecartEntity;
import vswe.stevescarts.mixins.AbstractMinecartEntityAccessor;
import vswe.stevescarts.modules.MinecartModule;
import vswe.stevescarts.modules.ModuleStorage;
import vswe.stevescarts.screen.widget.WCart;

import java.util.Collection;
import java.util.Objects;

public class ModularMinecartRenderer extends EntityRenderer<ModularMinecartEntity> {
	public static final ModularMinecartEntity FAKE_ENTITY = Objects.requireNonNull(StevesCarts.MODULAR_MINECART_ENTITY.create(null));

	public ModularMinecartRenderer(EntityRendererFactory.Context ctx) {
		super(ctx);
	}

	@Override
	public Identifier getTexture(ModularMinecartEntity entity) {
		return null;
	}

	@Override
	public void render(ModularMinecartEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
		Profiler profiler = MinecraftClient.getInstance().getProfiler();
		profiler.push("stevescarts:modular_cart");
		super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
		matrices.push();
		long l = (long) entity.getId() * 493286711L;
		l = l * l * 4392167121L + l * 98761L;
		float h = (((float) (l >> 16 & 7L) + 0.5f) / 8.0f - 0.5f) * 0.004f;
		float j = (((float) (l >> 20 & 7L) + 0.5f) / 8.0f - 0.5f) * 0.004f;
		float k = (((float) (l >> 24 & 7L) + 0.5f) / 8.0f - 0.5f) * 0.004f;
		matrices.translate(h, j, k);
		double renderX = MathHelper.lerp(tickDelta, entity.lastRenderX, entity.getX());
		double renderY = MathHelper.lerp(tickDelta, entity.lastRenderY, entity.getY());
		double renderZ = MathHelper.lerp(tickDelta, entity.lastRenderZ, entity.getZ());
		double offset = 0.3f;
		Vec3d vec3d = entity.snapPositionToRail(renderX, renderY, renderZ);
		float pitch = MathHelper.lerp(tickDelta, entity.prevPitch, entity.getPitch());
		if (vec3d != null) {
			Vec3d vec3d2 = entity.snapPositionToRailWithOffset(renderX, renderY, renderZ, offset);
			Vec3d vec3d3 = entity.snapPositionToRailWithOffset(renderX, renderY, renderZ, -offset);
			if (vec3d2 == null) {
				vec3d2 = vec3d;
			}
			if (vec3d3 == null) {
				vec3d3 = vec3d;
			}
			matrices.translate(vec3d.x - renderX, (vec3d2.y + vec3d3.y) / 2.0 - renderY, vec3d.z - renderZ);
			Vec3d vec3d4 = vec3d3.add(-vec3d2.x, -vec3d2.y, -vec3d2.z);
			if (vec3d4.length() != 0.0) {
				vec3d4 = vec3d4.normalize();
				yaw = (float) (Math.atan2(vec3d4.z, vec3d4.x) * 180.0 / Math.PI);
				pitch = (float) (Math.atan(vec3d4.y) * 73.0);
			}
		}
		matrices.translate(0.0, 0.375, 0.0);
		matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180.0f - yaw));
		if (((AbstractMinecartEntityAccessor) entity).isYawFlipped()) {
			matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180.0f));
		}
		matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(-pitch));
		float damageWobbleTicks = (float) entity.getDamageWobbleTicks() - tickDelta;
		float damageWobbleStrength = MathHelper.clamp(entity.getDamageWobbleStrength() - tickDelta, 0, Float.MAX_VALUE);
		if (damageWobbleTicks > 0.0f) {
			matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(MathHelper.sin(damageWobbleTicks) * damageWobbleTicks * damageWobbleStrength / 10.0f * entity.getDamageWobbleSide()));
		}
		matrices.scale(-1.0f, -1.0f, 1.0f);
		for (MinecartModule module : entity.getModuleList()) {
			StevesCartsClient.getModuleRenderDispatcher().render(module, yaw, tickDelta, matrices, vertexConsumers, light, true);
		}
		matrices.pop();
	}

	public static void renderAsItem(ItemStack stack, ModelTransformation.Mode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		matrices.push();
		matrices.scale(-1.0f, -1.0f, 1.0f);
		if (WCart.renderingCart) {
			matrices.scale(4, 4, 4);
		}
		matrices.multiply(Vec3f.POSITIVE_Y.getRadialQuaternion((float) Math.PI));
		matrices.translate(1.0F, 0.17F, 0.0F);
		Collection<MinecartModule> modules = ModuleStorage.read(stack);
		FAKE_ENTITY.modules.clear();
		for (MinecartModule module : modules) {
			FAKE_ENTITY.addModule(module, false);
			module.setMinecart(FAKE_ENTITY);
		}
		for (MinecartModule module : modules) {
			StevesCartsClient.getModuleRenderDispatcher().render(module, 0, 0, matrices, vertexConsumers, light, false);
		}
		matrices.pop();
	}
}
