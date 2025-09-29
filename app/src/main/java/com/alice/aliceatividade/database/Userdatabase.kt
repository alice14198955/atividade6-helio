package com.alice.aliceatividade.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.alice.aliceatividade.dao.UserDao
import com.alice.aliceatividade.dao.ProductDao
import com.alice.aliceatividade.model.User
import com.alice.aliceatividade.model.Product

@Database(entities = [User::class, Product::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun productDao(): ProductDao

    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getDatabase(context: Context): UserDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "users"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
