package llevc.peak.mixin;

import llevc.peak.ThePeakExpansion;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FireworksComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FlyingItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.OptionalInt;

@Mixin(FireworkRocketEntity.class)
public abstract class FireworkMixin extends ProjectileEntity implements FlyingItemEntity {
    public FireworkMixin(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "explode", at = @At("HEAD"))
    public void init(ServerWorld world, CallbackInfo ci) {
        if (this.getOwner() != null) {
            Vec3d direction = (this.getOwner().getEntityPos().subtract(this.getEntityPos()));
            if (direction.length() < 5) {
                Vec3d yo = direction.multiply(4/direction.length());
                ThePeakExpansion.LOGGER.info(yo.toString());
                this.getOwner().onLanding();
                this.getOwner().addVelocity(yo);
            }
        }
    }

    @Shadow
    public ItemStack getStack() {
        return null;
    }

    @Shadow
    protected void initDataTracker(DataTracker.Builder builder) {
    }
}
