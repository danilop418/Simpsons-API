# Simpsons API

Simpsons API es una aplicación Android desarrollada en Kotlin que consume una API de datos de personajes de Los Simpson. La aplicación sigue una arquitectura modular, separando capas de presentación, dominio y datos, y utiliza buenas prácticas de desarrollo en Android.

## Estructura del proyecto

El proyecto está organizado en módulos y carpetas para facilitar la escalabilidad y el mantenimiento. La estructura principal incluye:

- `app/src/main/java/com/example/simpsons/features/simpsons/core/api`: Contiene las interfaces y definiciones de la API.
- `app/src/main/java/com/example/simpsons/features/simpsons/data/remote/api`: Implementación de los servicios remotos y clientes HTTP.
- `app/src/main/java/com/example/simpsons/features/simpsons/domain`: Contiene la lógica de negocio y entidades del dominio.
- `app/src/main/java/com/example/simpsons/features/simpsons/presentation`: Maneja la interfaz de usuario, con subcarpetas para la lista de personajes, detalle y flujo de inicialización.
- `app/src/main/res`: Recursos de la aplicación, incluyendo layouts, imágenes, fuentes y valores de strings y estilos.

## Características

Simpsons API permite:

- Listar personajes de Los Simpson obtenidos de un API remoto.
- Visualizar detalles de cada personaje.
- Navegar entre pantallas de manera fluida y estructurada.

## Tecnologías utilizadas

- Kotlin como lenguaje principal.
- Android Studio como entorno de desarrollo.
- Arquitectura modular con capas de presentación, dominio y datos.
- Librerías de Android modernas y gestión de dependencias mediante Gradle.

## Ejecución

Para ejecutar la aplicación:

1. Clonar el repositorio.
2. Abrir el proyecto en Android Studio.
3. Sincronizar Gradle y asegurarse de que el SDK de Android esté correctamente configurado.
4. Ejecutar la app en un emulador o dispositivo físico.

## Testing

El proyecto incluye pruebas unitarias y de instrumentación ubicadas en:

- `app/src/test/java/com/example/simpsons`
- `app/src/androidTest/java/com/example/simpsons`

## Screeshot

![Animation](https://github.com/user-attachments/assets/fdf27888-9edf-4a2a-918f-05771eeb986e)

