package ca.ulaval.ima.tp3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ca.ulaval.ima.tp3.adapters.OffersAdapter
import ca.ulaval.ima.tp3.databinding.ActivityOffersBinding

class OffersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOffersBinding
    private lateinit var carDataService: CarDataService
    private lateinit var offersAdapter: OffersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOffersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setTitleTextColor(resources.getColor(R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        carDataService = CarDataService(this)

        val brandId = intent.getIntExtra("brandId", -1)
        val modelId = intent.getIntExtra("modelId", -1)
        val modelName = carDataService.getModelName(brandId, modelId)
        supportActionBar?.title = modelName
        val offers = carDataService.getOffersForModel(brandId, modelId)

        offersAdapter = OffersAdapter(offers) { offer ->
        }

        binding.recyclerViewOffers.apply {
            layoutManager = LinearLayoutManager(this@OffersActivity)
            adapter = offersAdapter
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
