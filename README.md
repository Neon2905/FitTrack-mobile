# FitTrack-mobile

FitTrack-mobile is a modern Android fitness tracking app built with Kotlin, Jetpack Compose, Room, Hilt, and WorkManager. It helps users track daily activities, monitor progress, and achieve fitness goals.

## Features

- **Activity Tracking:** Log walking, running, cycling, and weightlifting sessions with step count, calories, distance, and duration.
- **Live & Manual Logging:** Start live tracking or manually log activities.
- **Daily Summaries:** Automatic aggregation of daily activity stats (steps, calories, distance, duration).
- **Statistics & Trends:** View historical data, filter by day/week/month/year, and visualize with charts.
- **Challenges:** Participate in fitness challenges and monitor progress.
- **User Goals:** Set and track daily goals for steps and calories.
- **Authentication:** Secure login and registration.
- **Data Sync:** Sync activities with a remote server and handle offline data.
- **Location Tracking:** Integrated with Google Maps for route visualization.
- **Modern UI:** Built with Jetpack Compose for a responsive, intuitive interface.

## Architecture

- **MVVM Pattern:** ViewModels manage UI state and business logic.
- **Room Database:** Local storage for activities, challenges, and summaries.
- **Dependency Injection:** Hilt for managing dependencies.
- **WorkManager:** Background workers for daily summaries and data sync.
- **Retrofit:** Networking for remote API calls.

## Main Modules

- [`FitTrackMobile`](app/src/main/java/com/fittrackapp/fittrack_mobile/FitTrackMobile.kt): Application class, initializes DI and background tasks.
- [`AppDatabase`](app/src/main/java/com/fittrackapp/fittrack_mobile/data/local/AppDatabase.kt): Room database setup.
- [`ActivityEntity`](app/src/main/java/com/fittrackapp/fittrack_mobile/data/local/entity/ActivityEntity.kt): Activity data model.
- [`ActivityDao`](app/src/main/java/com/fittrackapp/fittrack_mobile/data/local/dao/ActivityDao.kt): DAO for activity CRUD and queries.
- [`DashboardViewModel`](app/src/main/java/com/fittrackapp/fittrack_mobile/presentation/dashboard/DashboardViewModel.kt): Loads dashboard data.
- [`DailySummaryWorker`](app/src/main/java/com/fittrackapp/fittrack_mobile/auto_task/worker/DailySummaryWorker.kt): Worker for daily summary aggregation.
- [`SyncDataWorker`](app/src/main/java/com/fittrackapp/fittrack_mobile/auto_task/worker/SyncDataWorker.kt): Worker for syncing unsynced activities.

## Getting Started

1. **Clone the repository:**

    ```sh
    git clone https://github.com/yourusername/FitTrack-mobile.git
    cd FitTrack-mobile
    ```

2. **Open in Android Studio or VS Code.**

3. **Build the project.**

4. **Run the app on an Android device or emulator.**

## Requirements

- Android SDK 27+
- Kotlin 1.9+
- Google Maps API key (for location features) [Google Maps API key is already set on this version]

## Project Structure

- **`app/`**: Main Android app module containing source code, resources, and configuration files.
  - **`src/main/java/com/fittrackapp/fittrack_mobile/`**: Core application logic.
    - **[`data/`](app/src/main/java/com/fittrackapp/fittrack_mobile/data/)**: Data layer including local database, entities, DAOs, and remote API interfaces.
    - **[`presentation/`](app/src/main/java/com/fittrackapp/fittrack_mobile/presentation/)**: UI components, screens, and ViewModels for user interaction.
    - **[`auto_task/`](app/src/main/java/com/fittrackapp/fittrack_mobile/auto_task/)**: Background workers for tasks like daily summaries and data synchronization.
    - **[`di/`](app/src/main/java/com/fittrackapp/fittrack_mobile/di/)**: Dependency injection setup using Hilt modules.
    - **[`navigation/`](app/src/main/java/com/fittrackapp/fittrack_mobile/navigation/)**: Navigation Logics and composable [AppNavHost](app/src/main/java/com/fittrackapp/fittrack_mobile/navigation/AppNavHost.kt) for screen transitions.
    - **[`domain/`](app/src/main/java/com/fittrackapp/fittrack_mobile/domain/)**: Business logic layer including use cases and domain models.
    - **[`sheet/`](app/src/main/java/com/fittrackapp/fittrack_mobile/sheet/)**: Bottom sheet components and related UI logic.
  - **`src/main/res/`**: App resources such as layouts, drawables, and values.
  - **`src/main/AndroidManifest.xml`**: App manifest file defining permissions and components.
- **`build.gradle`**: Gradle build configuration for dependencies and plugins.
- **`README.md`**: Project documentation and usage instructions.
