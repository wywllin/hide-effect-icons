# Hide Effect Icons

(This mod is 100% AI-generated)

---

## Introduction

A client-side mod for Minecraft 1.21.1 NeoForge that hides potion/status effect icons.

This mod cancels the following visual effects:
- Potion/status effect icons in the in-game HUD
- Potion/status effect particles emitted by your own player
- Fire overlay effect on screen when on fire

The effects themselves still work normally; only the visual effects are hidden.

---

## Configuration

Default config file path:

```text
.minecraft/config/hide_effect_icons-client.toml
```

**Config Options:**
- `hide_effect_icons` - Configure whether to hide HUD effect icons (enabled by default)
- `hide_own_effect_particles` - Configure whether to hide your own effect particles (enabled by default)
- `hide_fire_overlay` - Configure whether to hide fire overlay effect (enabled by default)

Hiding is enabled by default, can be disabled via config, or quickly adjusted through the mod configuration screen.

---

## Build

Use Java 21, then run:

```powershell
gradle build
```

The mod jar file will be generated in the `build/libs` directory.

---

## Download

Download the latest version from the [Release page](https://github.com/wywllin/hide-effect-icons/releases).

---

**Chinese Version:** [README.md](README.md)