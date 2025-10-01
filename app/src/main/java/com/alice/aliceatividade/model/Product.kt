package com.alice.aliceatividade.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alice.aliceatividade.database.UserDatabase
import kotlinx.coroutines.launch

@Entity(tableName = "products")
data class Product(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val price: Double
)

class ProductViewModel(application: Application) : AndroidViewModel(application) {
    private val productDao = UserDatabase.getDatabase(application).productDao()
    val products: LiveData<List<Product>> = productDao.getAllProductsLive()

    fun insert(product: Product) = viewModelScope.launch {
        productDao.insert(product)
    }
}
