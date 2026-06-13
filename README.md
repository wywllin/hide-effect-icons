# Hide Effect Icons

(此 mod 由 100% 的 AI 生成)

---

## 简介

一个用于 Minecraft 1.21.1 NeoForge 的客户端模组，可以隐藏药水/状态效果图标。

该模组会取消以下位置的原版效果渲染：
- 游戏内 HUD 界面

效果本身仍然正常工作，只是图标 UI 被隐藏了。

该模组还可以隐藏玩家自身发出的药水/状态效果粒子。

---

## 配置

配置文件默认路径为：

```text
.minecraft/config/hide_effect_icons-client.toml
```

**配置选项：**
- `hide_effect_icons` - 配置是否隐藏效果图标（默认开启）
- `hide_own_effect_particles` - 配置是否隐藏自身的效果粒子（默认开启）

隐藏功能默认开启，可通过配置文件关闭，也可通过模组配置界面快速调整配置。

---

## 构建

使用 Java 21，然后运行：

```powershell
gradle build
```

模组 jar 文件将生成在 `build/libs` 目录下。

---

## 下载

可通过 [Release 页面](https://github.com/wywllin/hide-effect-icons/releases) 下载最新版本。

---

**English Version:** [README_en.md](README_en.md)