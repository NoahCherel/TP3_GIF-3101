package ca.ulaval.ima.tp3.models

data class Brand(
    val id: Int,
    val name: String,
    val models: List<Model> = emptyList()
)

data class Model(
    val id: Int,
    val brandId: Int,
    val name: String,
    val offers: List<CarOffer> = emptyList()
)

data class CarOffer(
    val id: Int,
    val year: Int,
    val price: Int,
    val kilometers: Int,
    val description: String,
    val transmissions: String,
    val imageUrl: String,
    val sellerEmail: String,
    val sellerName: String,
    val sellerProprio: String,
    val isMyOffer: Boolean = false,
    val brandId: Int? = null,
    val modelId: Int? = null
)