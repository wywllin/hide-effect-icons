package com.example.hideeffecticons;

import net.neoforged.neoforge.common.ModConfigSpec;

public final class ClientConfig {
    public static final ModConfigSpec SPEC;
    private static final ModConfigSpec.BooleanValue HIDE_EFFECT_ICONS;
    private static final ModConfigSpec.BooleanValue HIDE_OWN_EFFECT_PARTICLES;
    private static final ModConfigSpec.BooleanValue HIDE_FIRE_OVERLAY;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        HIDE_EFFECT_ICONS = builder
                .comment("设置为 true 时，隐藏游戏 HUD 右上角的药水/状态效果图标。")
                .translation("hide_effect_icons.configuration.hide_effect_icons")
                .define("hide_effect_icons", true);
        HIDE_OWN_EFFECT_PARTICLES = builder
                .comment("设置为 true 时，隐藏自己身上由药水/状态效果产生的粒子。")
                .translation("hide_effect_icons.configuration.hide_own_effect_particles")
                .define("hide_own_effect_particles", true);
        HIDE_FIRE_OVERLAY = builder
                .comment("设置为 true 时，隐藏着火时屏幕上的火焰叠加效果。")
                .translation("hide_effect_icons.configuration.hide_fire_overlay")
                .define("hide_fire_overlay", true);
        SPEC = builder.build();
    }

    private ClientConfig() {
    }

    public static boolean hideEffectIcons() {
        return HIDE_EFFECT_ICONS.get();
    }

    public static boolean hideOwnEffectParticles() {
        return HIDE_OWN_EFFECT_PARTICLES.get();
    }

    public static boolean hideFireOverlay() {
        return HIDE_FIRE_OVERLAY.get();
    }
}
