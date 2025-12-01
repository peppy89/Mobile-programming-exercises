# Shopping List Room DB App

An Android application utilizing **Room Database** for local persistence and **Jetpack Compose** for the UI. It allows users to manage a shopping list with item details.

## Features

- **Database:** Uses SQLite via the Room abstraction layer.
- **UI:** Built with Jetpack Compose Table layout.
- **CRUD Operations:**
  - **Create:** Add items with Name, Quantity, Unit, and Price.
  - **Read:** Live updates of the list using Kotlin Flows.
  - **Delete:** Remove items from the database.
- **Architecture:** Follows the MVVM pattern (Model-View-ViewModel).

## Tech Stack

- Kotlin 2.0.21
- Jetpack Compose
- Room KTX 2.6.1
- KSP (Kotlin Symbol Processing)
- Android API 35

## Setup

1. Clone the repo.
2. Ensure Gradle uses KSP plugin version `2.0.21-1.0.27`.
3. Sync Gradle and Run on Emulator.
