# 开发交接文档

本文档用于帮助后续开发者快速接手这个 Minecraft 1.21.1 NeoForge 模组。

## 项目概览

这是一个纯客户端 NeoForge 模组，模组 ID 为 `hide_effect_icons`。

当前功能：

- 隐藏 HUD 右上角的药水/状态效果图标。
- 隐藏本地玩家自己身上的药水/状态效果粒子。
- 隐藏本地玩家着火时第一人称视角里的火焰遮挡。
- 生成带中文注释的客户端配置文件。
- 在 NeoForge 模组菜单的配置界面里显示中文名称和说明。

当前明确不做的事情：

- 不隐藏物品栏旁边的药水效果列表。
- 不移除或修改药水/状态效果本身。
- 不熄灭玩家，也不改变燃烧伤害。
- 不隐藏其他玩家身上的粒子。

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
build/libs/hide_effect_icons-1.0.0.jar
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
```

所有配置项默认值都是 `true`。

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
  "ScreenEffectRendererMixin"
]
```

新增 mixin 后必须在这里注册，否则不会生效。

### `zh_cn.json`

路径：

```text
src/main/resources/assets/hide_effect_icons/lang/zh_cn.json
```

职责：

- 给 NeoForge 内置配置界面提供中文标题、配置项名称和 tooltip。

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
```

把对应选项改成 `false` 即可恢复原版视觉效果。

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
- `build/libs/hide_effect_icons-1.0.0.jar` 存在。
- `hide_effect_icons.mixins.json` 只包含当前需要启用的 mixin。
- `zh_cn.json` 是合法 UTF-8 JSON。
- 游戏内测试：
  - `hide_effect_icons = true` 时 HUD 药水图标隐藏。
  - 物品栏旁边的药水效果列表仍然显示。
  - `hide_own_effect_particles = true` 时只隐藏自己身上的药水粒子。
  - `hide_first_person_fire = true` 时第一人称火焰遮挡隐藏。
  - 三个选项分别改成 `false` 后，原版视觉效果能恢复。

常用检查命令：

```powershell
.\gradlew.bat clean build
Get-Content src\main\resources\assets\hide_effect_icons\lang\zh_cn.json -Encoding UTF8 | ConvertFrom-Json | Out-Null
& 'C:\Program Files\Java\jdk-21.0.10\bin\jar.exe' tf build\libs\hide_effect_icons-1.0.0.jar
```

## 升级 Minecraft 或 NeoForge 时的注意事项

升级版本后，mixin 目标方法可能改名或签名变化。重点检查：

- `Gui#renderEffects(GuiGraphics, DeltaTracker)`
- `LivingEntity#tickEffects`
- `ScreenEffectRenderer#renderFire(Minecraft, PoseStack)`

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
build/libs/hide_effect_icons-1.0.0.jar
```

本仓库中的 `.gradle/`、`.gradle-dist/`、`build/` 是本地构建相关目录；接手开发时可以保留，也可以删除后重新构建生成。
