package ca.ulaval.ima.tp3.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import ca.ulaval.ima.tp3.R
import ca.ulaval.ima.tp3.databinding.FragmentBrandsBinding
import ca.ulaval.ima.tp3.adapters.BrandAdapter

class BrandsFragment : Fragment() {
    private lateinit var binding: FragmentBrandsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentBrandsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Simuler une liste de marques
        val brands = listOf("Toyota", "Honda", "Ford")

        // Configurer un adaptateur pour afficher les marques
        binding.recyclerViewBrands.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = BrandAdapter(brands) { brand ->
                val fragment = OffresFragment.newInstance(brand)
                parentFragmentManager.beginTransaction()
                    .replace(R.id.view_pager, fragment)
                    .addToBackStack(null)
                    .commit()
            }
        }
    }
}
