package com.ecommerce.project.ciphercart.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.ecommerce.project.ciphercart.R
import com.ecommerce.project.ciphercart.databinding.ActivityLaunchBinding
import com.ecommerce.project.ciphercart.utils.goToMainActivity
import com.ecommerce.project.ciphercart.viewmodels.LogInViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LaunchActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityLaunchBinding.inflate(layoutInflater)
    }
    private val logInViewModel:LogInViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if(logInViewModel.isUserLoggedIn()!=null){
            goToMainActivity(this)
        }
    }
}