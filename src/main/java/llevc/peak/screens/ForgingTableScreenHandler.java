package llevc.peak.screens;

import llevc.peak.ModBlocks;
import llevc.peak.ModComponents;
import llevc.peak.ModItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ForgingTableScreenHandler extends CraftingScreenHandler {
    private final ScreenHandlerContext context;
    private final PlayerEntity player;
    private boolean filling;

    public ForgingTableScreenHandler(int syncId, PlayerInventory playerInventory) {
        super(syncId, playerInventory, ScreenHandlerContext.EMPTY);
        this.context = ScreenHandlerContext.EMPTY;
        this.player = playerInventory.player;
    }

    public ForgingTableScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(syncId, playerInventory, context);
        this.context = context;
        this.player = playerInventory.player;
    }

    @Override public boolean canUse(PlayerEntity player) {
        return ScreenHandler.canUse(context, player, ModBlocks.ForgingTable);
    }

    public boolean slotIsOf(ScreenHandler handler, int index,Item item) {
        return handler.getSlot(index).getStack().isOf(item);
    }

    protected static void updateResult(ScreenHandler handler, ServerWorld world, PlayerEntity player, RecipeInputInventory craftingInventory, CraftingResultInventory resultInventory, @Nullable RecipeEntry<CraftingRecipe> recipe) {
        CraftingRecipeInput craftingRecipeInput = craftingInventory.createRecipeInput();
        ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)player;
        ItemStack itemStack = ItemStack.EMPTY;

        Item material = handler.getSlot(2).getStack().getItem();
        Item rod = handler.getSlot(8).getStack().getItem();
        Item air = Items.AIR;

        List<Item> swordPattern = List.of(
                air, material, air,
                air, material, air,
                air, rod, air
        );
        List<Item> pickaxePattern = List.of(
                material, material, material,
                air, rod, air,
                air, rod, air
        );
        List<Item> axePattern = List.of(
                material, material, air,
                material, rod, air,
                air, rod, air
        );
        List<Item> shovelPattern = List.of(
                air, material, air,
                air, rod, air,
                air, rod, air
        );
        List<Item> hoePattern = List.of(
                material, material, air,
                air, rod, air,
                air, rod, air
        );
        Iterator<List<Item>> yes = List.of(
                swordPattern, // 1 Sword
                pickaxePattern, // 2 Pick
                axePattern, // 3 Axe
                shovelPattern, // 4 Shovel
                hoePattern // 5 Hoe
        ).iterator();

        boolean sword = false;
        boolean pickaxe = false;
        boolean axe = false;
        boolean shovel = false;
        boolean hoe = false;
        boolean valid = false;

        if (material.getDefaultStack().isIn(TagKey.of(RegistryKeys.ITEM,Identifier.of("peak","forging/materials"))) && rod.getDefaultStack().isIn(TagKey.of(RegistryKeys.ITEM,Identifier.of("peak","forging/rods")))) {
            int j = 1;
            while (yes.hasNext()) {
                List<Item> pattern = yes.next();
                Iterator<Item> heyNow = pattern.iterator();
                int i = 0;
                final boolean[] heyo = {
                        false, false, false,
                        false, false, false,
                        false, false, false
                };
                while (heyNow.hasNext()) {
                    Item wait1Sec = heyNow.next();
                    Item wait2Sec = handler.getSlot(i + 1).getStack().getItem();
                    if (wait1Sec.equals(wait2Sec) || (wait1Sec.equals(Items.AIR) && handler.getSlot(i + 1).getStack().isEmpty())) {
                        heyo[i] = true;
                    }
                    i = i + 1;
                }
                if (Arrays.equals(heyo, new boolean[]{
                        true, true, true,
                        true, true, true,
                        true, true, true
                })) {
                    switch (j) {
                        case 1:
                            sword = true;
                        case 2:
                            pickaxe = true;
                        case 3:
                            axe = true;
                        case 4:
                            shovel = true;
                        case 5:
                            hoe = true;
                    }
                    valid = true;
                    break;
                }
                j = j + 1;
            }
        }

        if (valid) {
            ItemStack itemStack2 = null;
            if (sword) {
                itemStack2 = ModItems.ModularSwordItem.getDefaultStack();
            } else if (pickaxe) {
                itemStack2 = ModItems.ModularPickaxeItem.getDefaultStack();
            }  else if (axe) {
                itemStack2 = ModItems.ModularAxeItem.getDefaultStack();
            } else if (shovel) {
                itemStack2 = ModItems.ModularShovelItem.getDefaultStack();
            } else if (hoe) {
                itemStack2 = ModItems.ModularHoeItem.getDefaultStack();
            }

            assert itemStack2 != null;
            itemStack2.set(ModComponents.ModularComponent.ModularComponentType,new ModComponents.ModularComponent.Modular(material.toString(),rod.toString()));
            itemStack2.onCraftByPlayer(player,1);
            itemStack = itemStack2;
        }

        resultInventory.setStack(0, itemStack);
        handler.setReceivedStack(0, itemStack);
        serverPlayerEntity.networkHandler.sendPacket(new ScreenHandlerSlotUpdateS2CPacket(handler.syncId, handler.nextRevision(), 0, itemStack));
    }

    @Override
    public void onContentChanged(Inventory inventory) {
        if (!this.filling) {
            this.context.run((world, pos) -> {
                if (world instanceof ServerWorld serverWorld) {
                    updateResult(this, serverWorld, this.player, this.craftingInventory, this.craftingResultInventory, null);
                }
            });
        }
    }

    public void onInputSlotFillStart() {
        this.filling = true;
    }

    @Override
    public void onInputSlotFillFinish(ServerWorld world, RecipeEntry<CraftingRecipe> recipe) {
        this.filling = false;
        updateResult(this, world, this.player, this.craftingInventory, this.craftingResultInventory, recipe);
    }
}
