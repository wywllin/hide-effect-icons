# Hide Effect Icons

(此 mod 由 100% 的 AI 生成)

适用于 Minecraft 1.21.1 NeoForge 的纯客户端模组。

本模组可以控制并隐藏以下内容：

- 隐藏界面右上角的药水/状态效果图标。
- 隐藏本地玩家自身因为药水/状态效果而产生的粒子。
- 隐藏本地玩家着火时在第一人称视角中的火焰遮挡。
- 隐藏闪电本体的渲染特效。
- 隐藏闪电触发的天空闪白、远景雾闪白与地表光照闪烁特效。
- 静音客户端的雷声与落雷撞击声（不影响雨声等其他天气音效）。

---

## 配置

首次启动游戏后会生成配置文件，编辑以下文件(默认flash)：

```text
.minecraft/config/hide_effect_icons-client.toml
```
---

## 构建

需要使用 Java 21：

```powershell
.\gradlew.bat clean build
```

构建生成的 jar 文件位于：

```text
build/libs/
```
---

## 下载

可通过 [Release 页面](https://github.com/wywllin/hide-effect-icons/releases) 下载最新版本。
