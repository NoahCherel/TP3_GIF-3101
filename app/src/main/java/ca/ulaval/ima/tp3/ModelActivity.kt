package ca.ulaval.ima.tp3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ca.ulaval.ima.tp3.adapters.ModelsAdapter
import ca.ulaval.ima.tp3.databinding.ActivityModelsBinding

class ModelsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityModelsBinding
    private lateinit var carDataService: CarDataService
    private lateinit var modelsAdapter: ModelsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModelsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setTitleTextColor(resources.getColor(R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        carDataService = CarDataService(this)
        val brandId = intent.getIntExtra("brandId", -1)
        val brandName = carDataService.getBrandName(brandId)
        supportActionBar?.title = brandName
        val models = carDataService.getModelsForBrand(brandId)
        modelsAdapter = ModelsAdapter(models) { model ->
        }

        binding.recyclerViewModels.apply {
            layoutManager = LinearLayoutManager(this@ModelsActivity)
            adapter = modelsAdapter
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
