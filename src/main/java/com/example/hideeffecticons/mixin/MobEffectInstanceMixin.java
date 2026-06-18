package com.example.hideeffecticons.mixin;

import com.example.hideeffecticons.ClientConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.world.effect.MobEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobEffectInstance.class)
public class MobEffectInstanceMixin {

    @Inject(method = "showIcon", at = @At("HEAD"), cancellable = true)
    private void hideEffectIcons$hideIconInHud(CallbackInfoReturnable<Boolean> cir) {
        if (ClientConfig.hideEffectIcons()) {
            Minecraft mc = Minecraft.getInstance();
            // If the player is in-game (no GUI screen open), we return false to pretend there's no icon.
            // This tricks Xaero's Minimap into not shifting down.
            // When a screen (like the inventory) is open, mc.screen != null, so the inventory effect list
            // will still correctly show the icons.
            if (mc != null && mc.screen == null) {
                cir.setReturnValue(false);
            }
        }
    }
}
