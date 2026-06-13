# Hide Effect Icons

一个用于 Minecraft 1.21.1 NeoForge 的客户端模组，可以隐藏药水/状态效果图标。

该模组会取消以下位置的原版效果渲染：

- 游戏内 HUD 界面

效果本身仍然正常工作，只是图标 UI 被隐藏了。

该模组还可以隐藏玩家自身发出的药水/状态效果粒子。

## 配置

启动游戏一次后，编辑以下文件：

```text
.minecraft/config/hide_effect_icons-client.toml
```

设置 `hide_effect_icons = false` 可以重新显示原版 HUD 效果图标。
设置 `hide_own_effect_particles = false` 可以重新显示自身的效果粒子。

生成的配置文件包含中文注释，当游戏语言设置为中文时，NeoForge 模组列表配置界面也会显示中文标签。

## 构建

使用 Java 21，然后运行：

```powershell
gradle build
```

模组 jar 文件将生成在 `build/libs` 目录下。

---

**English Version:** [README_en.md](README_en.md)