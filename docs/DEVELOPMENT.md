# 开发交接文档

本文档用于帮助后续开发者快速接手这个 Minecraft 1.21.1 NeoForge 模组。

## 项目概览

这是一个纯客户端 NeoForge 模组，模组 ID 为 `hide_effect_icons`。

当前功能：

- 隐藏 HUD 右上角的药水/状态效果图标。
- 隐藏本地玩家自己身上的药水/状态效果粒子。
- 隐藏本地玩家着火时第一人称视角里的火焰遮挡。
- 隐藏闪电本体的渲染特效（默认关闭）。
- 隐藏闪电触发的天空闪白、远景雾闪白与地表光照闪烁特效（默认关闭）。
- 静音客户端雷声与落雷撞击声（默认关闭）。
- 生成带中文注释的客户端配置文件。
- 在 NeoForge 模组菜单的配置界面里显示中英文名称和说明。

当前明确不做的事情：

- 不隐藏物品栏旁边的药水效果列表。
- 不移除或修改药水/状态效果本身。
- 不熄灭玩家，也不改变燃烧伤害。
- 不隐藏其他玩家身上的粒子。
- 不影响雷击真实逻辑（伤害、引燃方块、避雷针充能、铜块除锈、苦力怕转换等）。
- 不静音雨声等非雷电天气音效。

## 环境要求

- Minecraft：`1.21.1`
- NeoForge：当前配置为 `21.1.233`
- Java：`21`
- Gradle：使用项目内置 Wrapper

构建命令：

```powershell
.\gradlew.bat clean build
```

构建产物：

```text
build/libs/hide_effect_icons-1.2.0.jar
```

## 项目结构

需要关注的文件：

```text
build.gradle
gradle.properties
settings.gradle
gradlew
gradlew.bat
gradle/wrapper/
src/main/java/com/example/hideeffecticons/
src/main/resources/
docs/DEVELOPMENT.md
README.md
```

本地缓存或构建产物：

```text
.gradle/
.gradle-dist/
build/
runs/
out/
```

这些目录已经写入 `.gitignore`，不用提交到版本库。`build/libs/` 里的 jar 是最终构建产物，可以复制到游戏的 `mods` 文件夹测试。

## 核心源码说明

### `HideEffectIcons.java`

路径：

```text
src/main/java/com/example/hideeffecticons/HideEffectIcons.java
```

职责：

- 模组主入口。
- 注册客户端配置文件：`hide_effect_icons-client.toml`。
- 注册 NeoForge 内置配置界面：`ConfigurationScreen`。
- 向 `NeoForge.EVENT_BUS` 注册 `PlaySoundEvent` 监听：当 `mute_lightning_sound = true` 时拦截 `entity.lightning_bolt.thunder` 与 `entity.lightning_bolt.impact` 音效（通过 `event.setSound(null)` 静音）。

注册配置界面后，在 NeoForge 模组列表中点本模组的配置按钮，可以打开配置 UI。

### `ClientConfig.java`

路径：

```text
src/main/java/com/example/hideeffecticons/ClientConfig.java
```

职责：

- 定义所有客户端配置项。
- `.comment(...)` 中的中文会写入生成的 TOML 配置文件。
- `.translation(...)` 指定配置界面使用的翻译键。

当前配置项：

```toml
hide_effect_icons = true
hide_own_effect_particles = true
hide_first_person_fire = true
hide_lightning_bolt = false
hide_lightning_sky_flash = false
mute_lightning_sound = false
```

- 药水图标、自身粒子、第一人称火焰默认值为 `true`。
- 三个闪电相关选项默认值为 `false`，且 getter 中通过 `SPEC.isLoaded()` 防护，避免在客户端启动早期（如标题界面触发 `PlaySoundEvent` 时）读取未加载的配置。

### `GuiMixin.java`

路径：

```text
src/main/java/com/example/hideeffecticons/mixin/GuiMixin.java
```

目标类：

```text
net.minecraft.client.gui.Gui
```

作用：

- 注入 `renderEffects(GuiGraphics, DeltaTracker)`。
- 当 `hide_effect_icons = true` 时取消 HUD 药水图标渲染。
- 只影响 HUD 右上角图标，不影响物品栏旁边的效果列表。

### `LivingEntityMixin.java`

路径：

```text
src/main/java/com/example/hideeffecticons/mixin/LivingEntityMixin.java
```

目标类：

```text
net.minecraft.world.entity.LivingEntity
```

作用：

- 重定向 `tickEffects` 中生成状态效果粒子的 `Level#addParticle(...)` 调用。
- 仅当当前实体是 `Minecraft.getInstance().player` 且 `hide_own_effect_particles = true` 时阻止生成粒子。
- 不影响其他玩家，也不影响普通世界粒子。

### `ScreenEffectRendererMixin.java`

路径：

```text
src/main/java/com/example/hideeffecticons/mixin/ScreenEffectRendererMixin.java
```

目标类：

```text
net.minecraft.client.renderer.ScreenEffectRenderer
```

作用：

- 注入私有静态方法 `renderFire(Minecraft, PoseStack)`。
- 当 `hide_first_person_fire = true` 时取消第一人称火焰遮挡渲染。
- 不改变玩家着火状态，不改变伤害逻辑。

### `LightningBoltRendererMixin.java`

路径：

```text
src/main/java/com/example/hideeffecticons/mixin/LightningBoltRendererMixin.java
```

目标类：

```text
net.minecraft.client.renderer.entity.LightningBoltRenderer
```

作用：

- 使用显式方法描述符注入 `render(LightningBolt, float, float, PoseStack, MultiBufferSource, int)`。
- 当 `hide_lightning_bolt = true` 时取消闪电本体几何渲染。
- 不影响实体 Tick 及服务端雷击逻辑。

### `ClientLevelMixin.java`

路径：

```text
src/main/java/com/example/hideeffecticons/mixin/ClientLevelMixin.java
```

目标类：

```text
net.minecraft.client.multiplayer.ClientLevel
```

作用：

- 注入 `getSkyFlashTime()` 的 `@At("HEAD")`。
- 当 `hide_lightning_sky_flash = true` 时直接返回 `0`，同时抑制 `ClientLevel#getSkyColor`（天空/雾气闪白）与 `LightTexture#updateLightTexture`（地表光照闪烁），且不修改原版 `options.txt`。

## 资源文件说明

### `neoforge.mods.toml`

路径：

```text
src/main/resources/META-INF/neoforge.mods.toml
```

职责：

- NeoForge 模组元数据。
- 声明 mod id、版本、描述、依赖。
- 注册 mixin 配置文件。
- 依赖声明中使用 `side="CLIENT"`，表示这是客户端模组。

### `hide_effect_icons.mixins.json`

路径：

```text
src/main/resources/hide_effect_icons.mixins.json
```

当前启用的客户端 mixin：

```json
[
  "GuiMixin",
  "LivingEntityMixin",
  "ScreenEffectRendererMixin",
  "IClientMobEffectExtensionsMixin",
  "MobEffectInstanceMixin",
  "LightningBoltRendererMixin",
  "ClientLevelMixin"
]
```

新增 mixin 后必须在这里注册，否则不会生效。

### `zh_cn.json` 与 `en_us.json`

路径：

```text
src/main/resources/assets/hide_effect_icons/lang/zh_cn.json
src/main/resources/assets/hide_effect_icons/lang/en_us.json
```

职责：

- 给 NeoForge 内置配置界面提供中英文标题、配置项名称和 tooltip。

重要翻译键：

```text
hide_effect_icons.configuration.title
hide_effect_icons.configuration.section.hide.effect.icons.client.toml
hide_effect_icons.configuration.section.hide.effect.icons.client.toml.title
hide_effect_icons.configuration.hide_effect_icons
hide_effect_icons.configuration.hide_effect_icons.tooltip
hide_effect_icons.configuration.hide_own_effect_particles
hide_effect_icons.configuration.hide_own_effect_particles.tooltip
hide_effect_icons.configuration.hide_first_person_fire
hide_effect_icons.configuration.hide_first_person_fire.tooltip
hide_effect_icons.configuration.hide_lightning_bolt
hide_effect_icons.configuration.hide_lightning_bolt.tooltip
hide_effect_icons.configuration.hide_lightning_sky_flash
hide_effect_icons.configuration.hide_lightning_sky_flash.tooltip
hide_effect_icons.configuration.mute_lightning_sound
hide_effect_icons.configuration.mute_lightning_sound.tooltip
```

注意：NeoForge 会根据配置文件名 `hide_effect_icons-client.toml` 生成 section 翻译键，把非字母数字字符替换成点，所以这里需要使用：

```text
hide.effect.icons.client.toml
```

这一段。

## 生成的配置文件

游戏启动一次后会生成：

```text
.minecraft/config/hide_effect_icons-client.toml
```

示例内容：

```toml
# 设置为 true 时，隐藏游戏 HUD 右上角的药水/状态效果图标。
hide_effect_icons = true

# 设置为 true 时，隐藏自己身上由药水/状态效果产生的粒子。
hide_own_effect_particles = true

# 设置为 true 时，隐藏自己着火时第一人称视角中的火焰遮挡。
hide_first_person_fire = true

# 设置为 true 时，隐藏闪电本体的渲染特效。
hide_lightning_bolt = false

# 设置为 true 时，隐藏闪电引起的天空闪白、远景雾闪白与地表光照闪烁特效。
hide_lightning_sky_flash = false

# 设置为 true 时，静音客户端的雷声与落雷撞击声，不影响雨声等其他天气音效。
mute_lightning_sound = false
```

把对应选项改成 `true` / `false` 即可切换对应视觉或音效控制。

## 新增功能的推荐流程

如果后续要继续添加“隐藏某个视觉效果”的开关，建议按下面流程做：

1. 在 `ClientConfig.java` 添加新的 `BooleanValue`。
2. 给配置项添加中文 `.comment(...)`。
3. 给配置项添加 `.translation("hide_effect_icons.configuration.<配置名>")`。
4. 在 `zh_cn.json` 添加配置项名称和 `.tooltip`。
5. 新建或修改一个范围尽量小的 mixin。
6. 在 `hide_effect_icons.mixins.json` 注册新的 mixin。
7. 执行 `.\gradlew.bat clean build`。
8. 进游戏测试配置项为 `true` 和 `false` 两种情况。

实现原则：

- 优先拦截渲染或粒子生成，不修改真实游戏状态。
- 功能只影响本地客户端。
- 不影响其他玩家或服务器逻辑。
- 每个功能尽量有独立配置项，方便用户单独开关。

## 验证清单

交付新 jar 前建议检查：

- `.\gradlew.bat clean build` 构建成功。
- `build/libs/hide_effect_icons-1.2.0.jar` 存在。
- `hide_effect_icons.mixins.json` 只包含当前需要启用的 mixin。
- `zh_cn.json` 和 `en_us.json` 均为合法 UTF-8 JSON。
- 游戏内测试：
  - `hide_effect_icons = true` 时 HUD 药水图标隐藏。
  - 物品栏旁边的药水效果列表仍然显示。
  - `hide_own_effect_particles = true` 时只隐藏自己身上的药水粒子。
  - `hide_first_person_fire = true` 时第一人称火焰遮挡隐藏。
  - `hide_lightning_bolt = true` 时闪电本体隐藏，但雷击伤害、引燃和避雷针等逻辑正常。
  - `hide_lightning_sky_flash = true` 时天空/雾闪白和地表光照闪烁隐藏。
  - `mute_lightning_sound = true` 时雷声与落雷撞击声静音，雨声正常。
  - 所有选项恢复默认值（前三个改 `false`，后三个保持 `false`）时原版效果正常。

常用检查命令：

```powershell
.\gradlew.bat clean build
Get-Content src\main\resources\assets\hide_effect_icons\lang\zh_cn.json -Encoding UTF8 | ConvertFrom-Json | Out-Null
Get-Content src\main\resources\assets\hide_effect_icons\lang\en_us.json -Encoding UTF8 | ConvertFrom-Json | Out-Null
& 'C:\Program Files\Java\jdk-21.0.10\bin\jar.exe' tf build\libs\hide_effect_icons-1.2.0.jar
```

## 升级 Minecraft 或 NeoForge 时的注意事项

升级版本后，mixin 目标方法或事件签名可能变化。重点检查：

- `Gui#renderEffects(GuiGraphics, DeltaTracker)`
- `LivingEntity#tickEffects`
- `ScreenEffectRenderer#renderFire(Minecraft, PoseStack)`
- `LightningBoltRenderer#render(LightningBolt, float, float, PoseStack, MultiBufferSource, int)`
- `ClientLevel#getSkyFlashTime()`
- `PlaySoundEvent` 与 `SoundEvents.LIGHTNING_BOLT_THUNDER` / `LIGHTNING_BOLT_IMPACT`

可以从 NeoForge 生成的源码 jar 中确认目标方法：

```text
build/moddev/artifacts/*-sources.jar
```

如果运行时 mixin 报错，优先检查：

- 方法名是否变化。
- 参数类型是否变化。
- 目标调用是否仍存在。
- `hide_effect_icons.mixins.json` 是否注册了正确类名。

## 当前交付状态

当前项目已经可以直接构建。最终 jar 仍输出到：

```text
build/libs/hide_effect_icons-1.2.0.jar
```

本仓库中的 `.gradle/`、`.gradle-dist/`、`build/` 是本地构建相关目录；接手开发时可以保留，也可以删除后重新构建生成。
