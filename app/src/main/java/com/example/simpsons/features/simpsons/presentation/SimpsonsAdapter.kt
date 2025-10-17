package com.example.simpsons.features.simpsons.presentation

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.simpsons.features.simpsons.domain.Simpson
import com.example.superheroes.R

class SimpsonAdapter : RecyclerView.Adapter<SimpsonAdapter.SimpsonViewHolder>() {
    private var lista: List<SimpsonUiModel> = emptyList()

    fun updateList(newList: List<SimpsonUiModel>) {
        lista = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpsonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_simpson, parent, false)
        return SimpsonViewHolder(view)
    }

    override fun getItemCount(): Int = lista.size

    override fun onBindViewHolder(holder: SimpsonViewHolder, position: Int) {
        holder.bind(lista[position])
    }

    inner class SimpsonViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val image: ImageView = view.findViewById(R.id.simpson_image)
        private val name: TextView = view.findViewById(R.id.simpson_name)
        private val phrase: TextView = view.findViewById(R.id.simpson_phrase)

        fun bind(simpson: SimpsonUiModel) {
            name.text = simpson.name
            phrase.text = simpson.phrase
            image.load(simpson.imageUrl) {
                crossfade(true)
                placeholder(R.drawable.ic_launcher_background)
                error(R.drawable.ic_launcher_foreground)
            }

            itemView.setOnClickListener {
                val simpsonDomain = Simpson(
                    id = simpson.id,
                    name = simpson.name,
                    phrase = simpson.phrase,
                    imageUrl = simpson.imageUrl
                )
                SimpsonObserver.setSimpson(simpsonDomain)
                val context = itemView.context
                val intent = Intent(context, SimpsonDetailActivity::class.java)
                context.startActivity(intent)
            }
        }
    }
}