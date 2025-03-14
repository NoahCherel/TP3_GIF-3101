package ca.ulaval.ima.tp3.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ca.ulaval.ima.tp3.OffersActivity
import ca.ulaval.ima.tp3.databinding.ItemModelBinding
import ca.ulaval.ima.tp3.models.Model

class ModelsAdapter(
    private val models: List<Model>,
    private val onItemClick: (Model) -> Unit
) : RecyclerView.Adapter<ModelsAdapter.ModelViewHolder>() {

    inner class ModelViewHolder(private val binding: ItemModelBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model: Model) {
            binding.modelNameTextView.text = model.name
            binding.root.setOnClickListener {
                onItemClick(model)
                val intent = Intent(itemView.context, OffersActivity::class.java).apply {
                    putExtra("brandId", model.brandId)
                    putExtra("modelId", model.id)
                }
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModelViewHolder {
        val binding = ItemModelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ModelViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ModelViewHolder, position: Int) {
        holder.bind(models[position])
    }

    override fun getItemCount(): Int = models.size
}
