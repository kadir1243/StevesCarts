package vswe.stevescarts.module.addon;

public interface Toggleable {
	boolean isActive();

	default int getState() {
		return isActive() ? 1 : 0;
	}
}
