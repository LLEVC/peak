package llevc.peak;

import llevc.peak.blocks.CarrierBlock;
import llevc.peak.forging.ForgingTableBlock;
import net.minecraft.block.*;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class ModBlocks {
    private static Block register(String name, Function<AbstractBlock.Settings, Block> blockFactory, AbstractBlock.Settings settings, boolean shouldRegisterItem) {
        RegistryKey<Block> blockKey = keyOfBlock(name);
        Block block = blockFactory.apply(settings.registryKey(blockKey));
        if (shouldRegisterItem) {
            RegistryKey<Item> itemKey = keyOfItem(name);
            BlockItem blockItem = new BlockItem(block, new Item.Settings().registryKey(itemKey).useBlockPrefixedTranslationKey());
            Registry.register(Registries.ITEM, itemKey, blockItem);
        }
        return Registry.register(Registries.BLOCK, blockKey, block);
    }

    private static RegistryKey<Block> keyOfBlock(String name) {
        return RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(ThePeakExpansion.MOD_ID, name));
    }

    private static RegistryKey<Item> keyOfItem(String name) {
        return RegistryKey.of(RegistryKeys.ITEM, Identifier.of(ThePeakExpansion.MOD_ID, name));
    }

    public static void initialize () {
    }

    public static final Block TestBlock = register(
            "test_block",
            Block::new,
            AbstractBlock.Settings.create()
                    .sounds(BlockSoundGroup.GRASS),
            true
    );

    public static final Block ForgingTable = register(
            "forging_table",
            ForgingTableBlock::new,
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.STONE_GRAY)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.5F)
                    .sounds(BlockSoundGroup.WOOD)
                    .burnable(),
            true
    );

    public static final Block Carrier = register(
            "carrier",
            CarrierBlock::new,
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.DARK_GREEN)
                    .breakInstantly()
                    .noCollision()
                    .sounds(BlockSoundGroup.SPORE_BLOSSOM)
                    .pistonBehavior(PistonBehavior.DESTROY),
            true
    );

    //anvils
    public static final Block LightAnvil = register(
            "light_anvil",
            AnvilBlock::new,
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.IRON_GRAY)
                    .requiresTool()
                    .strength(1.25f, 300f)
                    .sounds(BlockSoundGroup.ANVIL)
                    .pistonBehavior(PistonBehavior.NORMAL),
            true
    );
    public static final Block ReinforcedAnvil = register(
            "reinforced_anvil",
            AnvilBlock::new,
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.IRON_GRAY)
                    .requiresTool()
                    .strength(5.0F, 1200.0F)
                    .sounds(BlockSoundGroup.ANVIL)
                    .pistonBehavior(PistonBehavior.BLOCK),
            true
    );
    public static final Block NetheriteAnvil = register(
            "netherite_anvil",
            AnvilBlock::new,
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.IRON_GRAY)
                    .requiresTool()
                    .strength(5.0F, 1200.0F)
                    .sounds(BlockSoundGroup.ANVIL)
                    .pistonBehavior(PistonBehavior.BLOCK),
            true
    );
}
