package llevc.peak.items;

import llevc.peak.ModComponents;
import llevc.peak.ThePeakExpansion;
import llevc.peak.modular.ModularMaterial;
import llevc.peak.modular.ModularMaterials;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import javax.xml.crypto.Data;
import java.util.Optional;

public class ModularSword extends Item {

    public ModularSword(Settings settings) {
        super(settings);
    }

    @Override
    public void onCraft(ItemStack stack, World world) {
        ModComponents.ModularComponent.Modular ayo = stack.getComponents().get(ModComponents.ModularComponent.ModularComponentType);
        Optional<ModularMaterial> sup = ModularMaterials.find(ayo.material());
        Optional<ModularMaterial> shup = ModularMaterials.find(ayo.rod());
        ThePeakExpansion.LOGGER.info(ayo.material());
        ThePeakExpansion.LOGGER.info(ayo.rod());
        if (sup.isPresent() && shup.isPresent()) {
            AttributeModifiersComponent yoShat = AttributeModifiersComponent.builder()
                    .add(
                            EntityAttributes.ATTACK_DAMAGE,
                            new EntityAttributeModifier(Identifier.ofVanilla("base_attack_damage"), (sup.get().swordDamage+shup.get().swordDamage), EntityAttributeModifier.Operation.ADD_VALUE),
                            AttributeModifierSlot.MAINHAND
                    )
                    .add(
                            EntityAttributes.ATTACK_SPEED,
                            new EntityAttributeModifier(Identifier.ofVanilla("base_attack_speed"), -2.4f, EntityAttributeModifier.Operation.ADD_VALUE),
                            AttributeModifierSlot.MAINHAND
                    ).build();
            stack.set(DataComponentTypes.MAX_DAMAGE,(sup.get().Durability+shup.get().Durability));
            stack.set(DataComponentTypes.ATTRIBUTE_MODIFIERS,yoShat);
        }
    }
}
