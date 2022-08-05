package vswe.stevescarts.module.storage;

import vswe.stevescarts.entity.CartEntity;
import vswe.stevescarts.module.CartModule;
import vswe.stevescarts.module.Configurable;
import vswe.stevescarts.module.ModuleType;

public abstract class StorageModule extends CartModule implements Configurable {
	protected StorageModule(CartEntity minecart, ModuleType<?> type) {
		super(minecart, type);
	}
}
