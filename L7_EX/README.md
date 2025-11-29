# Multi Counter App (Jetpack Compose)
A simple Android app built with Kotlin and Jetpack Compose.
The app displays a scrollable list of independent counters using LazyColumn.
Each counter can be incremented, decremented, added, or removed at runtime.

---

## Features
### Multi-Counter List
- Displays counters labeled as Counter_1, Counter_2, etc.
- Each counter stores its own independent value.
- The list is scrollable using LazyColumn.

### Counter Controls
Each counter card includes:
➕ Increment
➖ Decrement
❌ Remove counter

➕ Optional Feature
A floating + button allows the user to add new counters dynamically.

---

## UI Overview
- The home screen shows a top bar titled "Multi Counter".
- Below it is a scrollable list of counters.
- Each counter card includes the name, current value, and buttons.
- A floating action button (FAB) adds new counters.

## Tech Stack
- Kotlin
- Jetpack Compose UI
- Material 3 Components

## How It Works
### State Management
The counters are stored in a Compose state list:
```bash
val counters = remember { mutableStateListOf(0, 0, 0, 0, 0) }
```
Each counter value updates independently due to Compose’s reactive state system.
### Adding a Counter
```bash
counters.add(0)
```
### Removing a Counter
```bash
counters.removeAt(index)
```
## Running the App
1. Open the project in Android Studio.
2. Sync Gradle if prompted.
3. Connect an Android device or start an emulator.
4. Click Run ▶️

## Requirements Met
✔ Scrollable list of counters using LazyColumn

✔ Each counter has its own independent state

✔ User can change each counter value independently

✔ Optional ability to add/remove counter items
