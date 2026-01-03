package llevc.peak.items;

import llevc.peak.ThePeakExpansion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Random;
import java.util.random.RandomGenerator;

public class PrideItem extends Item {
    public PrideItem(Settings settings) {
        super(settings);
    }

    @Override
    public void postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        World world = attacker.getEntityWorld();
        if (stack.getDamage() > 0) {
            if (stack.getDamage()-10 <= 0) {
                if (!world.isClient()) {
                    stack.setDamage(100);
                    Vec3d pos = target.getEntityPos();
                    ((ServerWorld) world).spawnParticles(ParticleTypes.FLAME,pos.x,pos.y+1,pos.z,25,0.1,0.75,0.1,0.1);
                }
            } else {
                if (!world.isClient()) {
                    stack.setDamage(stack.getDamage() - 10);
                }
            }
        }
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient()) {
            return ActionResult.PASS;
        }
        //absorb potions
        user.getItemCooldownManager().set(user.getStackInHand(hand),20 * 2);
        int radius = 25;
        Box die = Box.of(user.getBlockPos().toCenterPos(),radius*2,radius*2,radius*2);
        for (Entity entity : world.getOtherEntities(user.getEntity(), die)) {
            LivingEntity sup = entity.getEntity();
            if (sup != null) {
                //ThePeakExpansion.LOGGER.info(sup.getName().getString());
                if (!sup.getActiveStatusEffects().isEmpty()) {
                    for (StatusEffectInstance hup : sup.getActiveStatusEffects().values()) {
                        user.addStatusEffect(hup);
                    }
                    sup.clearStatusEffects();
                }
            }
        }
        return ActionResult.SUCCESS;
    }
}
