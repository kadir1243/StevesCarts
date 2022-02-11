package vswe.stevescarts.block;

import net.minecraft.block.AbstractRailBlock;
import net.minecraft.block.enums.RailShape;
import net.minecraft.state.property.Property;

public class AdvancedDetectorRailBlock extends AbstractRailBlock {
	protected AdvancedDetectorRailBlock(Settings settings) {
		super(false, settings);
	}

	@Override
	public Property<RailShape> getShapeProperty() {
		return null;
	}
}
