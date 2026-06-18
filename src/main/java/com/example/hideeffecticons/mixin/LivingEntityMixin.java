package com.example.hideeffecticons.mixin;

import com.example.hideeffecticons.ClientConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Redirect(
            method = "tickEffects",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;addParticle(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)V"
            )
    )
    private void hideEffectIcons$hideOwnEffectParticles(
            Level level,
            ParticleOptions particleOptions,
            double x,
            double y,
            double z,
            double xSpeed,
            double ySpeed,
            double zSpeed
    ) {
        if (ClientConfig.hideOwnEffectParticles() && (Object) this == Minecraft.getInstance().player) {
            return;
        }

        level.addParticle(particleOptions, x, y, z, xSpeed, ySpeed, zSpeed);
    }
}
