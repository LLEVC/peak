package llevc.peak;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
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
        });
    }

    public static final Item TestItem = register(
            "test",
            Item::new,
            new Item.Settings()
    );

    //the item group lol
    public static final RegistryKey<ItemGroup> ItemGroupKey = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(ThePeakExpansion.MOD_ID, "item_group"));
    public static final ItemGroup PeakItemGroup = FabricItemGroup.builder()
            .icon(() -> new ItemStack(Items.BLAZE_ROD))
            .displayName(Text.translatable("itemGroup.peak"))
            .build();
}
