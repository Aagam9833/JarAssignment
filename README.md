# Jar Assignment

A simple Android application project for the Jar Assignment showing onboarding splash screens. The project uses a robust, maintainable **Clean Architecture** based on **MVVM (Model-View-ViewModel)** with clear separation into Data, Domain, and Presentation layers.

***

## 🚀 How to Run the Project

The easiest and most reliable way to run this project is by using the specified version of Android Studio.

1.  **Clone the repository:**
    Open your terminal or command prompt and clone the project:
    ```bash
    git clone [https://github.com/Aagam9833/JarAssignment.git](https://github.com/Aagam9833/JarAssignment.git)
    ```
2.  **Open in Android Studio:**
    * Launch the specified version of **Android Studio**.
    * Select **"Open"** and choose the cloned `JarAssignment` directory.
3.  **Sync Gradle:**
    Android Studio will automatically sync the project and download all dependencies.
4.  **Run:**
    * Ensure an **Android Virtual Device (Emulator)** or a physical device running **Android 7.0 (API 24)** or newer is selected.
    * Click the **Run** button (the green play arrow).

***

## 🛠️ Dependencies and Setup Required

To ensure a smooth and reproducible build, the following component versions are used:

| Component | Required Version | Source |
| :--- | :--- | :--- |
| **Android Studio** | **Narwhal 3 Feature Drop \| 2025.1.3** | Development Environment |
| **Java/JDK Version** | **11** | `app/build.gradle` (`sourceCompatibility`) |
| **Gradle Version** | **8.13** | `gradle/wrapper/gradle-wrapper.properties` |
| **Target SDK** | **36** | `app/build.gradle` (`targetSdk`) |
| **Minimum SDK** | **24** (Android 7.0 - Nougat) | `app/build.gradle` (`minSdk`) |

***

## 📝 Additional Notes on Implementation

### Architecture Overview

The project adheres to a standard **Clean Architecture** approach using **MVVM** across three distinct layers:

* **Presentation Layer:** Contains UI elements (Activities, Fragments) and **ViewModels** which manage UI state.
* **Domain Layer:** Holds the core **Use Cases** (business logic) and defines the **Repository Interfaces**.
* **Data Layer:** Contains **Data Sources** and the **Repository Implementation** which fetches, stores, and maps data according to the Domain interfaces.

### Key Implementation Details

| Feature | Technology Used | Implementation Notes |
| :--- | :--- | :--- |
| **Asynchronicity** | **Kotlin Coroutines** | Used throughout the Data and Domain layers for thread management and non-blocking operations. |
| **Data Management** | **Repository Pattern** | The Data layer implements a clean **Repository** to abstract data fetching from its source (e.g., local storage or network). |
| **Dependency Injection** | **Hilt** | Used to manage and provide dependencies (like Repositories and Use Cases) across all layers, ensuring loose coupling. |
| **Language** | **Kotlin** | The entire codebase is primarily written in Kotlin. |

---

**Note:** Tilted card animation functionality is yet to be implemented.
