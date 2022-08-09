package vswe.stevescarts.module.engine;

import io.github.cottonmc.cotton.gui.networking.NetworkSide;
import io.github.cottonmc.cotton.gui.networking.ScreenNetworking;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import org.jetbrains.annotations.NotNull;
import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.entity.CartEntity;
import vswe.stevescarts.module.CartModule;
import vswe.stevescarts.module.Configurable;
import vswe.stevescarts.module.ModuleType;
import vswe.stevescarts.screen.CartHandler;
import vswe.stevescarts.screen.widget.WPriorityButton;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.Property;
import net.minecraft.util.Identifier;

public abstract class EngineModule extends CartModule implements Configurable, Comparable<EngineModule> {
	public static final int HIGH_PRIORITY = 0;
	public static final int MEDIUM_PRIORITY = 1;
	public static final int LOW_PRIORITY = 2;
	public static final int DISABLED = 3;
	protected final Property priority = Property.create();
	protected boolean propelling = false;

	protected EngineModule(CartEntity minecart, ModuleType<?> type) {
		super(minecart, type);
	}

	@Override
	public void writeToNbt(NbtCompound nbt) {
		nbt.putInt("Priority", this.getPriority());
		super.writeToNbt(nbt);
	}

	@Override
	public void readFromNbt(NbtCompound nbt) {
		this.setPriority(nbt.getByte("Priority"));
		super.readFromNbt(nbt);
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

	public void addPriorityButton(CartHandler handler, WPlainPanel panel, int x, int y) {
		WPriorityButton priorityButton = new WPriorityButton(this::getPriority);
		panel.add(priorityButton, x, y, 16, 16);
		priorityButton.setOnClick(() -> {
			ScreenNetworking.of(handler, NetworkSide.CLIENT).send(priorityPacketId(this.getDiscriminator()), buf -> {});
			this.cyclePriority();
		});
		ScreenNetworking.of(handler, NetworkSide.SERVER).receive(priorityPacketId(this.getDiscriminator()), buf -> this.cyclePriority());
	}

	// Used to disambiguate priority packets
	protected abstract String getDiscriminator();

	@Override
	public int compareTo(@NotNull EngineModule o) {
		return -Integer.compare(o.getPriority(), this.getPriority());
	}

	protected static Identifier priorityPacketId(String discriminator) {
		return StevesCarts.id("priority_" + discriminator);
	}
}
