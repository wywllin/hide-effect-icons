# Hide Effect Icons

[English](#english) | [中文](#中文)

---

<h2 id="english">English</h2>

Minecraft 1.21.1 NeoForge client-side mod.

This mod can hide:

- HUD potion/status effect icons (compatible with minimap mods like Xaero's Minimap, preventing them from shifting downwards).
- Potion/status effect particles emitted by the local player.
- First-person fire overlay while the local player is burning.

It does not remove effects, extinguish the player, change damage, or hide the inventory-side effect list.

### Config

Launch the game once, then edit:

```text
.minecraft/config/hide_effect_icons-client.toml
```

Options:

```toml
hide_effect_icons = true
hide_own_effect_particles = true
hide_first_person_fire = true
```

Set an option to `false` to restore the corresponding vanilla visual effect.

The generated config file uses Chinese comments. The NeoForge mod-list config screen also has Chinese labels when the game language is Chinese.

### Build

Use Java 21:

```powershell
.\gradlew.bat clean build
```

The mod jar is generated under:

```text
build/libs/
```

Developer handoff notes are in [docs/DEVELOPMENT.md](docs/DEVELOPMENT.md).

---

<h2 id="中文">中文</h2>

适用于 Minecraft 1.21.1 NeoForge 的纯客户端模组。

本模组可以隐藏以下内容：

- 隐藏界面右上角的药水/状态效果图标（完美兼容 Xaero's Minimap 等小地图模组，开启时小地图不会发生位置下移）。
- 隐藏本地玩家自身因为药水/状态效果而产生的粒子。
- 隐藏本地玩家着火时在第一人称视角中的火焰遮挡。

本模组**不会**清除药水效果、不会熄灭玩家、不会改变任何伤害逻辑，也**不会**隐藏打开物品栏（E键）时显示的效果列表。

### 配置

首次启动游戏后会生成配置文件，编辑以下文件：

```text
.minecraft/config/hide_effect_icons-client.toml
```

可用选项：

```toml
hide_effect_icons = true
hide_own_effect_particles = true
hide_first_person_fire = true
```

将对应选项设置为 `false` 即可恢复原版的视觉效果。

生成的配置文件包含中文注释，并且当游戏语言设置为中文时，NeoForge 模组列表中的配置界面也会显示中文名称和说明。

### 构建

需要使用 Java 21：

```powershell
.\gradlew.bat clean build
```

构建生成的 jar 文件位于：

```text
build/libs/
```

开发交接文档请查阅 [docs/DEVELOPMENT.md](docs/DEVELOPMENT.md)。
