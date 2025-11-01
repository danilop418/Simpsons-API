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
        val tvPhrase: TextView

        init {
            ivImage = view.findViewById(R.id.simpsonImage)
            tvName = view.findViewById(R.id.simpsonName)
            tvPhrase = view.findViewById(R.id.simpsonPhrase)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.character_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.tvName.text = dataset[position].name
        holder.tvPhrase.text = dataset[position].phrase
        holder.ivImage.contentDescription = dataset[position].imageUrl

        holder.ivImage.load(dataset[position].imageUrl) {
            crossfade(true)
        }
    }

    override fun getItemCount() = dataset.size

    }