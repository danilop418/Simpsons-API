# Guía de Implementación: Rotación de Pantalla (Versión 3.0)

Según la [documentación oficial de Android](https://developer.android.com/guide/topics/resources/runtime-changes), aquí tienes las mejores prácticas para soportar el modo horizontal (Landscape).

## 1. Diseño Adaptativo (Layouts)
No es necesario hacerlo todo por código. La forma correcta es usar **Recursos Alternativos**.
Android detecta automáticamente el giro y busca si existe un diseño específico para ello.

*   **Carpeta por defecto:** `app/src/main/res/layout/` (Modo Vertical)
*   **Carpeta Horizontal:** `app/src/main/res/layout-land/` (Modo Horizontal)

**Cómo hacerlo:**
1.  Crea la carpeta `layout-land`.
2.  Copia tu archivo XML (ej: `activity_main.xml`) dentro.
3.  Modifica el diseño del XML de `layout-land` para que se vea bien en horizontal (ej: poner elementos lado a lado en vez de uno arriba de otro).

![Alternative Layouts](C:\Users\Usuario\.gemini\antigravity\brain\6a743b1c-036d-437b-8d34-d59e3b98e0a7\alternative_layouts_orientation_1768120101649.png)

## 2. No perder datos al girar (State Loss)
Cuando giras el móvil, **Android destruye tu actividad y la crea de nuevo** (para cargar el layout nuevo).
Esto significa que si el usuario estaba escribiendo un texto, se borrará si no tienes cuidado.

**Solución Recomendada: `ViewModel`**
No guardes datos en la pantalla (Activity/Fragment). Guárdalos en un `ViewModel`.
El `ViewModel` sobrevive al giro de pantalla.

![State Retention](C:\Users\Usuario\.gemini\antigravity\brain\6a743b1c-036d-437b-8d34-d59e3b98e0a7\state_retention_options_1768120486416.png)

## 3. Manifest (Qué NO hacer)
A veces verás tutoriales que te dicen que añadas `android:screenOrientation="portrait"` para bloquear el giro, o `android:configChanges` para gestionarlo tú manualmente.
**Google NO recomienda esto** a menos que sea un juego o una app muy específica (cámara, etc.).

Si quieres que tu app se adapte de verdad (como Gmail o YouTube), deja que el sistema maneje la rotación y usa los puntos 1 y 2.

![Manifest Orientation](C:\Users\Usuario\.gemini\antigravity\brain\6a743b1c-036d-437b-8d34-d59e3b98e0a7\manifest_screen_orientation_1768120303238.png)

## 4. Jetpack Compose (La Opción Moderna)
Si usas Jetpack Compose, olvídate de los XML. Compose maneja esto con **código reactivo**.

### Opción A: Detectar Orientación (`LocalConfiguration`)
Es la forma más rápida de saber si estás en vertical u horizontal.

```kotlin
val configuration = LocalConfiguration.current
when (configuration.orientation) {
    Configuration.ORIENTATION_LANDSCAPE -> {
        // Muestra diseño horizontal (ej. Row)
    }
    else -> {
        // Muestra diseño vertical (ej. Column)
    }
}
```
![Compose Local Configuration](C:\Users\Usuario\.gemini\antigravity\brain\6a743b1c-036d-437b-8d34-d59e3b98e0a7\compose_local_configuration_1768120994909.png)

### Opción B: Diseño Adaptativo Real (`BoxWithConstraints`)
Si quieres algo más fino (ej: cambiar el diseño si el ancho es > 600dp, no solo por girar), usa esto.
Te da el `maxWidth` disponible en tiempo real.

```kotlin
BoxWithConstraints {
    if (maxWidth < 600.dp) {
        // Diseño compacto (Móvil vertical)
    } else {
        // Diseño expandido (Tablet o Móvil horizontal)
    }
}
```
![Compose BoxWithConstraints](C:\Users\Usuario\.gemini\antigravity\brain\6a743b1c-036d-437b-8d34-d59e3b98e0a7\compose_box_with_constraints_1768121137805.png)

### Opción C: Window Size Classes (Recomendado por Google)
Para apps profesionales, Google recomienda usar clases de tamaño de ventana. Es la forma más robusta y funciona en móviles, tablets, plegables y multi-ventana.

![Window Size Classes](C:\Users\Usuario\.gemini\antigravity\brain\6a743b1c-036d-437b-8d34-d59e3b98e0a7\window_size_classes_concepts_1768121210596.png)

**Paso 1: Añade la dependencia**
```gradle
dependencies {
    implementation("androidx.compose.material3.adaptive:adaptive:1.0.0")
}
```

**Paso 2: Úsalo en tu Composable**
```kotlin
@Composable
fun MyApp(
    windowSizeClass: WindowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
) {
    when (windowSizeClass.windowWidthSizeClass) {
        WindowWidthSizeClass.COMPACT -> {
            // Móvil vertical: Una columna
            Column { /* ... */ }
        }
        WindowWidthSizeClass.MEDIUM -> {
            // Móvil horizontal o tablet pequeña: Dos columnas
            Row { /* ... */ }
        }
        WindowWidthSizeClass.EXPANDED -> {
            // Tablet grande: Tres columnas o diseño maestro-detalle
            Row { /* ... */ }
        }
    }
}
```

**¿Por qué es mejor?**
- Funciona automáticamente en multi-ventana y plegables.
- Google lo usa en Gmail, Play Store, etc.
- Se adapta al ancho real, no solo a la orientación.

## Proceso de Investigación (Capturas Completas)
Si quieres ver todo el proceso de investigación que hice en la documentación oficial de Android, aquí están las grabaciones completas:

````carousel
![Investigación Android Rotation (Parte 1)](C:\Users\Usuario\.gemini\antigravity\brain\6a743b1c-036d-437b-8d34-d59e3b98e0a7\android_rotation_research_1768119983265.webp)
<!-- slide -->
![Investigación Compose Rotation (Parte 2)](C:\Users\Usuario\.gemini\antigravity\brain\6a743b1c-036d-437b-8d34-d59e3b98e0a7\compose_rotation_research_1768120721729.webp)
<!-- slide -->
![Investigación Window Size Classes (Parte 3)](C:\Users\Usuario\.gemini\antigravity\brain\6a743b1c-036d-437b-8d34-d59e3b98e0a7\window_size_class_code_1768121451035.webp)
````

## Resumen
1.  **Diseño XML:** Copia tus XML a la carpeta `layout-land`.
2.  **Diseño Compose:** Usa `LocalConfiguration` o `BoxWithConstraints`.
3.  **Lógica:** Usa `ViewModel` siempre.
4.  **Config:** No toques el Manifest.
