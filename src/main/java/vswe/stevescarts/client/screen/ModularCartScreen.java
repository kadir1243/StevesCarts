package vswe.stevescarts.client.screen;

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import vswe.stevescarts.screen.CartAssemblerHandler;
import vswe.stevescarts.screen.ModularCartHandler;

@Environment(EnvType.CLIENT)
public class ModularCartScreen extends CottonInventoryScreen<ModularCartHandler> {
	public ModularCartScreen(ModularCartHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title);
	}
}
