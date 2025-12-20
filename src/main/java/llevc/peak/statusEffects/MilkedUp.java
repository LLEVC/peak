package llevc.peak.statusEffects;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.InstantStatusEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.server.world.ServerWorld;
import org.jetbrains.annotations.Nullable;

public class MilkedUp extends InstantStatusEffect {
    public MilkedUp(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void applyInstantEffect(ServerWorld world, @Nullable Entity effectEntity, @Nullable Entity attacker, LivingEntity target, int amplifier, double proximity) {
        target.clearStatusEffects();
    }
}
