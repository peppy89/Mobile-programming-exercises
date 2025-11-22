package eu.tutorials.l5_ex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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

@Composable
fun MultiCounterScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CounterRow()
        Spacer(modifier = Modifier.height(16.dp))
        CounterRow()
        Spacer(modifier = Modifier.height(16.dp))
        CounterRow()
    }
}

@Composable
fun CounterRow() {
    var text by remember { mutableStateOf("0") }
    val currentValue = text.toIntOrNull() ?: 0

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(
            onClick = {
                val newValue = currentValue - 1
                text = newValue.toString()
            }
        ) {
            Text(text = "-")
        }

        TextField(
            value = text,
            onValueChange = { newText ->
                // Allow empty or valid integer input
                if (newText.isEmpty() || newText.toIntOrNull() != null) {
                    text = newText
                }
            },
            modifier = Modifier.width(100.dp),
            singleLine = true,
            label = { Text("Value") }
        )

        Button(
            onClick = {
                val newValue = currentValue + 1
                text = newValue.toString()
            }
        ) {
            Text(text = "+")
        }
    }
}
