package com.openclassrooms.arista.ui

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.openclassrooms.arista.R
import com.openclassrooms.arista.databinding.ActivityMainBinding
import com.openclassrooms.arista.ui.exercise.ExerciseFragment
import com.openclassrooms.arista.ui.sleep.SleepFragment
import com.openclassrooms.arista.ui.user.UserDataFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigation.setOnNavigationItemSelectedListener(navListener)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, UserDataFragment()).commit()
        }
    }

    private val navListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        val selectedFragment = when (item.itemId) {
            R.id.nav_user_data -> UserDataFragment()
            R.id.nav_exercise -> ExerciseFragment()
            R.id.nav_sleep -> SleepFragment()
            else -> null
        }

        selectedFragment?.let {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, it).commit()
            true
        } ?: false
    }
}
