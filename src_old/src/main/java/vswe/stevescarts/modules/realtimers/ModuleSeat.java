package vswe.stevescarts.modules.realtimers;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import vswe.stevescarts.entitys.EntityMinecartModular;
import vswe.stevescarts.guis.GuiMinecart;
import vswe.stevescarts.helpers.Localization;
import vswe.stevescarts.helpers.ResourceHelper;
import vswe.stevescarts.modules.ModuleBase;

public class ModuleSeat extends ModuleBase {
	private int[] buttonRect;
	private boolean relative;
	private float chairAngle;

	public ModuleSeat(final EntityMinecartModular cart) {
		super(cart);
		buttonRect = new int[] { 20, 20, 24, 12 };
	}

	@Override
	public boolean hasSlots() {
		return false;
	}

	@Override
	public boolean hasGui() {
		return true;
	}

	@Override
	public int guiWidth() {
		return 55;
	}

	@Override
	public int guiHeight() {
		return 35;
	}

	@Override
	public void drawForeground(final GuiMinecart gui) {
		drawString(gui, getModuleName(), 8, 6, 4210752);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void drawBackground(final GuiMinecart gui, final int x, final int y) {
		ResourceHelper.bindResource("/gui/chair.png");
		final int imageID = getState();
		int borderID = 0;
		if (inRect(x, y, buttonRect)) {
			if (imageID == 0) {
				borderID = 2;
			} else {
				borderID = 1;
			}
		}
		drawImage(gui, buttonRect, 0, buttonRect[3] * borderID);
		final int srcY = buttonRect[3] * 3 + imageID * (buttonRect[3] - 2);
		drawImage(gui, buttonRect[0] + 1, buttonRect[1] + 1, 0, srcY, buttonRect[2] - 2, buttonRect[3] - 2);
	}

	@Override
	public void drawMouseOver(final GuiMinecart gui, final int x, final int y) {
		drawStringOnMouseOver(gui, getStateName(), x, y, buttonRect);
	}

	private int getState() {
		if (getCart().getCartRider() == null) {
			return 1;
		}
		if (getCart().getCartRider() == getClientPlayer()) {
			return 2;
		}
		return 0;
	}

	private String getStateName() {
		return Localization.MODULES.ATTACHMENTS.SEAT_MESSAGE.translate(String.valueOf(getState()));
	}

	@Override
	public void mouseClicked(final GuiMinecart gui, final int x, final int y, final int button) {
		if (button == 0 && inRect(x, y, buttonRect)) {
			sendPacket(0);
		}
	}

	@Override
	protected void receivePacket(final int id, final byte[] data, final EntityPlayer player) {
		if (id == 0 && player != null) {
			if (getCart().getCartRider() == null) {
				player.startRiding(getCart());
			} else if (getCart().getCartRider() == player) {
				player.dismountRidingEntity();
			}
		}
	}

	@Override
	public int numberOfPackets() {
		return 1;
	}

	@Override
	public void update() {
		super.update();
		if (getCart().getCartRider() != null) {
			relative = false;
			chairAngle = (float) (3.141592653589793 + 3.141592653589793 * getCart().getCartRider().rotationYaw / 180.0);
		} else {
			relative = true;
			chairAngle = 1.5707964f;
		}
	}

	public float getChairAngle() {
		return chairAngle;
	}

	public boolean useRelativeRender() {
		return relative;
	}

	@Override
	public float mountedOffset(final Entity rider) {
		return -0.1f;
	}
}
