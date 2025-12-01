# Sensor Data Display App

## Description

An Android application built with Kotlin and Jetpack Compose that displays real-time sensor data including magnetometer, temperature, and light sensor readings. The app includes font customization options (default, bold, italic) that are persisted using DataStore.

## Features

- Real-time magnetometer data display (X, Y, Z axes in μT)
- Temperature sensor reading in Celsius
- Light sensor reading in lux
- Font style preferences (Default, Bold, Italic)
- Persistent font settings using DataStore
- Material 3 design

## Technologies Used

- Kotlin
- Jetpack Compose
- Android Sensor Framework
- DataStore for preferences
- Coroutines

## Testing

Use Android Emulator's Extended Controls → Virtual Sensors to test sensor values.
![alt text](image.png)
