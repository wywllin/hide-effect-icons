package com.example.hideeffecticons;

import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.event.sound.PlaySoundEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;

@Mod(HideEffectIcons.MOD_ID)
public final class HideEffectIcons {
    public static final String MOD_ID = "hide_effect_icons";

    public HideEffectIcons(ModContainer container) {
        container.registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC, MOD_ID + "-client.toml");
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
        NeoForge.EVENT_BUS.addListener(HideEffectIcons::onPlaySound);
    }

    private static void onPlaySound(PlaySoundEvent event) {
        if (!ClientConfig.muteLightningSound()) {
            return;
        }

        SoundInstance sound = event.getSound();
        if (sound == null) {
            return;
        }

        ResourceLocation location = sound.getLocation();
        if (SoundEvents.LIGHTNING_BOLT_THUNDER.getLocation().equals(location)
                || SoundEvents.LIGHTNING_BOLT_IMPACT.getLocation().equals(location)) {
            event.setSound(null);
        }
    }
}
