import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.simpsons.R
import com.example.simpsons.features.simpsons.domain.Simpson

class SimpsonsAdapter(private val dataset: List<Simpson>) :
    RecyclerView.Adapter<SimpsonsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivImage : ImageView
        val tvName : TextView
        val tvPhrase: TextView

        init {
            ivImage = view.findViewById(R.id.simpson_image)
            tvName = view.findViewById(R.id.simpson_name)
            tvPhrase = view.findViewById(R.id.simpson_phrase)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_simpson, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.tvName.text = dataset[position].name
        holder.tvPhrase.text = dataset[position].phrase
        holder.ivImage.contentDescription = dataset[position].imageUrl
    }

    override fun getItemCount() = dataset.size

    }