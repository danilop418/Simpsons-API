package com.example.simpsons.features.simpsons

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.simpsons.databinding.ActivityMainBinding


class SimpsonsMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}