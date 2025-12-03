package llevc.peak.mixin;

import com.google.common.collect.Lists;
import llevc.peak.ModComponents;
import net.minecraft.block.Block;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.MergedComponentMap;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.apache.commons.lang3.function.TriConsumer;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Mixin(ItemStack.class)
public abstract class AnvilTooltipMixin {
    @Shadow public abstract ItemStack copy();

    @Shadow public abstract Item getItem();

    @Unique private boolean DAMN(Identifier id) {
        return Block.getBlockFromItem(this.getItem()).getDefaultState().isIn(TagKey.of(RegistryKeys.BLOCK, id));
    }

    @Inject(at = @At("HEAD"), method = "appendTooltip")
    private void init(Item.TooltipContext context, TooltipDisplayComponent displayComponent, @Nullable PlayerEntity player, TooltipType type, Consumer<Text> textConsumer, CallbackInfo ci) {
        if (DAMN(Identifier.of("peak","anvil/tier0"))) {
            //tier 0
            textConsumer.accept(Text.translatable("item.peak.anvil_cap").append(Text.literal(" 20")).formatted(Formatting.YELLOW));
        } else if (DAMN(Identifier.of("peak","anvil/tier1"))) {
            //tier 1
            textConsumer.accept(Text.translatable("item.peak.anvil_cap").append(Text.literal(" 40")).formatted(Formatting.YELLOW));
        } else if (DAMN(Identifier.of("peak","anvil/tier2"))) {
            //tier 2
            textConsumer.accept(Text.translatable("item.peak.anvil_cap").append(Text.literal(" 60")).formatted(Formatting.YELLOW));
        }
    }
}