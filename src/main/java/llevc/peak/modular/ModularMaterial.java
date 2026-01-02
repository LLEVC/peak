package llevc.peak.modular;

import net.minecraft.block.Block;
import net.minecraft.component.ComponentChanges;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.TagKey;

import java.util.Objects;

public class ModularMaterial {
    public float swordDamage = 6f;
    public float pickaxeDamage = 4f;
    public float axeDamage = 7f;
    public int Durability = 2564;
    public float speed = 2f;
    public int enchantability = 15;
    public ToolMaterial toolMaterial = ToolMaterial.WOOD;
    public ComponentChanges componentChanges = ComponentChanges.EMPTY;
    public ModularMaterial(float swordDamage, float pickaxeDamage, float axeDamage, int Durability, int speed, int enchantability, ToolMaterial toolMaterial, ComponentChanges componentChanges) {
        this.swordDamage = swordDamage;
        this.pickaxeDamage = pickaxeDamage;
        this.axeDamage = axeDamage;
        this.Durability = Durability;
        this.speed = speed;
        this.enchantability = enchantability;
        this.toolMaterial = toolMaterial;
        this.componentChanges = componentChanges;
    }
    public ModularMaterial(float swordDamage, float pickaxeDamage, float axeDamage, int Durability, int speed, int enchantability, ToolMaterial toolMaterial) {
        this.swordDamage = swordDamage;
        this.pickaxeDamage = pickaxeDamage;
        this.axeDamage = axeDamage;
        this.Durability = Durability;
        this.speed = speed;
        this.enchantability = enchantability;
        this.toolMaterial = toolMaterial;
        this.componentChanges = ComponentChanges.EMPTY;
    }
    public ModularMaterial(float swordDamage, float pickaxeDamage, float axeDamage, int Durability, ToolMaterial toolMaterial) {
        this.swordDamage = swordDamage;
        this.pickaxeDamage = pickaxeDamage;
        this.axeDamage = axeDamage;
        this.Durability = Durability;
        this.speed = toolMaterial.speed();
        this.enchantability = toolMaterial.enchantmentValue();
        this.toolMaterial = toolMaterial;
        this.componentChanges = ComponentChanges.EMPTY;
    }
    public ModularMaterial(ToolMaterial toolMaterial,ComponentChanges componentChanges) {
        this.swordDamage = toolMaterial.attackDamageBonus()+3f;
        this.pickaxeDamage = toolMaterial.attackDamageBonus()+1f;
        this.axeDamage = toolMaterial.attackDamageBonus()+6f;
        this.Durability = toolMaterial.durability();
        this.speed = toolMaterial.speed();
        this.enchantability = toolMaterial.enchantmentValue();
        this.toolMaterial = toolMaterial;
        this.componentChanges = componentChanges;
    }
    public ModularMaterial(ToolMaterial toolMaterial) {
        this.swordDamage = toolMaterial.attackDamageBonus()+3f;
        this.pickaxeDamage = toolMaterial.attackDamageBonus()+1f;
        this.axeDamage = toolMaterial.attackDamageBonus()+6f;
        this.Durability = toolMaterial.durability();
        this.speed = toolMaterial.speed();
        this.enchantability = toolMaterial.enchantmentValue();
        this.toolMaterial = toolMaterial;
        this.componentChanges = ComponentChanges.EMPTY;
    }
}
