package com.alice.aliceatividade

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.alice.aliceatividade.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(HomeFragment())
        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_user -> replaceFragment(HomeFragment())
                R.id.nav_people -> replaceFragment(UserFragment())
                R.id.nav_product -> replaceFragment(ProductFragment())
                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment): Boolean {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                android.R.anim.slide_in_left,
                android.R.anim.slide_out_right
            )
            .replace(R.id.fragmentContainerView, fragment)
            .commit()
        return true
    }
}
