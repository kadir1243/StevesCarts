package vswe.stevescarts.modules.engine;

import io.github.cottonmc.cotton.gui.networking.NetworkSide;
import io.github.cottonmc.cotton.gui.networking.ScreenNetworking;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.Property;
import org.jetbrains.annotations.NotNull;
import vswe.stevescarts.entity.ModularMinecartEntity;
import vswe.stevescarts.modules.Configurable;
import vswe.stevescarts.modules.MinecartModule;
import vswe.stevescarts.modules.MinecartModuleType;
import vswe.stevescarts.screen.ModularCartHandler;
import vswe.stevescarts.screen.StevesCartsScreenHandlers;
import vswe.stevescarts.screen.widget.WPriorityButton;

public abstract class EngineModule extends MinecartModule implements Configurable, Comparable<EngineModule> {
	public static final int HIGH_PRIORITY = 0;
	public static final int MEDIUM_PRIORITY = 1;
	public static final int LOW_PRIORITY = 2;
	public static final int DISABLED = 3;
	protected final Property priority = Property.create();
	protected boolean propelling = false;

	protected EngineModule(ModularMinecartEntity minecart, MinecartModuleType<?> type) {
		super(minecart, type);
	}

	@Override
	public NbtCompound writeNbt(NbtCompound nbt) {
		nbt.putInt("Priority", this.getPriority());
		return super.writeNbt(nbt);
	}

	@Override
	public void readNbt(NbtCompound nbt) {
		this.setPriority(nbt.getByte("Priority"));
		super.readNbt(nbt);
	}

	@Override
	public abstract boolean canPropel();

	@Override
	public abstract void onPropel();

	public boolean isPropelling() {
		return propelling;
	}

	public void setPropelling(boolean propelling) {
		this.propelling = propelling;
	}

	public int getPriority() {
		return this.priority.get();
	}

	public void setPriority(int priority) {
		this.priority.set(priority);
	}

	protected void cyclePriority() {
		int priority = this.getPriority();
		if (priority == DISABLED) {
			this.setPriority(HIGH_PRIORITY);
		} else {
			this.setPriority((byte) (priority + 1));
		}
	}

	public void addPriorityButton(ModularCartHandler handler, WPlainPanel panel, int x, int y) {
		WPriorityButton priorityButton = new WPriorityButton(this::getPriority);
		panel.add(priorityButton, x, y, 16, 16);
		priorityButton.setOnClick(() -> {
			ScreenNetworking.of(handler, NetworkSide.CLIENT).send(StevesCartsScreenHandlers.priorityPacketId(this.getDiscriminator()), buf -> {});
			this.cyclePriority();
		});
		ScreenNetworking.of(handler, NetworkSide.SERVER).receive(StevesCartsScreenHandlers.priorityPacketId(this.getDiscriminator()), buf -> this.cyclePriority());
	}

	// Used to disambiguate priority packets
	protected abstract String getDiscriminator();

	@Override
	public int compareTo(@NotNull EngineModule o) {
		return -Integer.compare(o.getPriority(), this.getPriority());
	}
}
