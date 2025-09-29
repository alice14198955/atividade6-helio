package com.alice.aliceatividade

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.alice.aliceatividade.database.UserDatabase
import com.alice.aliceatividade.databinding.ActivityMainBinding
import com.alice.aliceatividade.model.User
import com.alice.aliceatividade.model.Product


class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = Room.databaseBuilder(applicationContext, UserDatabase::class.java, "user-database")
            .allowMainThreadQueries()
            .build()

        val novoUsuario = User(name = "Alice", email = "alice@gmail.com")
        db.userDao().insert(novoUsuario)

        val novoProduto = Product(name = "Notebook", price = 3500.0)
        db.productDao().insert(novoProduto)

        val users = db.userDao().getAllUsers()
        for (user in users) {
            Log.d("User", "${user.id}: ${user.name} - ${user.email}")
        }

        val products = db.productDao().getAllProducts()
        for (product in products) {
            Log.d("Product", "${product.id}: ${product.name} - R$ ${product.price}")
        }
        replaceFragment(fragment = HomeFragment())
        //binding.

   }

    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                android.R.anim.slide_in_left,
                android.R.anim.slide_out_right
            )
            .replace(R.id.fragmentContainerView, fragment)
            .commit()
    }
}