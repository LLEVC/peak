package llevc.peak;

import llevc.peak.statusEffects.MilkedUp;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class ModEffects {
    public static final RegistryEntry<StatusEffect> MilkedUp = register("milk", new MilkedUp(StatusEffectCategory.NEUTRAL
            ,16579836));

    private static RegistryEntry<StatusEffect> register(String id, StatusEffect statusEffect) {
        return Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(ThePeakExpansion.MOD_ID,id), statusEffect);
    }
}
