import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.simpsons.R
import com.example.simpsons.features.domain.Simpson
import coil.load
import com.example.simpsons.databinding.ViewSimpsonsItemBinding

data class SimpsonWithFavorite(
    val simpson: Simpson,
    val isFavorite: Boolean
)

class SimpsonsAdapter(
    private val onItemClick: (Simpson) -> Unit,
    private val onFavoriteClick: (Simpson) -> Unit
) : ListAdapter<SimpsonWithFavorite, SimpsonsAdapter.ViewHolder>(SimpsonDiffCallback()) {

    class ViewHolder(private val binding: ViewSimpsonsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: SimpsonWithFavorite,
            onItemClick: (Simpson) -> Unit,
            onFavoriteClick: (Simpson) -> Unit
        ) {
            val simpson = item.simpson
            binding.name.text = simpson.name
            binding.description.text = simpson.phrase
            binding.avatar.contentDescription = simpson.name
            binding.avatar.load(simpson.imageUrl) {
                crossfade(true)
            }
            binding.root.setOnClickListener {
                onItemClick(simpson)
            }

            val iconRes = if (item.isFavorite) R.drawable.ic_heart_filled else R.drawable.ic_heart_outline
            binding.btnFavorite.setIconResource(iconRes)
            binding.btnFavorite.setOnClickListener {
                onFavoriteClick(simpson)
            }
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
        holder.bind(getItem(position), onItemClick, onFavoriteClick)
    }

    private class SimpsonDiffCallback : DiffUtil.ItemCallback<SimpsonWithFavorite>() {
        override fun areItemsTheSame(oldItem: SimpsonWithFavorite, newItem: SimpsonWithFavorite): Boolean {
            return oldItem.simpson.id == newItem.simpson.id
        }

        override fun areContentsTheSame(oldItem: SimpsonWithFavorite, newItem: SimpsonWithFavorite): Boolean {
            return oldItem == newItem
        }
    }
}