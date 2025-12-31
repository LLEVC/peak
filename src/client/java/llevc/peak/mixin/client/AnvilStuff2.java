package llevc.peak.mixin.client;

import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.AnvilScreen;
import net.minecraft.client.gui.screen.ingame.ForgingScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilScreen.class)
public class AnvilStuff2 {
    MinecraftClient client = MinecraftClient.getInstance();
    HitResult hit = client.crosshairTarget;
    @ModifyConstant(method = "drawForeground", constant = @Constant(intValue = 40))
    private int mixinLimitInt(int i) {
        if (hit.getType().equals(HitResult.Type.BLOCK)) {
            BlockHitResult blockHit = (BlockHitResult) hit;
            BlockState yuh = client.world.getBlockState(blockHit.getBlockPos());
            if (yuh.isIn(TagKey.of(RegistryKeys.BLOCK,Identifier.of("peak","anvil/tier0")))) {
                return 24;
            } else if (yuh.isIn(TagKey.of(RegistryKeys.BLOCK,Identifier.of("peak","anvil/tier1")))) {
                return 48;
            } else if (yuh.isIn(TagKey.of(RegistryKeys.BLOCK,Identifier.of("peak","anvil/tier2")))) {
                return 64;
            } else if  (yuh.isIn(TagKey.of(RegistryKeys.BLOCK,Identifier.of("peak","anvil/tier3")))) {
                return 256;
            }
            return Integer.MAX_VALUE;
        }
        return Integer.MAX_VALUE;
    }
}
