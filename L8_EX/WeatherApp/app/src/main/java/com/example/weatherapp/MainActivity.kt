package com.example.weatherapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// --- 1. Data Models (JSON Mapping) ---
data class WeatherResponse(
    val name: String,
    val main: MainData,
    val id: Int
)

data class MainData(
    val temp: Double,
    val humidity: Int
)

// --- 2. Retrofit Interface ---
interface WeatherApi {
    @GET("weather")
    suspend fun getWeather(
        @Query("q") city: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): WeatherResponse
}

// --- 3. Retrofit Singleton Object ---
object RetrofitInstance {
    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

    val api: WeatherApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }
}

// --- 4. ViewModel ---
class WeatherViewModel : ViewModel() {
    // State to hold the list of weather data
    var weatherList = mutableStateListOf<WeatherResponse>()
        private set

    var errorMessage by mutableStateOf("")

    // *** PASTE YOUR API KEY HERE ***
    private val apiKey = "e117a64828a6c0f8c7267dcb706c921d"

    fun fetchWeather(city: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getWeather(city = city, apiKey = apiKey)
                // Add new weather data to the top of the list
                weatherList.add(0, response)
                errorMessage = ""
            } catch (e: Exception) {
                errorMessage = "Error: Could not find '$city' or API issue."
                Log.e("WeatherError", e.toString())
            }
        }
    }
}

// --- 5. UI (Compose) ---
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                WeatherScreen()
            }
        }
    }
}

@Composable
fun WeatherScreen(viewModel: WeatherViewModel = viewModel()) {
    var cityInput by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "City Weather Fetcher",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Input Field
        OutlinedTextField(
            value = cityInput,
            onValueChange = { cityInput = it },
            label = { Text("Enter City Name") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Fetch Button
        Button(
            onClick = {
                if (cityInput.isNotBlank()) {
                    viewModel.fetchWeather(cityInput)
                    cityInput = "" // Clear input after search
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Get Weather")
        }

        // Error Message display
        if (viewModel.errorMessage.isNotEmpty()) {
            Text(
                text = viewModel.errorMessage,
                color = Color.Red,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // List of Weather Data
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(viewModel.weatherList) { weather ->
                WeatherCard(weather)
            }
        }
    }
}

@Composable
fun WeatherCard(weather: WeatherResponse) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = weather.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Temp: ${weather.main.temp}°C")
                Text(text = "Humidity: ${weather.main.humidity}%")
            }
        }
    }
}