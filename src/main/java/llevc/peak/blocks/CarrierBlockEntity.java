package llevc.peak.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

public class CarrierBlockEntity extends BlockEntity {

    public CarrierBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.Carrier, pos, state);
    }
}
