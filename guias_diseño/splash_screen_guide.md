# Guía de Splash Screen - Android API Oficial

## Fuente Oficial

🔗 [developer.android.com - Splash Screen](https://developer.android.com/develop/ui/views/launch/splash-screen)

---

## ¿Por qué usar la API oficial?

A partir de **Android 12**, el sistema gestiona automáticamente la pantalla de presentación. Si usas una pantalla custom (como un Composable separado), tendrás **dos splash screens**: la del sistema + la tuya.

![Android Splash Screen Overview](C:/Users/Usuario/.gemini/antigravity/brain/2d84c4fd-8056-4eae-8b2b-9952372b81bb/splash_screen_overview_1768039262422.png)

---

## Elementos del Splash Screen

![Elementos del Splash Screen](C:/Users/Usuario/.gemini/antigravity/brain/2d84c4fd-8056-4eae-8b2b-9952372b81bb/splash_screen_elements_1768039291628.png)

| Elemento | Descripción | Tamaño |
|----------|-------------|--------|
| **App Icon** | Tu icono adaptable | 240 x 240 dp |
| **Icon Background** | Fondo circular del icono | Opcional |
| **Window Background** | Fondo de toda la pantalla | Color sólido |

---

## Implementación

![Pasos de implementación](C:/Users/Usuario/.gemini/antigravity/brain/2d84c4fd-8056-4eae-8b2b-9952372b81bb/splash_screen_usage_steps_1768039405049.png)

### 1. Añadir dependencia

```kotlin
// build.gradle.kts (app)
dependencies {
    implementation("androidx.core:core-splashscreen:1.0.1")
}
```

### 2. Crear tema del Splash

```xml
<!-- res/values/themes.xml -->
<style name="Theme.App.Starting" parent="Theme.SplashScreen">
    <item name="windowSplashScreenBackground">@color/splash_background</item>
    <item name="windowSplashScreenAnimatedIcon">@drawable/ic_launcher_foreground</item>
    <item name="postSplashScreenTheme">@style/Theme.YourApp</item>
</style>
```

### 3. Aplicar en AndroidManifest

```xml
<activity
    android:name=".MainActivity"
    android:theme="@style/Theme.App.Starting">
```

### 4. Instalar en MainActivity

```kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        // resto de tu código...
    }
}
```

---

## Eliminar tu SplashScreen custom

Una vez implementada la API oficial, puedes **eliminar**:
- `SplashScreen.kt` (el Composable)
- La navegación inicial al splash
- El delay artificial de 2500ms

El sistema gestiona automáticamente la transición.

---

## Personalización avanzada

```kotlin
splashScreen.setKeepOnScreenCondition { 
    // Mantener splash mientras carga datos
    viewModel.isLoading.value 
}

splashScreen.setOnExitAnimationListener { splashScreenView ->
    // Animación de salida personalizada
    splashScreenView.remove()
}
```
