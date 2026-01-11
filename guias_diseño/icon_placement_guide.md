# Guía de Posicionamiento de Iconos - MD3

## Fuentes Oficiales

🔗 [m3.material.io/foundations/layout/spacing](https://m3.material.io/foundations/layout/understanding-layout/spacing)
🔗 [developer.android.com/edge-to-edge](https://developer.android.com/develop/ui/views/layout/edge-to-edge)

---

## El Problema: Icono muy cerca del Status Bar

![MD3 Margins](C:/Users/Usuario/.gemini/antigravity/brain/2d84c4fd-8056-4eae-8b2b-9952372b81bb/md3_layout_spacing_1768041163353.png)

![Window Insets en Android](C:/Users/Usuario/.gemini/antigravity/brain/2d84c4fd-8056-4eae-8b2b-9952372b81bb/android_window_insets_1768041264489.png)

---

## Especificaciones MD3

| Elemento | Valor | Descripción |
|----------|-------|-------------|
| **Margen lateral** | 16-24 dp | Espacio desde el borde de pantalla |
| **Tamaño icono** | 24 x 24 dp | Tamaño visual del icono |
| **Touch target** | 48 x 48 dp | Área mínima para tocar (accesibilidad) |
| **Padding icono** | 12 dp | Alrededor del icono para alcanzar 48dp |
| **Grid base** | 4 dp | Todos los espaciados en múltiplos de 4 |

---

## WindowInsets - La Solución

En Android 15+ las apps son edge-to-edge por defecto. Debes usar `WindowInsets` para evitar que el contenido quede debajo del status bar.

**Tipos de Insets:**
- `statusBars` - Espacio del status bar (batería, hora)
- `navigationBars` - Barra de navegación inferior
- `safeDrawing` - Combinación segura para dibujar

---

## Implementación en Compose

```kotlin
IconButton(
    onClick = { ... },
    modifier = Modifier
        .align(Alignment.TopEnd)
        .statusBarsPadding()  // ← AÑADIR ESTO
        .padding(16.dp)       // Padding adicional
) {
    Icon(...)
}
```

O para toda la pantalla:

```kotlin
Box(
    modifier = Modifier
        .fillMaxSize()
        .windowInsetsPadding(WindowInsets.safeDrawing)
) {
    // Contenido seguro
}
```

---

## Tu Caso Específico (NADIR)

**Antes:** `padding(24.dp)` - No considera el status bar

**Después:**
```kotlin
IconButton(
    modifier = Modifier
        .align(Alignment.TopEnd)
        .statusBarsPadding()
        .padding(horizontal = 16.dp, vertical = 8.dp)
)
```

Esto añade automáticamente el padding necesario para que el icono quede debajo del status bar, más 16dp horizontal y 8dp vertical extra.
