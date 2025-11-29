# L6_EX – Countries & Flags (Material 3)

This Android app displays the **name and flag of four countries** using
Material Design 3 cards.  
It was created as an exercise on:

- Material Design / Material 3 theming  
- Dynamic color & dark theme  
- Custom fonts  
- Changing the app launcher icon  
- Using `Column` instead of `LazyColumn`

---

## Features

- 🇫🇮 **Finland**, 🇯🇵 **Japan**, 🇧🇷 **Brazil**, 🇨🇦 **Canada**
  - Each country is shown in a **Card** with its flag and name.
- Cards are stacked using a **`Column`** (no `LazyColumn`).
- **Custom card style**
  - Rounded corners (`RoundedCornerShape`)
  - Custom background color based on `MaterialTheme.colorScheme.primaryContainer`
  - Elevation to create a raised look
- **Dynamic color + dark theme**
  - `dynamicDarkColorScheme` / `dynamicLightColorScheme` on Android 12+
  - Falls back to custom `lightColorScheme` / `darkColorScheme` on older devices
- **Custom font – Geo**
  - `Geo` font is added under `res/font`
  - Typography is customized in `Type.kt` (`AppTypography`) and applied via `MaterialTheme`
- **Custom app icon**
  - Launcher icon changed to a **globe** image  
    (`https://cdn-icons-png.flaticon.com/512/3626/3626838.png`)

---

## Technical Details

### UI

- Main screen: `CountriesScreen`
  - Uses `Column` with `Arrangement.spacedBy(...)` to control spacing between cards.
- Each item: `CountryCard`
  - Uses `Card` from Material 3 (`androidx.compose.material3.Card`)
  - Inside the card, a `Row` with:
    - `Image` for the flag (using `painterResource`)
    - `Text` for the country name (using `MaterialTheme.typography.titleMedium`, which uses Geo)

### Theming

- `ui/theme/Theme.kt`
  - `L6_EXTheme` composable:
    ```kotlin
    @Composable
    fun L6_EXTheme(
        darkTheme: Boolean = isSystemInDarkTheme(),
        dynamicColor: Boolean = true,
        content: @Composable () -> Unit
    )
    ```
  - Chooses between:
    - Dynamic color schemes (`dynamicDarkColorScheme`, `dynamicLightColorScheme`) on Android 12+
    - Custom `LightColorScheme` / `DarkColorScheme` otherwise
  - Passes `colorScheme` and `AppTypography` to `MaterialTheme`.

- `ui/theme/Type.kt`
  - Defines `GeoFontFamily` using `R.font.geo_regular`.
  - Defines `AppTypography` based on Material 3 `Typography`, but with Geo as the font family.

### Data

- A simple `Country` data class with:
  - `name`: string resource ID
  - `flagRes`: drawable resource ID
- A list of four countries (`countries`) used by `CountriesScreen`.

---

## App Icon

- Launcher icon changed via **Image Asset** in Android Studio:
  - `app` → **New ▸ Image Asset**
  - Icon Type: *Launcher Icons (Adaptive and Legacy)*
  - Asset Type: *Image* → globe PNG
- Manifest points to:
  ```xml
  android:icon="@mipmap/ic_launcher"
  android:roundIcon="@mipmap/ic_launcher_round"
