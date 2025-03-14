package ca.ulaval.ima.tp3.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import ca.ulaval.ima.tp3.CarDataService
import ca.ulaval.ima.tp3.ModelsActivity
import ca.ulaval.ima.tp3.R
import ca.ulaval.ima.tp3.adapters.BrandsAdapter
import ca.ulaval.ima.tp3.databinding.FragmentBrandsBinding

class BrandsFragment : Fragment() {
    private lateinit var binding: FragmentBrandsBinding
    private lateinit var carDataService: CarDataService
    private lateinit var brandsAdapter: BrandsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentBrandsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        println("BrandsFragment.onViewCreated")
        super.onViewCreated(view, savedInstanceState)

        carDataService = CarDataService(requireContext())
        val brands = carDataService.getBrands()

        brandsAdapter = BrandsAdapter(brands) { brand ->
            val intent = Intent(context, ModelsActivity::class.java)
            intent.putExtra("brandId", brand.id)
            startActivity(intent)
        }

        binding.recyclerViewBrands.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = brandsAdapter
        }
    }
}
