package ca.ulaval.ima.tp3

import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import ca.ulaval.ima.tp3.databinding.ActivityTabBarBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import ca.ulaval.ima.tp3.fragments.AnnonceFragment
import ca.ulaval.ima.tp3.fragments.BrandsFragment
import ca.ulaval.ima.tp3.fragments.VentesFragment

class SectionsPagerAdapter(private val context: android.content.Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> BrandsFragment()
            1 -> VentesFragment()
            2 -> AnnonceFragment()
            else -> BrandsFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "OFFRES"
            1 -> "VENDRE"
            2 -> "MES ANNONCES"
            else -> null
        }
    }

    override fun getCount(): Int {
        return 3
    }
}

class TabBar : AppCompatActivity() {

    private lateinit var binding: ActivityTabBarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTabBarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sectionsPagerAdapter = ca.ulaval.ima.tp3.SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        tabs.tabTextColors = resources.getColorStateList(R.color.white, theme)
        tabs.setupWithViewPager(viewPager)
    }
}
