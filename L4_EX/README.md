# Product Banner App (Android, Kotlin + XML)

This project is a simple Android app that displays a **product banner** with:

- A product image in the **background**
- **Product name**
- **Company name**
- **Contact information** (email / phone)
- Proper alignment and layout using `ConstraintLayout` and `LinearLayout`

The UI is defined in XML and rendered by a classic `AppCompatActivity`.

---

## Tech Stack

- **Language:** Kotlin  
- **UI:** XML layouts  
- **Activity:** `AppCompatActivity`  
- **Layout widgets:** `ConstraintLayout`, `ImageView`, `LinearLayout`, `TextView`

---

## Layout Overview

`res/layout/activity_main.xml`:

- Root: `ConstraintLayout`
- Full-screen `ImageView` using `@drawable/image_test` as the **background**
- Bottom-aligned `LinearLayout` overlay (semi-transparent) containing:
  - `TextView` – product name  
  - `TextView` – company name  
  - `TextView` – contact info  

This creates a banner-style screen where text appears on top of the image.

---

## Main Activity

`MainActivity.kt`:

```kotlin
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
