package com.example.simpsons.features.simpsons.presentation

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import coil.load
import com.example.simpsons.features.simpsons.domain.Simpson
import com.example.superheroes.R

class SimpsonDetailActivity : AppCompatActivity() {
    private lateinit var simpsonImage: ImageView
    private lateinit var simpsonName: TextView
    private lateinit var simpsonPhrase: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_simpson_detail)

        simpsonImage = findViewById(R.id.simpsonImage)
        simpsonName = findViewById(R.id.simpsonName)
        simpsonPhrase = findViewById(R.id.simpsonPhrase)

        SimpsonObserver.selectedSimpson.observe(this, Observer { simpson ->
            if (simpson != null) {
                bindSimpson(simpson)
            } else {
                Toast.makeText(this, "No hay personaje seleccionado", Toast.LENGTH_SHORT).show()
                finish()
            }
        })
    }

    private fun bindSimpson(simpson: Simpson) {
        simpsonName.text = simpson.name
        simpsonPhrase.text = simpson.phrase
        simpsonImage.load(simpson.imageUrl) {
            crossfade(true)
            placeholder(R.drawable.ic_launcher_background)
            error(R.drawable.ic_launcher_foreground)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        SimpsonObserver.clear()
    }
}