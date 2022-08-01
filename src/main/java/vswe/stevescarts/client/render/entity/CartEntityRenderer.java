package vswe.stevescarts.client.render.entity;

import vswe.stevescarts.entity.CartEntity;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;

public class CartEntityRenderer extends EntityRenderer<CartEntity> {
	public CartEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx);
	}

	@Override
	public Identifier getTexture(CartEntity entity) {
		return null;
	}

	@Override
	public void render(CartEntity entity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
		super.render(entity, f, g, matrixStack, vertexConsumerProvider, i);
		matrixStack.push();
		long l = (long) entity.getId() * 493286711L;
		l = l * l * 4392167121L + l * 98761L;
		float h = (((float)(l >> 16 & 7L) + 0.5f) / 8.0f - 0.5f) * 0.004f;
		float j = (((float)(l >> 20 & 7L) + 0.5f) / 8.0f - 0.5f) * 0.004f;
		float k = (((float)(l >> 24 & 7L) + 0.5f) / 8.0f - 0.5f) * 0.004f;
		matrixStack.translate(h, j, k);
		double renderX = MathHelper.lerp(g, entity.lastRenderX, entity.getX());
		double renderY = MathHelper.lerp(g, entity.lastRenderY, entity.getY());
		double renderZ = MathHelper.lerp(g, entity.lastRenderZ, entity.getZ());
		Vec3d vec3d = entity.snapPositionToRail(renderX, renderY, renderZ);
		float renderPitch = MathHelper.lerp(g, entity.prevPitch, entity.getPitch());
		if (vec3d != null) {
			Vec3d vec3d2 = entity.snapPositionToRailWithOffset(renderX, renderY, renderZ, 0.3f);
			Vec3d vec3d3 = entity.snapPositionToRailWithOffset(renderX, renderY, renderZ, -0.3f);
			if (vec3d2 == null) {
				vec3d2 = vec3d;
			}
			if (vec3d3 == null) {
				vec3d3 = vec3d;
			}
			matrixStack.translate(vec3d.x - renderX, (vec3d2.y + vec3d3.y) / 2.0 - renderY, vec3d.z - renderZ);
			Vec3d vec3d4 = vec3d3.add(-vec3d2.x, -vec3d2.y, -vec3d2.z);
			if (vec3d4.length() != 0.0) {
				vec3d4 = vec3d4.normalize();
				f = (float)(Math.atan2(vec3d4.z, vec3d4.x) * 180.0 / Math.PI);
				renderPitch = (float)(Math.atan(vec3d4.y) * 73.0);
			}
		}
		matrixStack.translate(0.0, 0.375, 0.0);
		matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180.0f - f));
		matrixStack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(-renderPitch));
		float p = (float) entity.getDamageWobbleTicks() - g;
		float q = entity.getDamageWobbleStrength() - g;
		if (q < 0.0f) {
			q = 0.0f;
		}
		if (p > 0.0f) {
			matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(MathHelper.sin(p) * p * q / 10.0f * (float) entity.getDamageWobbleSide()));
		}
		matrixStack.scale(-1.0f, -1.0f, 1.0f);
//		this.model.setAngles(entity, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f); TODO
//		VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(this.model.getLayer(this.getTexture(entity)));
//		this.model.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
		matrixStack.pop();
	}
}
