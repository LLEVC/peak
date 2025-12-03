package llevc.peak.mixin;

import llevc.peak.ModComponents;
import net.minecraft.block.BlockState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.ForgingSlotsManager;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.StringHelper;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(AnvilScreenHandler.class)
public abstract class AnvilStuffMixin extends ForgingScreenHandler {
	public AnvilStuffMixin(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context, ForgingSlotsManager forgingSlotsManager) {
		super(type, syncId, playerInventory, context, forgingSlotsManager);
	}
	@Unique private boolean ayowait(ItemStack itemStack3, Item item) {
		return itemStack3.itemMatches(item.getRegistryEntry());
	}
	@Shadow @Final private Property levelCost;
	@Shadow protected abstract boolean canTakeOutput(PlayerEntity player, boolean present);
	@Shadow public abstract int getLevelCost();

	@Shadow private boolean keepSecondSlot;

	@Shadow private int repairItemUsage;

	@Shadow protected abstract void onTakeOutput(PlayerEntity player, ItemStack stack);

	@Shadow private @Nullable String newItemName;

	@Unique private boolean heyo(ItemStack itemStack) {
        return (this.newItemName != null && !StringHelper.isBlank(this.newItemName) && !this.newItemName.equals(itemStack.getName().getString()));
    }

	@Unique private void addLevel(int amount) {
		if (this.getLevelCost() <= 0) {
			this.levelCost.set(amount);
		} else {
			this.levelCost.set(this.getLevelCost()+amount);
		}
	}

	@Unique public int HEY() {
		final int[] value = {40};
		this.context.run((world,pos) -> {
			BlockState blockState = world.getBlockState(pos);
			if (blockState.isIn(TagKey.of(RegistryKeys.BLOCK,Identifier.of("peak","anvil/tier0")))) {
				value[0] = 20;
			} else if (blockState.isIn(TagKey.of(RegistryKeys.BLOCK,Identifier.of("peak","anvil/tier1")))) {
				value[0] = 40;
			} else if (blockState.isIn(TagKey.of(RegistryKeys.BLOCK,Identifier.of("peak","anvil/tier2")))) {
				value[0] = 60;
			}
		});

		return value[0];
	}

	@Inject(at = @At("RETURN"),method = "canUse", cancellable = true)
	protected void init(BlockState state, CallbackInfoReturnable<Boolean> cir) {
		boolean yessir = state.isIn(TagKey.of(RegistryKeys.BLOCK,Identifier.of("peak","anvil/can_use")));
		cir.setReturnValue(yessir);
	}

	@ModifyConstant(method = "updateResult", constant = @Constant(intValue = 40))
	private int mixinLimitInt(int i) {
		return HEY();
	}

	@ModifyConstant(method = "updateResult", constant = @Constant(intValue = 39))
	private int mixinMaxInt(int i) {
		return HEY()-1;
	}

	@Inject(method = "updateResult",at = @At("RETURN"))
	private void init(CallbackInfo info) {
		ItemStack itemStack = this.input.getStack(0);
		ItemStack itemStack2 = itemStack.copy();
		ItemStack itemStack3 = this.input.getStack(1);

		if (!this.output.getStack(0).isEmpty()) {
			itemStack2 = this.output.getStack(0).copy();
		}

		if (!itemStack.isEmpty() && !itemStack3.isEmpty()) {
			//studded stuff
			boolean isChainmail = (ayowait(itemStack3,Items.CHAINMAIL_HELMET) || ayowait(itemStack3,Items.CHAINMAIL_CHESTPLATE) || ayowait(itemStack3,Items.CHAINMAIL_LEGGINGS) || ayowait(itemStack3,Items.CHAINMAIL_BOOTS));
			boolean matchesArmorType = itemStack3.contains(DataComponentTypes.EQUIPPABLE) && Objects.equals(itemStack3.get(DataComponentTypes.EQUIPPABLE).slot(), itemStack.get(DataComponentTypes.EQUIPPABLE).slot());
			boolean isStudded = ModComponents.isStudded(itemStack);

			if (heyo(itemStack)) {
				itemStack2.set(DataComponentTypes.CUSTOM_NAME, Text.literal(this.newItemName));
				addLevel(1);
			}

			if (matchesArmorType && isChainmail && !isStudded) {

				itemStack2.set(ModComponents.StuddedComponent,true);

				EntityAttributeModifier ayo = new EntityAttributeModifier(Identifier.ofVanilla("studded_armor"),2,EntityAttributeModifier.Operation.ADD_VALUE);
				EntityAttributeModifier heyo = new EntityAttributeModifier(Identifier.ofVanilla("studded_armor_toughness"),1,EntityAttributeModifier.Operation.ADD_VALUE);

				itemStack2.set(
						DataComponentTypes.ATTRIBUTE_MODIFIERS,
						itemStack2.getOrDefault(DataComponentTypes.ATTRIBUTE_MODIFIERS,AttributeModifiersComponent.DEFAULT).with(EntityAttributes.ARMOR,ayo,AttributeModifierSlot.ARMOR).with(EntityAttributes.ARMOR_TOUGHNESS,heyo,AttributeModifierSlot.ARMOR)
				);
				//new EntityAttributeModifier(Identifier.of("armor"),2,EntityAttributeModifier.Operation.ADD_VALUE)
				this.output.setStack(0,itemStack2);
				if (this.getLevelCost() <= 0) {
					this.levelCost.set(5);
				}
				//this.canTakeOutput(player,true);
				this.sendContentUpdates();
			}
		}
	}
	@Inject(at = @At("HEAD"),method = "onTakeOutput")
	private void init(PlayerEntity player, ItemStack stack, CallbackInfo ci) {
		//ItemStack itemStack1 = this.input.getStack(0);
		ItemStack itemStack = this.input.getStack(1).copy();
		if (itemStack.isDamageable()) {
			if (this.keepSecondSlot && this.repairItemUsage > 0) {
				if (itemStack.getDamage() + this.repairItemUsage < itemStack.getMaxDamage()) {
					itemStack.setDamage(itemStack.getDamage() + this.repairItemUsage);
					this.input.setStack(1, itemStack);
					this.repairItemUsage = 0;
				} else if (itemStack.getDamage() + this.repairItemUsage == itemStack.getMaxDamage()) {
					player.playSoundToPlayer(SoundEvent.of(Identifier.ofVanilla("entity.item.break")), SoundCategory.PLAYERS, 1,1);
					this.repairItemUsage = 1;
				}
			}
		}

		this.context.run((world,pos) -> {
			BlockState blockState = world.getBlockState(pos);

		});
	}
}