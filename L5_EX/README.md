# L5_EX – Multi Counter App (Jetpack Compose)

This is a simple Android app written in **Kotlin** using **Jetpack Compose**.

The app shows **three independent counters** in a column.  
Each counter row contains:

- a **“-” button** to decrease the value  
- a **TextField** where you can type the starting value  
- a **“+” button** to increase the value  

Each counter works independently from the others.

---

## Features

- Built with **Jetpack Compose** and **Material 3**.
- Three counters displayed in a vertical **Column**.
- Each counter row (`CounterRow`) contains:
  - `Button` with text `-`
  - `TextField` showing the current value (editable)
  - `Button` with text `+`
- The text field accepts only:
  - empty text, or  
  - valid integer numbers (`toIntOrNull` check).
- The counters are visually centered vertically on the screen.

---

## Main Code

### MainActivity

```kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    MultiCounterScreen()
                }
            }
        }
    }
}
