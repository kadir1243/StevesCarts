package vswe.stevescarts.module;

import org.jetbrains.annotations.NotNull;

public interface Worker extends Comparable<Worker> {
	int LOW_PRIORITY = 0;
	int NORMAL_PRIORITY = 100;
	int HIGHER_PRIORITY = 200;
	int HIGHEST_PRIORITY = 300;

	default int getPriority() {
		return NORMAL_PRIORITY;
	}

	void work();

	@Override
	default int compareTo(@NotNull Worker o) {
		return o.getPriority() - getPriority();
	}
}
