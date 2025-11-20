# Multi Counter App (Jetpack Compose)

A simple Android app written in **Kotlin** using **Jetpack Compose**.

The app shows **three independent counters** in a column.  
Each counter has:

- a **“-” button** to decrease the value  
- a **text field** where you can type the starting value  
- a **“+” button** to increase the value  

Each counter works independently from the others.

---

## Features

- Built with **Jetpack Compose** and **Material 3**.
- Three counters displayed in a column.
- Each counter row is composed of:
  - `Button` with text `-`
  - `TextField` displaying the current value (editable)
  - `Button` with text `+`
- The text field accepts only integer values (or empty text).

- `app/src/main/res/values/themes.xml`
  - Defines the app theme used in `AndroidManifest.xml` (e.g. `@style/Theme.L5_EX`).
