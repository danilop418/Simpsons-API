# Guía de Diseño de Logos para Apps

## Fuentes Originales

### Material Design 3 - Sistema de Colores
🔗 [m3.material.io/styles/color/overview](https://m3.material.io/styles/color/overview)

![Material Design 3 - Sistema de colores base vs dinámico](C:/Users/Usuario/.gemini/antigravity/brain/2d84c4fd-8056-4eae-8b2b-9952372b81bb/m3_color_overview_1768037208259.png)

![Material Design 3 - Tonos accesibles con contraste 3:1](C:/Users/Usuario/.gemini/antigravity/brain/2d84c4fd-8056-4eae-8b2b-9952372b81bb/m3_accessibility_tones_1768037270954.png)

![Material Design 3 - Roles de color y niveles de contraste](C:/Users/Usuario/.gemini/antigravity/brain/2d84c4fd-8056-4eae-8b2b-9952372b81bb/m3_color_roles_contrast_1768037292914.png)

---

### Psicología del Color Azul
🔗 [verywellmind.com - Psychology of Blue](https://www.verywellmind.com/the-color-psychology-of-blue-2795815)

![Psicología del azul - Significados culturales](C:/Users/Usuario/.gemini/antigravity/brain/2d84c4fd-8056-4eae-8b2b-9952372b81bb/blue_psychology_effects_1768037415963.png)

![Psicología del azul - Efectos fisiológicos y marketing](C:/Users/Usuario/.gemini/antigravity/brain/2d84c4fd-8056-4eae-8b2b-9952372b81bb/blue_marketing_design_1768037472370.png)

---

## Principios Clave

| Principio | Descripción |
|-----------|-------------|
| **Simplicidad** | Un elemento central, 2-3 colores máximo |
| **Escalabilidad** | Funciona desde 16px hasta 512px |
| **Contraste 3:1** | Mínimo entre elementos para accesibilidad |
| **Tonos relacionados** | Usar variantes del mismo color para profundidad |

---

## Por Qué Azul para NADIR

- ✅ Reduce frecuencia cardíaca y presión arterial
- ✅ Asociado con tranquilidad en todas las culturas
- ✅ Aumenta productividad y creatividad
- ✅ Transmite confianza y profesionalidad

---

## Propuestas de Paleta

### Opción A: Bicolor Armónico
| Rol | Color | Hex |
|-----|-------|-----|
| Principal | Azul cielo | `#5B9BD5` |
| Profundidad | Azul océano | `#2E75B6` |
| Sparkle | Blanco | `#FFFFFF` |

### Opción B: Monocromático ✅ (Seleccionada)
| Rol | Color | Hex |
|-----|-------|-----|
| Principal | Azul pastel | `#7EB8DA` |
| Sombra | Azul medio | `#4A90B8` |
| Sparkle | Blanco | `#FFFFFF` |

### Opción C: Tu paleta actual
| Rol | Color | Hex |
|-----|-------|-----|
| Principal | Primary actual | `#4A7B9D` |
| Variante | Más claro | `#6B9EBC` |
| Sparkle | Blanco | `#FFFFFF` |

---

## Android Adaptive Icons - Especificaciones

🔗 [developer.android.com - Adaptive Icons](https://developer.android.com/develop/ui/views/launch/icon_design_adaptive)

![Android Adaptive Icon - Capas y zona segura](C:/Users/Usuario/.gemini/antigravity/brain/2d84c4fd-8056-4eae-8b2b-9952372b81bb/android_adaptive_icon_layers_1768038451912.png)

![Android Adaptive Icon - Especificaciones de tamaño](C:/Users/Usuario/.gemini/antigravity/brain/2d84c4fd-8056-4eae-8b2b-9952372b81bb/android_adaptive_icon_specs_1768038511280.png)

### Dimensiones de Capas (Foreground y Background)

| Elemento | Tamaño | Descripción |
|----------|--------|-------------|
| **Capa total** | 108 x 108 dp | Foreground y background deben ser de este tamaño |
| **Zona segura** | 66 x 66 dp | Área central que nunca será recortada |
| **Reserva externa** | 18 dp | Cada lado reservado para máscaras y efectos |
| **Logo mínimo** | 48 x 48 dp | Tamaño mínimo del elemento principal |
| **Logo máximo** | 66 x 66 dp | No exceder para evitar recortes |

### Google Play Store

| Especificación | Valor |
|----------------|-------|
| **Tamaño** | 512 x 512 px |
| **Formato** | PNG 32-bit con alpha |
| **Espacio color** | sRGB |
| **Peso máximo** | 1024 KB |
| **Forma** | Cuadrado (Google aplica esquinas) |
| **Sombras** | NO incluir (Google las aplica) |

### Configuración en Android Studio

**Background Layer:**
- Asset type: Color
- Color: `#1E2A32` (Navy oscuro, igual al fondo de la app)

**Foreground Layer:**
- Asset type: Image
- Source: El logo PNG sin fondo
