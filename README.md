# Hide Effect Icons

A small Minecraft 1.21.1 NeoForge client mod that hides potion/status effect icons.

It cancels the vanilla effect rendering in:

- The in-game HUD.

The effects themselves still work normally; only the icon UI is hidden.

The mod can also hide potion/status effect particles emitted by your own player.

## Config

After launching the game once, edit:

```text
.minecraft/config/hide_effect_icons-client.toml
```

Set `hide_effect_icons = false` to show the vanilla HUD effect icons again.
Set `hide_own_effect_particles = false` to show your own effect particles again.

The generated config file includes Chinese comments, and the NeoForge mod-list config screen has Chinese labels when the game language is Chinese.

## Build

Use Java 21, then run:

```powershell
gradle build
```

The mod jar will be generated under `build/libs`.
