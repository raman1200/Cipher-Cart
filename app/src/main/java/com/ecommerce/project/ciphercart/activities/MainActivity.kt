package com.ecommerce.project.ciphercart.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.ecommerce.project.ciphercart.R
import com.ecommerce.project.ciphercart.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navHostFragment:NavHostFragment
    private lateinit var navController:NavController

    private val binding by lazy {
       ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        setUpNavHost()
        listeners()

    }

    private fun setUpNavHost() {
        navHostFragment = supportFragmentManager.findFragmentById(R.id.host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bottomNav, navController)

    }

    private fun listeners() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                // Hide the BottomNavigationView when navigating to a specific fragment
                R.id.addressAddFragment -> {
                    binding.bottomNav.visibility = View.GONE
                }
//                R.id.map -> {
//                    binding.bottomNav.visibility = View.GONE
//                }
                R.id.productsViewFragment -> {
                    binding.bottomNav.visibility = View.GONE
                }
                R.id.productDetailFragment -> {
                    binding.bottomNav.visibility = View.GONE
                }
                R.id.notificationSettingFragment -> {
                    binding.bottomNav.visibility = View.GONE
                }
                R.id.editProfileFragment -> {
                    binding.bottomNav.visibility = View.GONE
                }
                R.id.addressViewFragment -> {
                    binding.bottomNav.visibility = View.GONE
                }
                R.id.savedFragment -> {
                    binding.bottomNav.visibility = View.GONE
                }
                R.id.languageFragment -> {
                    binding.bottomNav.visibility = View.GONE
                }
                R.id.securityFragment  -> {
                    binding.bottomNav.visibility = View.GONE
                }
                R.id.notificationFragment -> {
                    binding.bottomNav.visibility = View.GONE
                }
                R.id.createNewPasswordFragment -> {
                    binding.bottomNav.visibility = View.GONE
                }
                R.id.pinVerifyFragment -> {
                    binding.bottomNav.visibility = View.GONE
                }
                else -> {
                    // Show the BottomNavigationView for other fragments
                    binding.bottomNav.visibility = View.VISIBLE
                }
            }
        }
    }
    override fun onBackPressed() {

        // Get the current destination fragment
        val currentDestination = navController.currentDestination

        // Check if the current fragment is the home fragment
        if (currentDestination?.id == R.id.homeFragment) {
            // If it's the home fragment, finish the activity
            finishAffinity()
        } else {
            // If it's not the home fragment, navigate up
            super.onBackPressed()
        }
    }


}