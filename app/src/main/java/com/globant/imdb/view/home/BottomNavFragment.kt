package com.globant.imdb.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.globant.imdb.R
import com.globant.imdb.databinding.FragmentBottomNavBinding

class BottomNavFragment : Fragment() {

    private var binding: FragmentBottomNavBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentBottomNavBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            bottomNavBottomNavigationView.menu.findItem(R.id.search_icon_bnv).isChecked = true
            bottomNavBottomNavigationView.setOnItemSelectedListener {
                when(it.itemId){
                    R.id.home_icon_bnv -> {
                        replaceFragment(HomeFragment())
                        bottomNavBottomNavigationView.menu.findItem(R.id.home_icon_bnv).isChecked = true
                    }
                    R.id.search_icon_bnv-> {
                        replaceFragment(SearchFragment())
                        bottomNavBottomNavigationView.menu.findItem(R.id.search_icon_bnv).isChecked = true
                    }
                    R.id.play_icon_bnv -> {
                        replaceFragment(PlayFragment())
                        bottomNavBottomNavigationView.menu.findItem(R.id.play_icon_bnv).isChecked = true
                    }
                    R.id.profile_icon_bnv -> {
                        replaceFragment(ProfileFragment())
                        bottomNavBottomNavigationView.menu.findItem(R.id.play_icon_bnv).isChecked = true
                    }
                }
                true
            }
        }
        replaceFragment(SearchFragment())
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = childFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.bottomNav_frame_layout, fragment)
        fragmentTransaction.commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}