package com.example.hideeffecticons.mixin;

import com.example.hideeffecticons.ClientConfig;
import com.example.hideeffecticons.EffectExtensionWrapperCache;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.neoforge.client.extensions.common.IClientMobEffectExtensions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(IClientMobEffectExtensions.class)
public interface IClientMobEffectExtensionsMixin {

    @Inject(method = "of(Lnet/minecraft/world/effect/MobEffect;)Lnet/neoforged/neoforge/client/extensions/common/IClientMobEffectExtensions;", at = @At("RETURN"), cancellable = true, remap = false)
    private static void hideEffectIcons$wrapExtensions(MobEffect effect, CallbackInfoReturnable<IClientMobEffectExtensions> cir) {
        if (ClientConfig.hideEffectIcons()) {
            cir.setReturnValue(EffectExtensionWrapperCache.getWrapper(cir.getReturnValue()));
        }
    }
}
