package com.globant.imdb.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.globant.imdb.R
import com.globant.imdb.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var backCallback: OnBackPressedCallback
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        binding.apply {
            bottomNavigationView.setupWithNavController(navController)
            navController.addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.homeFragment, R.id.searchFragment, R.id.playFragment, R.id.profileFragment, R.id.detailsFragment -> {
                        bottomNavigationView.visibility = View.VISIBLE
                    }
                    else -> {
                        bottomNavigationView.visibility = View.GONE
                    }
                }
            }
        }

        backCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                when (navController.currentDestination?.id) {
                    R.id.homeFragment, R.id.splashFragment, R.id.loginFragment, R.id.registrationFragment, R.id.detailsFragment -> {
                        isEnabled = false
                        onBackPressedDispatcher.onBackPressed()
                        isEnabled = true
                    }
                    else -> {
                        navController.popBackStack(R.id.homeFragment, false)
                    }
                }
            }
        }
        onBackPressedDispatcher.addCallback(this, backCallback)
    }
}