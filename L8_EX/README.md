# Android Weather Stack App

A lightweight Android app built with Kotlin and Jetpack Compose that retrieves live weather information from the OpenWeatherMap API and displays it in a vertically scrollable list.
## Features

- **API Integration:** Utilizes Retrofit and Gson to request and parse weather data from OpenWeatherMap.
- **Concurrency:** Leverages Kotlin Coroutines and viewModelScope to handle background network operations.
- **UI:** Entirely composed with Jetpack Compose, using a LazyColumn to present weather entries efficiently.
- **Architecture:** Implements the MVVM (Model–View–ViewModel) design pattern for clean separation of concerns.

## Prerequisites

- Android Studio Hedgehog or newer.
- A valid OpenWeatherMap API key.

## Setup

1. Clone the repository.
2. Open `MainActivity.kt`.
3. In the WeatherViewModel class, find the private val apiKey variable.
4. Replace `"YOUR_OPENWEATHERMAP_API_KEY_HERE"` with your valid API key.
5. Sync your Gradle files and run the app on a device or emulator.

## Dependencies

- Retrofit 2.9.0
- Gson Converter 2.9.0
- Lifecycle ViewModel Compose 2.6.1
- Material3 Design
