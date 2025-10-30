package com.example.simpsons.features.simpsons.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.simpsons.R
import com.example.simpsons.features.simpsons.domain.Simpson

class SimpsonsAdapter (private val onItemClick: (Simpson) -> Unit): RecyclerView.Adapter<SimpsonsAdapter.SimpsonsViewHolder>() {

    private var simpsons: List<Simpson> = emptyList()

    fun updateSimpsons(newCharacters: List<Simpson>) {
        simpsons = newCharacters
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpsonsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_simpson, parent, false)
        return SimpsonsViewHolder(view,onItemClick)
    }

    override fun onBindViewHolder(holder: SimpsonsViewHolder, position: Int) {
        holder.bind(simpsons[position])
    }

    override fun getItemCount(): Int = simpsons.size

    inner class SimpsonsViewHolder(itemView: View, private val onItemClick: (Simpson) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.simpson_name)
        private val phraseTextView: TextView = itemView.findViewById(R.id.simpson_phrase)
        private val imageView: ImageView = itemView.findViewById(R.id.simpson_image)


        fun bind(simpsons: Simpson) {
            nameTextView.text = simpsons.name
            phraseTextView.text = simpsons.phrase

            imageView.load(simpsons.imageUrl) {
                crossfade(true)
            }

            itemView.setOnClickListener {
                onItemClick(simpsons)
            }
        }
    }
}