# BuildSrc Convention Plugins

Этот модуль содержит convention plugins для упрощения конфигурации Gradle модулей в проекте FlowersOfLife.

## Доступные плагины

### 1. `flowersoflife.android-library`
**Базовый плагин для Android библиотек**

Автоматически настраивает:
- Android Gradle Plugin
- Kotlin Android Plugin
- `compileSdk = 35`
- `minSdk = 26`
- Java compatibility 19
- Kotlin jvmTarget 19
- ProGuard конфигурацию

**Использование:**
```kotlin
plugins {
    id("flowersoflife.android-library")
}

android {
    namespace = "com.kuzmin.flowersoflife.your.module"
}
```

### 2. `flowersoflife.android-compose`
**Плагин для модулей с Jetpack Compose**

Дополнительно к базовому плагину настраивает:
- Jetpack Compose buildFeatures
- Compose Compiler версия 1.5.1

**Использование:**
```kotlin
plugins {
    id("flowersoflife.android-compose")
}

android {
    namespace = "com.kuzmin.flowersoflife.your.module"
}
```

### 3. `flowersoflife.android-hilt`
**Плагин для модулей с Dagger Hilt**

Автоматически настраивает:
- Hilt Android Plugin
- Kapt Plugin
- Hilt зависимости (implementation + kapt)
- Kapt correctErrorTypes = true

**Использование:**
```kotlin
plugins {
    id("flowersoflife.android-library")
    id("flowersoflife.android-hilt")
}

android {
    namespace = "com.kuzmin.flowersoflife.your.module"
}
```

### 4. `flowersoflife.android-feature`
**Комплексный плагин для feature модулей**

Включает в себя:
- `flowersoflife.android-library`
- `flowersoflife.android-compose`
- `flowersoflife.android-hilt`
- Navigation Compose
- Hilt Navigation
- Packaging конфигурацию

**Использование:**
```kotlin
plugins {
    id("flowersoflife.android-feature")
}

android {
    namespace = "com.kuzmin.flowersoflife.feature.yourfeature"
}

dependencies {
    // Ваши специфичные зависимости
    implementation(project(":core:domain"))
    implementation(project(":core:ui"))
}
```

## Примеры использования

### Feature модуль (auth, home, tasks, etc.)
```kotlin
plugins {
    id("flowersoflife.android-feature")
}

android {
    namespace = "com.kuzmin.flowersoflife.feature.auth"
}

dependencies {
    implementation(project(":core:navigation"))
    implementation(project(":core:domain"))
    implementation(project(":core:ui"))
    api(project(":common"))
}
```

### Core модуль без Compose
```kotlin
plugins {
    id("flowersoflife.android-library")
    id("flowersoflife.android-hilt")
}

android {
    namespace = "com.kuzmin.flowersoflife.core.domain"
}

dependencies {
    api(project(":common"))
}
```

### Core модуль с Compose
```kotlin
plugins {
    id("flowersoflife.android-compose")
    id("flowersoflife.android-hilt")
}

android {
    namespace = "com.kuzmin.flowersoflife.core.ui"
}

dependencies {
    api(project(":common"))
}
```

### Data Provider модуль
```kotlin
plugins {
    id("flowersoflife.android-library")
    id("flowersoflife.android-hilt")
}

android {
    namespace = "com.kuzmin.flowersoflife.data_provider"
}

dependencies {
    api(project(":common"))
    implementation(project(":core:network"))
    implementation(project(":core:domain"))
}
```

## Преимущества

✅ **Меньше дублирования кода** - общая конфигурация в одном месте  
✅ **Единообразие** - все модули настроены одинаково  
✅ **Легкость поддержки** - изменения применяются ко всем модулям  
✅ **Быстрая настройка** - новые модули создаются за секунды  
✅ **Type-safe** - ошибки видны на этапе компиляции  

## Структура

```
buildSrc/
├── build.gradle.kts                    # Конфигурация buildSrc
├── settings.gradle.kts                 # Settings для buildSrc
├── README.md                           # Эта документация
└── src/main/kotlin/
    ├── AndroidLibraryConventionPlugin.kt
    ├── AndroidComposeConventionPlugin.kt
    ├── AndroidHiltConventionPlugin.kt
    └── AndroidFeatureConventionPlugin.kt
```

## Обновление плагинов

Чтобы обновить конфигурацию для всех модулей:

1. Отредактируйте нужный plugin в `buildSrc/src/main/kotlin/`
2. Gradle автоматически пересоберет buildSrc
3. Все модули получат обновления

Например, чтобы обновить `compileSdk` для всех модулей:
```kotlin
// В AndroidLibraryConventionPlugin.kt
compileSdk = 36  // Изменить здесь один раз
```

