package com.example.hideeffecticons.mixin;

import com.example.hideeffecticons.ClientConfig;
import net.minecraft.client.multiplayer.ClientLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientLevel.class)
public abstract class ClientLevelMixin {
    @Inject(method = "getSkyFlashTime", at = @At("HEAD"), cancellable = true)
    private void hideEffectIcons$hideLightningSkyFlash(CallbackInfoReturnable<Integer> callbackInfo) {
        if (ClientConfig.hideLightningSkyFlash()) {
            callbackInfo.setReturnValue(0);
        }
    }
}
