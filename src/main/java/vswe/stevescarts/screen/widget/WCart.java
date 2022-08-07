package vswe.stevescarts.screen.widget;

import java.util.List;
import java.util.function.Supplier;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.cottonmc.cotton.gui.client.ScreenDrawing;
import io.github.cottonmc.cotton.gui.widget.WWidget;
import vswe.stevescarts.entity.CartEntity;
import vswe.stevescarts.module.ModuleType;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.math.floatprovider.FloatSupplier;
import net.minecraft.world.World;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class WCart extends WWidget {
	public static boolean renderingCart = false;
	private final Supplier<List<ModuleType<?>>> modules;
	private final FloatSupplier roll;
	private final FloatSupplier yaw;
	private final Supplier<World> worldSupplier;
	private CartEntity entity = null;

	public WCart(Supplier<List<ModuleType<?>>> modules, int width, int height, FloatSupplier roll, FloatSupplier yaw, Supplier<World> worldSupplier) {
		this.modules = modules;
		this.roll = roll;
		this.yaw = yaw;
		this.worldSupplier = worldSupplier;
		this.setSize(width, height);
		refresh();
	}

	public void refresh() {
		this.entity = new CartEntity(this.worldSupplier.get(), this.modules.get());
	}

	@Environment(EnvType.CLIENT)
	@Override
	public void paint(MatrixStack matrices, int x, int y, int mouseX, int mouseY) {
		float f = (float)Math.atan(mouseX / 40.0f);
		float g = (float)Math.atan(mouseY / 40.0f);
		ScreenDrawing.coloredRect(matrices, x, y, this.getWidth(), this.getHeight(), 0xFF303030);
		ScreenDrawing.coloredRect(matrices, x + 2, y + 2, this.getWidth() - 4, this.getHeight() - 4, 0xFF000000);
		MatrixStack matrixStack = RenderSystem.getModelViewStack();
		matrixStack.push();
		matrixStack.translate(x + width/2, y + height/1.5f, 1050.0);
		matrixStack.scale(1.0f, 1.0f, -1.0f);
		entity.setYaw(180.0f + f * 40.0f);
		entity.setPitch(-g * 20.0f);
		RenderSystem.applyModelViewMatrix();
		MatrixStack matrixStack2 = new MatrixStack();
		matrixStack2.translate(0.0, 0.0, 1000.0);
		matrixStack2.scale(50, 50, 50);
		Quaternion quaternion = Vec3f.POSITIVE_Z.getDegreesQuaternion(180.0f);
		Quaternion quaternion2 = Vec3f.POSITIVE_X.getDegreesQuaternion(g * 20.0f);
		quaternion.hamiltonProduct(quaternion2);
		matrixStack2.multiply(quaternion);
		DiffuseLighting.method_34742();
		EntityRenderDispatcher entityRenderDispatcher = MinecraftClient.getInstance().getEntityRenderDispatcher();
		quaternion2.conjugate();
		entityRenderDispatcher.setRotation(quaternion2);
		entityRenderDispatcher.setRenderShadows(false);
		VertexConsumerProvider.Immediate immediate = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();
		RenderSystem.runAsFancy(() -> entityRenderDispatcher.render(entity, 0.0, 0.0, 0.0, 0.0f, 1.0f, matrixStack2, immediate, 0xF000F0));
		immediate.draw();
		entityRenderDispatcher.setRenderShadows(true);
		matrixStack.pop();
		RenderSystem.applyModelViewMatrix();
		DiffuseLighting.enableGuiDepthLighting();
	}
}
