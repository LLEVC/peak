package llevc.peak.mixin;

import llevc.peak.ModItems;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.passive.AbstractCowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractCowEntity.class)
public class MilkMixin {
    @Inject(method = "interactMob",at = @At("HEAD"))
    public void init(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        ItemStack whatsinhishanndddd = player.getStackInHand(hand);
        if (whatsinhishanndddd.isOf(Items.GLASS_BOTTLE)) {
            player.playSound(SoundEvents.ENTITY_COW_MILK,1,1);
            player.setStackInHand(hand, ItemUsage.exchangeStack(whatsinhishanndddd,player, PotionContentsComponent.createStack(Items.POTION,RegistryEntry.of(ModItems.milk.value()))));
            cir.setReturnValue(ActionResult.SUCCESS);
        }
    }
}
