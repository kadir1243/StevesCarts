package vswe.stevescarts.client;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import vswe.stevescarts.client.modules.ModuleRenderDispatcher;

public class StevesCartsClient implements ClientModInitializer {
	private static ModuleRenderDispatcher moduleRenderDispatcher;

	@Override
	public void onInitializeClient() {
		moduleRenderDispatcher = new ModuleRenderDispatcher(MinecraftClient.getInstance().textRenderer, MinecraftClient.getInstance().getItemRenderer());
	}

	public static ModuleRenderDispatcher getModuleRenderDispatcher() {
		return moduleRenderDispatcher;
	}
}
