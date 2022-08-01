package vswe.stevescarts.module.hull;

import org.jetbrains.annotations.Nullable;
import vswe.stevescarts.entity.CartEntity;
import vswe.stevescarts.module.CartModule;
import vswe.stevescarts.module.ModuleType;

public class HullModule extends CartModule {
	public HullModule(@Nullable CartEntity entity, ModuleType<?> type) {
		super(entity, type);
	}
}
