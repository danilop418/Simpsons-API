package com.example.simpsons.features.presentation.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.simpsons.databinding.ViewSimpsonsItemBinding
import com.example.simpsons.features.domain.Simpson

class SimpsonsPagingAdapter(
    private val onItemClick: (Simpson) -> Unit
) : PagingDataAdapter<Simpson, SimpsonsPagingAdapter.ViewHolder>(SimpsonDiffCallback) {

    class ViewHolder(private val binding: ViewSimpsonsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: Simpson,
            onItemClick: (Simpson) -> Unit
        ) {
            binding.name.text = item.name
            binding.description.text = item.phrase
            binding.avatar.contentDescription = item.name
            binding.avatar.load(item.imageUrl) {
                crossfade(true)
            }
            binding.root.setOnClickListener {
                onItemClick(item)
            }
            // Note: Favorites logic is temporarily omitted in this Paging migration step
            // as we are transitioning from ListAdapter<SimpsonWithFavorite> to PagingDataAdapter<Simpson>
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewSimpsonsItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item, onItemClick)
        }
    }

    object SimpsonDiffCallback : DiffUtil.ItemCallback<Simpson>() {
        override fun areItemsTheSame(oldItem: Simpson, newItem: Simpson): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Simpson, newItem: Simpson): Boolean {
            return oldItem == newItem
        }
    }
}
