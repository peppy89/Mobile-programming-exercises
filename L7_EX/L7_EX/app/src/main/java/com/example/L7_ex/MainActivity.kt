package com.example.L7_ex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.ExperimentalMaterial3Api


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                MultiCounterScreen()
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MultiCounterScreen() {
    // List of counters, each element is the current value of that counter
    val counters = remember {
        // start with 5 counters (all 0); change size if you like
        mutableStateListOf(0, 0, 0, 0, 0)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Multi Counter") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    // Add a new counter with initial value 0
                    counters.add(0)
                }
            ) {
                Text("+")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text(
                text = "Counters",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(counters) { index, countValue ->
                    CounterRow(
                        name = "Counter_${index + 1}",
                        value = countValue,
                        onIncrement = {
                            counters[index] = counters[index] + 1
                        },
                        onDecrement = {
                            counters[index] = counters[index] - 1
                        },
                        onRemove = {
                            counters.removeAt(index)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun CounterRow(
    name: String,
    value: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    onRemove: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Column {
                Text(text = name, fontWeight = FontWeight.Bold)
                Text(text = "Value: $value", fontSize = 18.sp)
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Button(onClick = onDecrement, modifier = Modifier.padding(end = 4.dp)) {
                    Text("-")
                }
                Button(onClick = onIncrement, modifier = Modifier.padding(end = 8.dp)) {
                    Text("+")
                }
                OutlinedButton(onClick = onRemove) {
                    Text("Remove")
                }
            }
        }
    }
}
