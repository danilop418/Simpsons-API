package com.example.simpsons.features.simpsons.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.simpsons.R
import com.example.simpsons.features.simpsons.domain.Simpson

class SimpsonsAdapter : RecyclerView.Adapter<SimpsonsAdapter.SimpsonsViewHolder>() {

    private var simpsons: List<Simpson> = emptyList()

    fun updateSimpsons(newCharacters: List<Simpson>) {
        simpsons = newCharacters
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpsonsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_simpson, parent, false)
        return SimpsonsViewHolder(view)
    }

    override fun onBindViewHolder(holder: SimpsonsViewHolder, position: Int) {
        holder.bind(simpsons[position])
    }

    override fun getItemCount(): Int = simpsons.size

    inner class SimpsonsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.simpson_name)

        fun bind(simpsons: Simpson) {
            nameTextView.text = simpsons.name
        }
    }
}