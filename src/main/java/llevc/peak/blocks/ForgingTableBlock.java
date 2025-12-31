package llevc.peak.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.BlockState;
import net.minecraft.block.CraftingTableBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.*;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ForgingTableBlock extends CraftingTableBlock {
    public static final MapCodec<ForgingTableBlock> CODEC = createCodec(ForgingTableBlock::new);
    private static final Text SCREEN_TITLE = Text.translatable("container.peak.forge");

    @Override
    public MapCodec<ForgingTableBlock> getCodec() {
        return CODEC;
    }

    public ForgingTableBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
        return new SimpleNamedScreenHandlerFactory(
                (syncId, inventory, player) -> new ForgingTableScreenHandler(syncId, inventory, ScreenHandlerContext.create(world, pos)), SCREEN_TITLE
        );
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient()) {
            player.openHandledScreen(state.createScreenHandlerFactory(world, pos));
        }

        return ActionResult.SUCCESS;
    }
}
