package ca.ulaval.ima.tp3.models

data class CarBrand(val id: String, val name: String, val logo: String? = null)

data class CarModel(val id: String, val brandId: String, val name: String, val image: String? = null)

data class Offer(
    val id: String,
    val modelId: String,
    val title: String,
    val price: Double,
    val description: String,
    val sellerName: String,
    val sellerEmail: String,
    val imageUrl: String,
    val features: List<String>
)