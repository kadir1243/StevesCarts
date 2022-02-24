package vswe.stevescarts.client.modules.model.engine;

import net.minecraft.client.model.ModelPart;
import net.minecraft.util.Identifier;
import vswe.stevescarts.client.modules.model.ModuleModel;

public abstract class AbstractCoalEngineModel extends ModuleModel {
	public AbstractCoalEngineModel(ModelPart root, Identifier texture) {
		super(root, texture);
		root.setPivot(10.5f, 0.5f, -0.0f);
		root.yaw = -1.5707964f;
	}
}
