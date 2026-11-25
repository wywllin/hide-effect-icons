package com.example.hideeffecticons;

import net.neoforged.neoforge.common.ModConfigSpec;

public final class ClientConfig {
    public static final ModConfigSpec SPEC;
    private static final ModConfigSpec.BooleanValue HIDE_EFFECT_ICONS;
    private static final ModConfigSpec.BooleanValue HIDE_OWN_EFFECT_PARTICLES;
    private static final ModConfigSpec.BooleanValue HIDE_FIRST_PERSON_FIRE;
    private static final ModConfigSpec.BooleanValue HIDE_LIGHTNING_BOLT;
    private static final ModConfigSpec.BooleanValue HIDE_LIGHTNING_SKY_FLASH;
    private static final ModConfigSpec.BooleanValue MUTE_LIGHTNING_SOUND;

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
        HIDE_FIRST_PERSON_FIRE = builder
                .comment("设置为 true 时，隐藏自己着火时第一人称视角中的火焰遮挡。")
                .translation("hide_effect_icons.configuration.hide_first_person_fire")
                .define("hide_first_person_fire", true);
        HIDE_LIGHTNING_BOLT = builder
                .comment("设置为 true 时，隐藏闪电本体的渲染特效。")
                .translation("hide_effect_icons.configuration.hide_lightning_bolt")
                .define("hide_lightning_bolt", false);
        HIDE_LIGHTNING_SKY_FLASH = builder
                .comment("设置为 true 时，隐藏闪电引起的天空闪白、远景雾闪白与地表光照闪烁特效。")
                .translation("hide_effect_icons.configuration.hide_lightning_sky_flash")
                .define("hide_lightning_sky_flash", false);
        MUTE_LIGHTNING_SOUND = builder
                .comment("设置为 true 时，静音客户端的雷声与落雷撞击声，不影响雨声等其他天气音效。")
                .translation("hide_effect_icons.configuration.mute_lightning_sound")
                .define("mute_lightning_sound", false);
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

    public static boolean hideFirstPersonFire() {
        return HIDE_FIRST_PERSON_FIRE.get();
    }

    public static boolean hideLightningBolt() {
        return SPEC.isLoaded() && HIDE_LIGHTNING_BOLT.get();
    }

    public static boolean hideLightningSkyFlash() {
        return SPEC.isLoaded() && HIDE_LIGHTNING_SKY_FLASH.get();
    }

    public static boolean muteLightningSound() {
        return SPEC.isLoaded() && MUTE_LIGHTNING_SOUND.get();
    }
}
