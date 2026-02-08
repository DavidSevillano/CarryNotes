# CarryNotes 🚀 

**CarryNotes** es una aplicación nativa de Android diseñada para ofrecer una experiencia de toma de notas rápida, intuitiva y visualmente atractiva. Construida desde cero utilizando las tecnologías más modernas del ecosistema Android, la app se centra en la productividad y la organización personal.

[![Google Play](https://img.shields.io/badge/Google_Play-Disponible-green?logo=googleplay&logoColor=white)](https://play.google.com/store/apps/details?id=com.burixer.carrynotes)
![Kotlin](https://img.shields.io/badge/Kotlin-100%25-blue?logo=kotlin)
![Compose](https://img.shields.io/badge/Jetpack-Compose-orange?logo=jetpackcompose)

---

## ✨ Características Principales

- ✍️ **Notas Rápidas:** Captura ideas al instante con un flujo optimizado.
- 📂 **Organización por Categorías:** Clasifica tus pensamientos para mantener el orden (Trabajo, Personal, Ideas, etc.).
- 🏆 **Sistema de Logros:** Capa de gamificación que premia tu constancia y nivel de organización.
- 🌍 **Soporte Multi-idioma:** Localización completa en **Inglés** y **Español**.
- 🔒 **Privacidad Total:** Los datos se almacenan localmente en el dispositivo mediante Room; la app funciona 100% offline.
- ⚡ **Interfaz Moderna:** Diseño basado en Material 3 con animaciones fluidas.

---

## 🛠 Stack Tecnológico

Este proyecto implementa las últimas librerías y patrones recomendados por Google:

- **UI:** [Jetpack Compose](https://developer.android.com/jetpack/compose) (interfaz declarativa).
- **Arquitectura:** **MVVM** (Model-View-ViewModel) para un código limpio y escalable.
- **Navegación:** **Navigation 3** (Type-safe), utilizando la última generación de la librería de navegación de Android para garantizar seguridad en tiempo de compilación.
- **Persistencia de Datos:** [Room Database](https://developer.android.com/training/data-storage/room) con **KSP** para una gestión de base de datos eficiente.
- **Asincronía:** Kotlin Coroutines & Flow para flujos de datos reactivos.
- **Serialización:** Kotlinx Serialization para la gestión de tipos de datos complejos.
- **Configuración SDK:**
  - `compileSdk: 36` (Android 16 Developer Preview) para compatibilidad con Navigation 3.
  - `targetSdk: 35` (Android 15 estable) para optimización en dispositivos actuales.

---

## 🏗️ Estructura del Proyecto

El código está organizado siguiendo los principios de **Clean Code**:

- `ui/`: Pantallas (Screens) y componentes de Compose.
- `viewmodel/`: Lógica de negocio y gestión de estado de la UI.
- `data/`: Entidades de Room, DAOs y Repositorios.
- `navigation/`: Configuración de rutas seguras con Navigation 3.

---

## 📈 Roadmap (Próximos Pasos)

- [x] Lanzamiento oficial v1.1
- [x] Implementación de Sistema de Logros
- [x] Soporte Multi-idioma (ES/EN)

---

## 👤 Autor

**BuriDeveloper** 
- **Google Play:** [Descarga CarryNotes](https://play.google.com/store/apps/details?id=com.burixer.carrynotes)
- **LinkedIn:** [Mi Perfil de LinkedIn](https://www.linkedin.com/in/david-sevillano-domínguez-a7a432244/)

---

## 📄 Licencia

Este proyecto está bajo la Licencia MIT.
