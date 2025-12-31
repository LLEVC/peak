package llevc.peak;

import llevc.peak.items.ModularItem;
import llevc.peak.items.PrideItem;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.*;
import net.minecraft.potion.Potion;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Unit;

import java.util.function.Function;

public class ModItems {
    public static Item register(String name, Function<Item.Settings, Item> itemFactory, Item.Settings settings) {
        // Create the item key.
        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(ThePeakExpansion.MOD_ID, name));

        // Create the item instance.
        Item item = itemFactory.apply(settings.registryKey(itemKey));

        // Register the item.
        Registry.register(Registries.ITEM, itemKey, item);

        return item;
    }

    public static void initialize () {
        // Register the group.
        Registry.register(Registries.ITEM_GROUP, ItemGroupKey, PeakItemGroup);

        // Register items to the custom item group.
        ItemGroupEvents.modifyEntriesEvent(ItemGroupKey).register(itemGroup -> {
            itemGroup.add(TestItem);
            itemGroup.add(ModBlocks.TestBlock.asItem());
            itemGroup.add(ModBlocks.ForgingTable.asItem());
            itemGroup.add(ModBlocks.LightAnvil.asItem());
            itemGroup.add(ModBlocks.ReinforcedAnvil.asItem());
            itemGroup.add(ModBlocks.NetheriteAnvil.asItem());
            itemGroup.add(ModBlocks.Carrier.asItem());
            itemGroup.add(Pride);
        });
    }

    public static final Item TestItem = register(
            "test",
            Item::new,
            new Item.Settings()
    );

    //potio
    public static final RegistryEntry.Reference<Potion> milk = Registry.registerReference(
            Registries.POTION,
            Identifier.of(ThePeakExpansion.MOD_ID,"milk"),
            new Potion(
                    "milk",
                    new StatusEffectInstance(ModEffects.MilkedUp,1,0)
            )
    );

    //sins
    public static final Item Pride = register(
            "pride",
            PrideItem::new,
            new Item.Settings()
                    .attributeModifiers(AttributeModifiersComponent.builder()
                            .add(
                                    EntityAttributes.ATTACK_DAMAGE,
                                    new EntityAttributeModifier(Identifier.ofVanilla("base_attack_damage"), 11.0, EntityAttributeModifier.Operation.ADD_VALUE),
                                    AttributeModifierSlot.MAINHAND
                            )
                            .add(
                                    EntityAttributes.ATTACK_SPEED,
                                    new EntityAttributeModifier(Identifier.ofVanilla("base_attack_speed"), -3.0F, EntityAttributeModifier.Operation.ADD_VALUE),
                                    AttributeModifierSlot.MAINHAND
                            )
                            .build()
                    )
                    .maxDamage(100)
                    .component(DataComponentTypes.UNBREAKABLE, Unit.INSTANCE)
    );

    //template
    //lowk might scrap
    public static final Item ModularSwordItem = register(
            "modular_sword",
            ModularItem::new,
            new Item.Settings().sword(ToolMaterial.WOOD,3f,-2.4f)
    );
    public static final Item ModularPickaxeItem = register(
            "modular_pickaxe",
            ModularItem::new,
            new Item.Settings().sword(ToolMaterial.WOOD,3f,-2.4f)
    );
    public static final Item ModularAxeItem = register(
            "modular_axe",
            ModularItem::new,
            new Item.Settings().sword(ToolMaterial.WOOD,3f,-2.4f)
    );
    public static final Item ModularShovelItem = register(
            "modular_shovel",
            ModularItem::new,
            new Item.Settings().sword(ToolMaterial.WOOD,3f,-2.4f)
    );
    public static final Item ModularHoeItem = register(
            "modular_hoe",
            ModularItem::new,
            new Item.Settings().sword(ToolMaterial.WOOD,3f,-2.4f)
    );

    //the item group lol
    public static final RegistryKey<ItemGroup> ItemGroupKey = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(ThePeakExpansion.MOD_ID, "item_group"));
    public static final ItemGroup PeakItemGroup = FabricItemGroup.builder()
            .icon(() -> new ItemStack(Items.BLAZE_ROD))
            .displayName(Text.translatable("itemGroup.peak"))
            .build();
}
