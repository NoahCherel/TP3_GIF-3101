package ca.ulaval.ima.tp3

import android.content.Context
import com.google.gson.Gson
import ca.ulaval.ima.tp3.models.Brand
import ca.ulaval.ima.tp3.models.Model
import ca.ulaval.ima.tp3.models.CarOffer
import java.io.File

class CarDataService(private val context: Context) {
    private var data: CarData? = null

    data class CarData(
        val brands: List<Brand>,
        val myOffers: List<CarOffer>
    )

    fun loadData(): CarData {
        if (data == null) {
            val file = File(context.filesDir, "Database.json")
            println("Loading data from file: ${file.absolutePath}")
            val jsonString = if (file.exists()) {
                file.readText()
            } else {
                context.assets.open("database/Database.json").bufferedReader().use { it.readText() }.also {
                    file.writeText(it)
                }
            }
            data = Gson().fromJson(jsonString, CarData::class.java)
        }
        return data!!
    }


    fun getBrands(): List<Brand> {
        return loadData().brands
    }

    fun saveData() {
        val file = File(context.filesDir, "Database.json")
        file.writeText(Gson().toJson(data))
    }

    fun getModelsForBrand(brandId: Int): List<Model> {
        return loadData().brands.find { it.id == brandId }?.models ?: emptyList()
    }

    fun getOffersForModel(brandId: Int, modelId: Int): List<CarOffer> {
        return loadData().brands
            .find { it.id == brandId }
            ?.models
            ?.find { it.id == modelId }
            ?.offers ?: emptyList()
    }

    fun getOfferById(offerId: Int): CarOffer? {
        loadData().brands.forEach { brand ->
            brand.models.forEach { model ->
                model.offers.find { it.id == offerId }?.let { return it }
            }
        }
        return loadData().myOffers.find { it.id == offerId }
    }

    fun getMyOffers(): List<CarOffer> {
        return loadData().myOffers
    }

    fun addOffer(offer: CarOffer): Boolean {
        val carData = loadData()
        val newMyOffers = carData.myOffers.toMutableList()
        newMyOffers.add(offer)
        val brand = carData.brands.find { it.id == offer.brandId }
        val model = brand?.models?.find { it.id == offer.modelId }
        if (model != null) {
            val newOffers = model.offers.toMutableList()
            newOffers.add(offer)
            val newModels = brand.models.map {
                if (it.id == offer.modelId) it.copy(offers = newOffers) else it
            }
            val newBrands = carData.brands.map {
                if (it.id == offer.brandId) it.copy(models = newModels) else it
            }
            data = carData.copy(brands = newBrands, myOffers = newMyOffers)
            saveData()
            return true
        }

        return false
    }


    fun getBrandName(brandId: Int): CharSequence? {
        return loadData().brands.find { it.id == brandId }?.name
    }

    fun getModelName(brandId: Int, modelId: Int): CharSequence? {
        return loadData().brands
            .find { it.id == brandId }
            ?.models
            ?.find { it.id == modelId }
            ?.name
    }

    fun getBrandAndModelName(brandId: Int?, modelId: Int?): String {
        val brandName = getBrandName(brandId!!)
        val modelName = getModelName(brandId, modelId!!)
        return "$brandName $modelName"
    }

    fun clearData() {
        data = null
        val file = File(context.filesDir, "Database.json")
        file.delete()
    }
}