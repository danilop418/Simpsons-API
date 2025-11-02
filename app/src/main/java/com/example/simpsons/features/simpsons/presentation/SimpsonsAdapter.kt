import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.simpsons.R
import com.example.simpsons.features.simpsons.domain.Simpson
import coil.load

class SimpsonsAdapter(private val dataset: List<Simpson>) :
    RecyclerView.Adapter<SimpsonsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivImage : ImageView
        val tvName : TextView

        init {
            ivImage = view.findViewById(R.id.image)
            tvName = view.findViewById(R.id.name)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_simpsons_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val item = dataset[position]
        holder.tvName.text = item.name
        holder.ivImage.contentDescription = item.imageUrl

        holder.ivImage.load(item.imageUrl) {
            crossfade(true)
        }
    }

    override fun getItemCount() = dataset.size

    }