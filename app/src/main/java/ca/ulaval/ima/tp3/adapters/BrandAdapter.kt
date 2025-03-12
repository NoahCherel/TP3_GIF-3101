package ca.ulaval.ima.tp3.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ca.ulaval.ima.tp3.databinding.ItemBrandBinding

class BrandAdapter(private val brands: List<String>, private val onBrandSelected: (String) -> Unit) : RecyclerView.Adapter<BrandAdapter.BrandViewHolder>() {
    class BrandViewHolder(private val binding: ItemBrandBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(brand: String, onBrandSelected: (String) -> Unit) {
            println("Binding brand $brand")
            binding.brand = brand
            binding.root.setOnClickListener {
                onBrandSelected(brand)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrandViewHolder {
        val binding = ItemBrandBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BrandViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BrandViewHolder, position: Int) {
        holder.bind(brands[position], onBrandSelected)
    }

    override fun getItemCount(): Int {
        return brands.size
    }
}
