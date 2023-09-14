package com.ecommerce.project.ciphercart.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.ecommerce.project.ciphercart.R
import com.ecommerce.project.ciphercart.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
       ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.host_fragment) as NavHostFragment
//        val navController = navHostFragment.navController
//
//        binding.bottomNav.setupWithNavController(navController)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bottomNav, navController)
    }
}