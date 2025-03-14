package ca.ulaval.ima.tp3.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ca.ulaval.ima.tp3.CarDetailActivity
import ca.ulaval.ima.tp3.CarDataService
import ca.ulaval.ima.tp3.R
import ca.ulaval.ima.tp3.models.CarOffer
import com.squareup.picasso.Picasso

class OffersAdapter(
    private var offers: List<CarOffer>,
    private val onOfferClick: (CarOffer) -> Unit
) : RecyclerView.Adapter<OffersAdapter.OfferViewHolder>() {

    class OfferViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val yearTextView: TextView = itemView.findViewById(R.id.yearTextView)
        val priceTextView: TextView = itemView.findViewById(R.id.priceTextView)
        val kilometersTextView: TextView = itemView.findViewById(R.id.kilometersTextView)
        val carNameTextView: TextView = itemView.findViewById(R.id.carNameTextView)
        val carImageView: ImageView = itemView.findViewById(R.id.carImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_offer, parent, false)
        return OfferViewHolder(view)
    }

    override fun onBindViewHolder(holder: OfferViewHolder, position: Int) {
        val offer = offers[position]
        val marque = CarDataService(holder.itemView.context).getBrandAndModelName(offer.brandId, offer.modelId)

        Picasso.get().load(offer.imageUrl).into(holder.carImageView)
        holder.carNameTextView.text = marque
        holder.yearTextView.text = offer.year.toString()
        holder.priceTextView.text = "${offer.price} $"
        holder.kilometersTextView.text = "${offer.kilometers} km"

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, CarDetailActivity::class.java).apply {
                putExtra("offerId", offer.id)
            }
            holder.itemView.context.startActivity(intent)
            onOfferClick(offer)
        }
    }



    override fun getItemCount(): Int = offers.size
}
