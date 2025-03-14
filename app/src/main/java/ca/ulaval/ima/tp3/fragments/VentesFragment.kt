package ca.ulaval.ima.tp3.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ca.ulaval.ima.tp3.R
import ca.ulaval.ima.tp3.CarDataService
import ca.ulaval.ima.tp3.models.CarOffer
import ca.ulaval.ima.tp3.models.SharedViewModel
import java.util.*

class VentesFragment : Fragment() {
    private lateinit var carDataService: CarDataService
    private var offerAddedListener: OfferAddedListener? = null
    private lateinit var viewModel: SharedViewModel

    interface OfferAddedListener {
        fun onOfferAdded()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OfferAddedListener) {
            offerAddedListener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_vente, container, false)

        carDataService = CarDataService(requireContext())
        viewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        val brandSpinner: Spinner = view.findViewById(R.id.brandSpinner)
        val modelSpinner: Spinner = view.findViewById(R.id.modelSpinner)
        val yearEditText: EditText = view.findViewById(R.id.yearEditText)
        val priceEditText: EditText = view.findViewById(R.id.priceEditText)
        val kmEditText: EditText = view.findViewById(R.id.kmEditText)
        val transmissionSpinner: Spinner = view.findViewById(R.id.transmissionSpinner)
        val nameEditText: EditText = view.findViewById(R.id.nameEditText)
        val emailEditText: EditText = view.findViewById(R.id.emailEditText)
        val imageUrlEditText: EditText = view.findViewById(R.id.imageUrlEditText)
        val submitButton: Button = view.findViewById(R.id.submitButton)
        val brands = carDataService.getBrands()
        val brandNames = brands.map { it.name }
        val brandAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, brandNames)
        brandSpinner.adapter = brandAdapter

        brandSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedBrandId = brands[position].id
                val models = carDataService.getModelsForBrand(selectedBrandId)
                val modelNames = models.map { it.name }
                val modelAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, modelNames)
                modelSpinner.adapter = modelAdapter
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        val transmissions = listOf("Automatique", "Manuelle")
        val transmissionAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, transmissions)
        transmissionSpinner.adapter = transmissionAdapter

        yearEditText.setText("2020")
        priceEditText.setText("15000")
        kmEditText.setText("30000")
        nameEditText.setText("Utilisateur")
        emailEditText.setText("user@email.com")
        imageUrlEditText.setText("https://images.caradisiac.com/logos-ref/modele/modele--renault-clio-4/S8-modele--renault-clio-4.jpg")

        val defaultBrandIndex = brands.indexOfFirst { it.name == "Renault" }
        if (defaultBrandIndex != -1) {
            brandSpinner.setSelection(defaultBrandIndex)
            val defaultModels = carDataService.getModelsForBrand(brands[defaultBrandIndex].id)
            val defaultModelIndex = defaultModels.indexOfFirst { it.name == "Clio" }
            if (defaultModelIndex != -1) {
                modelSpinner.setSelection(defaultModelIndex)
            }
        }

        transmissionSpinner.setSelection(1) // 1 = "Manuelle"

        submitButton.setOnClickListener {
            val selectedBrand = brands[brandSpinner.selectedItemPosition]
            val selectedModel = carDataService.getModelsForBrand(selectedBrand.id)[modelSpinner.selectedItemPosition]

            val newOffer = CarOffer(
                id = Random().nextInt(99999),
                year = yearEditText.text.toString().toIntOrNull() ?: 2020,
                price = priceEditText.text.toString().toIntOrNull() ?: 10000,
                kilometers = kmEditText.text.toString().toIntOrNull() ?: 50000,
                description = "",
                transmissions = transmissionSpinner.selectedItem.toString(),
                imageUrl = imageUrlEditText.text.toString(),
                sellerEmail = emailEditText.text.toString(),
                sellerName = nameEditText.text.toString(),
                sellerProprio = "oui",
                isMyOffer = true,
                brandId = selectedBrand.id,
                modelId = selectedModel.id
            )

            if (carDataService.addOffer(newOffer)) {
                Toast.makeText(requireContext(), "Offre ajoutée avec succès !", Toast.LENGTH_SHORT).show()
                var z = carDataService.getOffersForModel(selectedBrand.id, selectedModel.id)
                if (z.contains(newOffer)) {
                    println("Offer added successfully")
                } else {
                    println("Offer not added")
                }
                viewModel.updateOffers(carDataService.getMyOffers())
                offerAddedListener?.onOfferAdded()
            } else {
                Toast.makeText(requireContext(), "Erreur lors de l'ajout.", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }
}
