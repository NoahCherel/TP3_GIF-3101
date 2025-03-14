package ca.ulaval.ima.tp3

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import ca.ulaval.ima.tp3.databinding.ActivityCarDetailBinding
import com.squareup.picasso.Picasso

class CarDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCarDetailBinding
    private lateinit var carDataService: CarDataService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCarDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.toolbar.setTitleTextColor(resources.getColor(R.color.white, null))

        carDataService = CarDataService(this)
        val offerId = intent.getIntExtra("offerId", -1)

        val offer = carDataService.getOfferById(offerId)
        val marque = CarDataService(this).getBrandAndModelName(offer?.brandId, offer?.modelId)

        offer?.let {
            supportActionBar?.title = marque
            Picasso.get().load(it.imageUrl).into(binding.carImageView)
            binding.carNameValue.text = marque
            binding.yearValue.text = it.year.toString()
            binding.priceValue.text = it.price.toString() + " $"
            binding.kilometersValue.text = it.kilometers.toString() + " km"
            binding.transmissionValue.text = it.transmissions
            binding.sellerEmailValue.text = it.sellerEmail
            binding.sellerNameValue.text = it.sellerName
            binding.sellerPropertyValue.text = it.sellerProprio

            binding.contactButton.setOnClickListener { view ->
                val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:${it.sellerEmail}")
                    putExtra(Intent.EXTRA_SUBJECT, "Intérêt pour: ${it.description}")
                    putExtra(Intent.EXTRA_TEXT, "Bonjour ${it.sellerName},\n\nJe suis intéressé par votre voiture ${it.description} (année ${it.year}) à ${it.price}$.\n\nPouvez-vous me contacter pour plus d'informations?\n\nCordialement,")
                }

                if (emailIntent.resolveActivity(packageManager) != null) {
                    startActivity(emailIntent)
                }
            }

        } ?: run {
            supportActionBar?.title = "Car Not Found"
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}