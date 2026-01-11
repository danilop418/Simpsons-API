# Guía de Colores para Apps de Salud - Esfera de Respiración

## Fuentes Oficiales

🔗 [m3.material.io/usability/applying-m-3-expressive](https://m3.material.io/foundations/usability/applying-m-3-expressive)
🔗 [Psicología del color azul](https://www.verywellmind.com/the-color-psychology-of-blue-2795815)

---

## Ejemplo de MD3: Breathing Session

![MD3 Breathing Session Example](C:/Users/Usuario/.gemini/antigravity/brain/2d84c4fd-8056-4eae-8b2b-9952372b81bb/md3_breathing_exercise_design_1768041341525.png)

MD3 usa una **flor orgánica** con gradiente amarillo-verde suave para la esfera de respiración. La forma se expande/contrae visualmente para guiar la inhalación/exhalación.

---

## Paletas de Wellness Apps

![Wellness App Color Palettes](C:/Users/Usuario/.gemini/antigravity/brain/2d84c4fd-8056-4eae-8b2b-9952372b81bb/wellness_app_color_palettes_images_1768045515847.png)

---

## Psicología del Color para Respiración

| Color | Efecto Psicológico | Uso Recomendado |
|-------|-------------------|-----------------|
| **Azul claro** | Calma, reduce frecuencia cardíaca | Inhalar (expansión) |
| **Azul medio** | Estabilidad, confianza | Hold (mantener) |
| **Verde menta** | Frescura, renovación | Exhalar (liberación) |
| **Lavanda** | Relajación profunda | Transiciones |

---

## Opciones de Paleta para NADIR

### Opción 1: Monocromático Azul (Actual)
| Fase | Color | Hex |
|------|-------|-----|
| Inhale | Azul cielo | `#7EC8E3` |
| Hold | Azul medio | `#4A90B8` |
| Exhale | Azul océano | `#2E5A7C` |

### Opción 2: Azul-Verde (Como Calm)
| Fase | Color | Hex |
|------|-------|-----|
| Inhale | Turquesa claro | `#6EC5B8` |
| Hold | Turquesa medio | `#4AAFA0` |
| Exhale | Verde azulado | `#2E8B7A` |

### Opción 3: Gradiente Cálido (Como MD3 Aura)
| Fase | Color | Hex |
|------|-------|-----|
| Inhale | Amarillo suave | `#F4E4BA` |
| Hold | Melocotón | `#E8C4A0` |
| Exhale | Naranja pastel | `#D4A574` |

---

## Teoría del Color - Fundamentos

**Colores fríos (azul, verde, violeta):**
- Reducen frecuencia cardíaca
- Bajan presión arterial
- Inducen calma y concentración

**Colores cálidos (amarillo, naranja, rojo):**
- Aumentan energía
- Estimulan creatividad
- Mejor para "despertar" o finalizar

---

## Recomendación para NADIR

Dado que NADIR es para **reducir ansiedad**, recomiendo:

**Opción 2 (Azul-Verde)** porque:
1. Mantiene la identidad azul actual
2. El verde añade sensación de "renovación" en la exhalación
3. Apps como Calm y Headspace usan esta combinación
4. Científicamente asociado con reducción de estrés

**Implementación:**
```kotlin
object BreathingColors {
    val inhale = Color(0xFF6EC5B8)  // Turquesa claro
    val hold = Color(0xFF4AAFA0)    // Turquesa medio  
    val exhale = Color(0xFF2E8B7A)  // Verde azulado
}
```
