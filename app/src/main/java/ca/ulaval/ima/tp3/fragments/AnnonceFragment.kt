package ca.ulaval.ima.tp3.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ca.ulaval.ima.tp3.CarDataService
import ca.ulaval.ima.tp3.R
import ca.ulaval.ima.tp3.adapters.OffersAdapter
import ca.ulaval.ima.tp3.databinding.FragmentAnnonceBinding
import ca.ulaval.ima.tp3.models.SharedViewModel
class AnnonceFragment : Fragment() {
    private lateinit var binding: FragmentAnnonceBinding
    private lateinit var carDataService: CarDataService
    private lateinit var offersAdapter: OffersAdapter
    private lateinit var viewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAnnonceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        carDataService = CarDataService(requireContext())
        viewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        // Initialiser offersAdapter avec une liste vide pour éviter l'erreur
        offersAdapter = OffersAdapter(emptyList()) { offer -> }

        viewModel.offers.observe(viewLifecycleOwner, Observer { offers ->
            offersAdapter = OffersAdapter(offers) { offer -> }
            binding.recyclerViewMyOffers.adapter = offersAdapter
        })

        // Configuration du RecyclerView
        binding.recyclerViewMyOffers.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = offersAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        if (::carDataService.isInitialized && ::binding.isInitialized) {
            val myOffers = carDataService.getMyOffers()
            offersAdapter = OffersAdapter(myOffers) { offer -> }
        }
    }
}
