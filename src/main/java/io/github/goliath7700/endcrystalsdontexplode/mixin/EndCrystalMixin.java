package io.github.goliath7700.endcrystalsdontexplode.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EndCrystal.class)
public abstract class EndCrystalMixin extends Entity {

    public EndCrystalMixin(EntityType<?> type, Level level) {
        super(type, level);
    }

    @Shadow
    protected abstract void onDestroyedBy(ServerLevel level, DamageSource source);


    @Shadow
    public abstract void kill(ServerLevel level);

    /**
     * @author
     * @reason
     */
    @Overwrite
    public final boolean hurtServer(final ServerLevel level, final DamageSource source, final float damage) {
        if (this.isInvulnerableToBase(source)) {
            return false;
        } else if (source.getEntity() instanceof EnderDragon) {
            return false;
        } else {
            if (!this.isRemoved()) {
                this.remove(Entity.RemovalReason.KILLED);
                this.onDestroyedBy(level, source);
            }

            return true;
        }
    }

}