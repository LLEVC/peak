package llevc.peak.modular;

import llevc.peak.ThePeakExpansion;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class ModularMaterials {
    //Materials
    public static final ModularMaterial Netherite = new ModularMaterial(7f,5f,9f,2030, ToolMaterial.NETHERITE);
    public static final ModularMaterial Diamond = new ModularMaterial(6f,4f,8f,1560,ToolMaterial.DIAMOND);
    public static final ModularMaterial Gold = new ModularMaterial(3f,1f,6f,31,ToolMaterial.GOLD);
    public static final ModularMaterial Iron = new ModularMaterial(5f,3f,8f,249,ToolMaterial.IRON);
    public static final ModularMaterial Copper = new ModularMaterial(4f,2f,8f,189,ToolMaterial.COPPER);
    public static final ModularMaterial Stone = new ModularMaterial(4f,2f,8f,130,ToolMaterial.STONE);
    public static final ModularMaterial Wooden = new ModularMaterial(3f,1f,6f,58,ToolMaterial.WOOD);
    //Rods
    public static final ModularMaterial Stick = new ModularMaterial(0f,0f,0f,1,0,0,ToolMaterial.WOOD);

    //Lists
    public static List<List<?>> itemList = List.of(
            List.of("minecraft:netherite_ingot",Netherite),
            List.of("minecraft:diamond",Diamond),
            List.of("minecraft:gold_ingot",Gold),
            List.of("minecraft:iron_ingot",Iron),
            List.of("minecraft:copper_ingot",Copper),
            List.of("minecraft:stick",Stick)
    );
    public static List<List<?>> tagList = List.of(
            List.of("minecraft:wooden_tool_materials",Wooden),
            List.of("minecraft:stone_tool_materials",Stone)
    );

    public void register(String name, ModularMaterial material, boolean Tag) {
        if (Tag) {
            tagList.add(List.of(name,material));
        } else {
            itemList.add(List.of(name,material));
        }
    }

    public void register(String name, ModularMaterial material) {
        register(name,material,false);
    }

    private static Optional<ModularMaterial> search(List<List<?>> list, String name, boolean tag) {
        ModularMaterial target = null;
        Iterator<List<?>> yochat = list.iterator();
        while (yochat.hasNext()) {
            List<?> ayo = yochat.next();
            if (tag) {
                ThePeakExpansion.LOGGER.info("tag");
                TagKey<Item> heyo = TagKey.of(RegistryKeys.ITEM, Identifier.of((String) ayo.getFirst()));
                Item nayo = Registries.ITEM.get(Identifier.of(name));
                ThePeakExpansion.LOGGER.info(heyo.toString());
                ThePeakExpansion.LOGGER.info(nayo.toString());
                if (nayo.getDefaultStack().isIn(heyo)) {
                    target = (ModularMaterial) ayo.getLast();
                    break;
                }
            } else {
                ThePeakExpansion.LOGGER.info("no tag");
                if (ayo.getFirst().equals(name)) {
                    target = (ModularMaterial) ayo.getLast();
                    break;
                }
            }
        }
        return Optional.ofNullable(target);
    }

    private static Optional<ModularMaterial> search(List<List<?>> list, String name) {
        return search(list,name,false);
    }

    public static Optional<ModularMaterial> find(String name, boolean tag) {
        Optional<ModularMaterial> found = null;
        if (tag) {
            found = search(tagList,name,true);
        } else {
            found = search(itemList,name);
        }
        return found;
    }

    public static Optional<ModularMaterial> find(String name) {
        if (find(name, false).isEmpty()) {
            return find(name, true);
        } else {
            return find(name, false);
        }
    }
}
