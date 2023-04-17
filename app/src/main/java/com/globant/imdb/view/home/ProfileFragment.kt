package com.globant.imdb.view.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.globant.imdb.databinding.FragmentProfileBinding
import com.globant.imdb.model.profileFragment.ProfileViewModel
import com.globant.imdb.model.profileFragment.ProfileViewModelFactory
import com.globant.imdb.view.MyIMDbApp
import javax.inject.Inject

class ProfileFragment : Fragment() {

    private var binding: FragmentProfileBinding? = null

    @Inject
    lateinit var profileViewModelFactory: ProfileViewModelFactory
    private val profileViewModel by lazy {
        ViewModelProvider(this, profileViewModelFactory)[ProfileViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val fragmentBinding = FragmentProfileBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireContext().applicationContext as MyIMDbApp).appComponent.injectProfileFragment(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            profileViewModel.apply {
                favoriteMovies.observe(viewLifecycleOwner){
                    testText.text = it.toString()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}