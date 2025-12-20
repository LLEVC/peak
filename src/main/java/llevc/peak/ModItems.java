package llevc.peak;

import llevc.peak.forging.weaponTypes.ModularSword;
import llevc.peak.items.PrideItem;
import llevc.peak.statusEffects.MilkedUp;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BannerPatternsComponent;
import net.minecraft.component.type.BlocksAttacksComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.potion.Potion;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Optional;
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
    public static final RegistryEntry.Reference<Potion> freak = Registry.registerReference(
            Registries.POTION,
            Identifier.of(ThePeakExpansion.MOD_ID,"freak"),
            new Potion(
                    "freak",
                    new StatusEffectInstance(StatusEffects.INSTANT_HEALTH,1,125)
            )
    );

    //Heavy Material

    //sins
    public static final Item Pride = register(
            "pride",
            PrideItem::new,
            new Item.Settings().sword(ToolMaterial.NETHERITE,6f,-2.4f)
    );

    //template
    //lowk might scrap
    public static final Item ModularSwordItem = register(
            "modular_sword",
            ModularSword::new,
            new Item.Settings().sword(ToolMaterial.WOOD,3f,-2.4f)
    );
    public static final Item ModularPickaxeItem = register(
            "modular_pickaxe",
            ModularSword::new,
            new Item.Settings().sword(ToolMaterial.WOOD,3f,-2.4f)
    );
    public static final Item ModularAxeItem = register(
            "modular_axe",
            ModularSword::new,
            new Item.Settings().sword(ToolMaterial.WOOD,3f,-2.4f)
    );
    public static final Item ModularShovelItem = register(
            "modular_shovel",
            ModularSword::new,
            new Item.Settings().sword(ToolMaterial.WOOD,3f,-2.4f)
    );
    public static final Item ModularHoeItem = register(
            "modular_hoe",
            ModularSword::new,
            new Item.Settings().sword(ToolMaterial.WOOD,3f,-2.4f)
    );

    //the item group lol
    public static final RegistryKey<ItemGroup> ItemGroupKey = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(ThePeakExpansion.MOD_ID, "item_group"));
    public static final ItemGroup PeakItemGroup = FabricItemGroup.builder()
            .icon(() -> new ItemStack(Items.BLAZE_ROD))
            .displayName(Text.translatable("itemGroup.peak"))
            .build();
}
