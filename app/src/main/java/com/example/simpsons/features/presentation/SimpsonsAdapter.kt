import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.simpsons.features.domain.Simpson
import coil.load
import com.example.simpsons.databinding.ViewSimpsonsItemBinding

class SimpsonsAdapter(private val dataset: List<Simpson>,
                      private val onItemClick: (Simpson) -> Unit
) : RecyclerView.Adapter<SimpsonsAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ViewSimpsonsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(simpson: Simpson, onItemClick: (Simpson) -> Unit) {
            binding.name.text = simpson.name
            binding.description.text = simpson.phrase
            binding.avatar.contentDescription = simpson.name
            binding.avatar.load(simpson.imageUrl) {
                crossfade(true)
            }
            binding.root.setOnClickListener {
                onItemClick(simpson)
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
        holder.bind(dataset[position], onItemClick)
    }

    override fun getItemCount() = dataset.size

    }