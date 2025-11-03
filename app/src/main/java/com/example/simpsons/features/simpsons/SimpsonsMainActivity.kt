package com.example.simpsons.features.simpsons

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.example.simpsons.R
import com.example.simpsons.databinding.ActivityMainBinding
import com.example.simpsons.features.simpsons.presentation.list.SimpsonsListFragment


class SimpsonsMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(R.id.fragment_container, SimpsonsListFragment())
            }
        }
    }
}