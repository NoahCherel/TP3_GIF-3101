package ca.ulaval.ima.tp3.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import ca.ulaval.ima.tp3.databinding.FragmentOffresBinding
import ca.ulaval.ima.tp3.models.Offer
import ca.ulaval.ima.tp3.adapters.OfferAdapter

class OffresFragment : Fragment() {
    private lateinit var binding: FragmentOffresBinding
    private lateinit var brand: String

    companion object {
        private const val ARG_BRAND = "brand"

        fun newInstance(brand: String): OffresFragment {
            return OffresFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_BRAND, brand)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            brand = it.getString(ARG_BRAND, "")
        } ?: throw IllegalArgumentException("brand must be provided")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentOffresBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
        }

        loadOffers(brand)
    }

    private fun loadOffers(brand: String) {
        // Simuler le chargement des offres pour une marque spécifique
        val offers = listOf(
            Offer("1", brand, "Toyota Corolla 2023", 25000.0, "Belle voiture...", "Jean Dupont", "jean@example.com", "url_image", listOf("Climatisation", "Bluetooth")),
            Offer("2", brand, "Toyota Corolla 2022", 22000.0, "Occasion parfaite...", "Marie Martin", "marie@example.com", "url_image", listOf("GPS", "Caméra de recul"))
        )

        // Mettez à jour l'adaptateur avec les offres chargées
        binding.recyclerView.adapter = OfferAdapter(offers)
    }
}
