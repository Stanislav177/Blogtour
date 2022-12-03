package com.example.blogtour

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.blogtour.databinding.ActivityMainBinding
import com.example.blogtour.ui.home.HomeFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.containerFragment, HomeFragment()).commit()
        }
    }
}