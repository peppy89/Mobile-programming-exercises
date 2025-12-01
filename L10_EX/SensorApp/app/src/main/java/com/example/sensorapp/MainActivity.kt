package com.example.sensorapp

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.example.sensorapp.ui.theme.SensorAppTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : ComponentActivity() {
    private lateinit var sensorManager: SensorManager
    private var magnetometerSensor: Sensor? = null
    private var temperatureSensor: Sensor? = null
    private var lightSensor: Sensor? = null

    private val magnetometerData = mutableStateOf(Triple(0f, 0f, 0f))
    private val temperatureData = mutableStateOf(0f)
    private val lightData = mutableStateOf(0f)

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            setupSensors()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                setupSensors()
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }

        setContent {
            SensorAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SensorScreen(
                        magnetometerData = magnetometerData.value,
                        temperatureData = temperatureData.value,
                        lightData = lightData.value,
                        dataStore = dataStore
                    )
                }
            }
        }
    }

    private fun setupSensors() {
        magnetometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
        temperatureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

        magnetometerSensor?.let {
            sensorManager.registerListener(magnetometerListener, it, SensorManager.SENSOR_DELAY_NORMAL)
        }

        temperatureSensor?.let {
            sensorManager.registerListener(temperatureListener, it, SensorManager.SENSOR_DELAY_NORMAL)
        }

        lightSensor?.let {
            sensorManager.registerListener(lightListener, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    private val magnetometerListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            magnetometerData.value = Triple(event.values[0], event.values[1], event.values[2])
        }

        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
    }

    private val temperatureListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            temperatureData.value = event.values[0]
        }

        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
    }

    private val lightListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            lightData.value = event.values[0]
        }

        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
    }

    override fun onDestroy() {
        super.onDestroy()
        sensorManager.unregisterListener(magnetometerListener)
        sensorManager.unregisterListener(temperatureListener)
        sensorManager.unregisterListener(lightListener)
    }
}

object PreferencesKeys {
    val FONT_STYLE = stringPreferencesKey("font_style")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SensorScreen(
    magnetometerData: Triple<Float, Float, Float>,
    temperatureData: Float,
    lightData: Float,
    dataStore: DataStore<Preferences>
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val fontStyleFlow: Flow<String> = dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.FONT_STYLE] ?: "default"
        }

    val fontStyle by fontStyleFlow.collectAsState(initial = "default")

    val textFontWeight = when (fontStyle) {
        "bold" -> FontWeight.Bold
        else -> FontWeight.Normal
    }

    val textFontStyle = when (fontStyle) {
        "italic" -> FontStyle.Italic
        else -> FontStyle.Normal
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Sensor Data Display",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = textFontWeight,
            fontStyle = textFontStyle
        )

        // Font Style Selection
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Font Style",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = textFontWeight,
                    fontStyle = textFontStyle
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    FilterChip(
                        selected = fontStyle == "default",
                        onClick = {
                            scope.launch {
                                dataStore.edit { preferences ->
                                    preferences[PreferencesKeys.FONT_STYLE] = "default"
                                }
                            }
                        },
                        label = { Text("Default") }
                    )

                    FilterChip(
                        selected = fontStyle == "bold",
                        onClick = {
                            scope.launch {
                                dataStore.edit { preferences ->
                                    preferences[PreferencesKeys.FONT_STYLE] = "bold"
                                }
                            }
                        },
                        label = { Text("Bold") }
                    )

                    FilterChip(
                        selected = fontStyle == "italic",
                        onClick = {
                            scope.launch {
                                dataStore.edit { preferences ->
                                    preferences[PreferencesKeys.FONT_STYLE] = "italic"
                                }
                            }
                        },
                        label = { Text("Italic") }
                    )
                }
            }
        }

        // Magnetometer Data
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Magnetometer",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = textFontWeight,
                    fontStyle = textFontStyle
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "X: ${String.format("%.2f", magnetometerData.first)} μT",
                    fontWeight = textFontWeight,
                    fontStyle = textFontStyle
                )
                Text(
                    text = "Y: ${String.format("%.2f", magnetometerData.second)} μT",
                    fontWeight = textFontWeight,
                    fontStyle = textFontStyle
                )
                Text(
                    text = "Z: ${String.format("%.2f", magnetometerData.third)} μT",
                    fontWeight = textFontWeight,
                    fontStyle = textFontStyle
                )
            }
        }

        // Temperature Data
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Temperature",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = textFontWeight,
                    fontStyle = textFontStyle
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "${String.format("%.1f", temperatureData)}°C",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = textFontWeight,
                    fontStyle = textFontStyle
                )
            }
        }

        // Light Data
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Light Sensor",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = textFontWeight,
                    fontStyle = textFontStyle
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "${String.format("%.1f", lightData)} lux",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = textFontWeight,
                    fontStyle = textFontStyle
                )
            }
        }
    }
}