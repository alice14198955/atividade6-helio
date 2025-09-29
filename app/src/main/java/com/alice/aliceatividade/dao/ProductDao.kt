package com.alice.aliceatividade.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alice.aliceatividade.model.Product

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(product: Product)

    @Query("SELECT * FROM products")
    fun getAllProducts(): List<Product>

    @Query("SELECT * FROM products")
    fun getAllProductsLive(): LiveData<List<Product>>

}
