package com.example.hideeffecticons.mixin;

import com.example.hideeffecticons.ClientConfig;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ScreenEffectRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ScreenEffectRenderer.class)
public abstract class ScreenEffectRendererMixin {
    @Inject(method = "renderFire", at = @At("HEAD"), cancellable = true)
    private static void hideEffectIcons$hideFirstPersonFire(Minecraft minecraft, PoseStack poseStack, CallbackInfo callbackInfo) {
        if (ClientConfig.hideFirstPersonFire()) {
            callbackInfo.cancel();
        }
    }
}
