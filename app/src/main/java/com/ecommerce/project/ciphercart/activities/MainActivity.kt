package com.ecommerce.project.ciphercart.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ecommerce.project.ciphercart.R
import com.ecommerce.project.ciphercart.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
       ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

    }
}