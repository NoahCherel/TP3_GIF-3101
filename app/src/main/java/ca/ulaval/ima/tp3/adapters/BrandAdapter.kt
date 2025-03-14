package ca.ulaval.ima.tp3.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ca.ulaval.ima.tp3.R
import ca.ulaval.ima.tp3.models.Brand

class BrandsAdapter(
    private val brands: List<Brand>,
    private val onBrandClick: (Brand) -> Unit
) : RecyclerView.Adapter<BrandsAdapter.BrandViewHolder>() {

    class BrandViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val brandNameTextView: TextView = itemView.findViewById(R.id.brandNameTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrandViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_brand, parent, false)
        return BrandViewHolder(view)
    }

    override fun onBindViewHolder(holder: BrandViewHolder, position: Int) {
        val brand = brands[position]
        holder.brandNameTextView.text = brand.name

        holder.itemView.setOnClickListener {
            onBrandClick(brand)
        }
    }

    override fun getItemCount(): Int = brands.size
}