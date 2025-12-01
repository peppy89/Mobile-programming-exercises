package com.example.shoppinglistroom

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

// ==========================================
// 1. ROOM ENTITY (The Table Structure)
// ==========================================
@Entity(tableName = "shopping_items")
data class ShoppingItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val quantity: Double,
    val unit: String,
    val price: Double
)

// ==========================================
// 2. ROOM DAO (Database Access Object)
// ==========================================
@Dao
interface ShoppingDao {
    @Query("SELECT * FROM shopping_items ORDER BY id DESC")
    fun getAllItems(): Flow<List<ShoppingItem>>

    @Insert
    suspend fun insertItem(item: ShoppingItem)

    @Delete
    suspend fun deleteItem(item: ShoppingItem)
}

// ==========================================
// 3. ROOM DATABASE
// ==========================================
@Database(entities = [ShoppingItem::class], version = 1, exportSchema = false)
abstract class ShoppingDatabase : RoomDatabase() {
    abstract fun shoppingDao(): ShoppingDao

    companion object {
        @Volatile
        private var INSTANCE: ShoppingDatabase? = null

        fun getDatabase(context: android.content.Context): ShoppingDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ShoppingDatabase::class.java,
                    "shopping_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

// ==========================================
// 4. VIEW MODEL (Logic)
// ==========================================
class ShoppingViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = ShoppingDatabase.getDatabase(application).shoppingDao()

    // Flow acts like LiveData, updates UI automatically when DB changes
    val allItems: Flow<List<ShoppingItem>> = dao.getAllItems()

    fun addItem(name: String, qty: String, unit: String, price: String) {
        viewModelScope.launch {
            val quantityVal = qty.toDoubleOrNull() ?: 0.0
            val priceVal = price.toDoubleOrNull() ?: 0.0
            if (name.isNotBlank()) {
                dao.insertItem(ShoppingItem(name = name, quantity = quantityVal, unit = unit, price = priceVal))
            }
        }
    }

    fun deleteItem(item: ShoppingItem) {
        viewModelScope.launch {
            dao.deleteItem(item)
        }
    }
}

// Factory to create ViewModel with Application context
class ShoppingViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShoppingViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ShoppingViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

// ==========================================
// 5. UI (Jetpack Compose)
// ==========================================
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val context = LocalContext.current.applicationContext as Application
                val viewModel: ShoppingViewModel = viewModel(
                    factory = ShoppingViewModelFactory(context)
                )
                ShoppingScreen(viewModel)
            }
        }
    }
}

@Composable
fun ShoppingScreen(viewModel: ShoppingViewModel) {
    // Collect the database list as State
    val shoppingList by viewModel.allItems.collectAsState(initial = emptyList())

    // Input States
    var name by remember { mutableStateOf("") }
    var qty by remember { mutableStateOf("") }
    var unit by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Shopping List (Room DB)", fontSize = 24.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 16.dp))

        // --- Input Section ---
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Item") }, modifier = Modifier.weight(2f))
            OutlinedTextField(value = unit, onValueChange = { unit = it }, label = { Text("Unit") }, modifier = Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = qty, onValueChange = { qty = it },
                label = { Text("Qty") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = price, onValueChange = { price = it },
                label = { Text("$$") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )
        }

        Button(
            onClick = {
                viewModel.addItem(name, qty, unit, price)
                // Clear inputs
                name = ""
                qty = ""
                unit = ""
                price = ""
            },
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
        ) {
            Text("Add to List")
        }

        // --- Table Header ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .padding(8.dp)
        ) {
            Text("Item", fontWeight = FontWeight.Bold, modifier = Modifier.weight(2f))
            Text("Qty", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
            Text("Price", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
            Text("Del", fontWeight = FontWeight.Bold, modifier = Modifier.width(40.dp))
        }

        // --- List Data ---
        LazyColumn {
            items(shoppingList) { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(2f)) {
                        Text(item.name, fontWeight = FontWeight.Bold)
                        Text(item.unit, fontSize = 12.sp, color = Color.Gray)
                    }
                    Text(item.quantity.toString(), modifier = Modifier.weight(1f))
                    Text("$${item.price}", modifier = Modifier.weight(1f))

                    IconButton(
                        onClick = { viewModel.deleteItem(item) },
                        modifier = Modifier.width(40.dp)
                    ) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red)
                    }
                }
                Divider()
            }
        }
    }
}