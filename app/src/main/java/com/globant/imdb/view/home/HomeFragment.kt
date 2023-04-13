package com.globant.imdb.view.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.globant.imdb.databinding.FragmentHomeBinding
import com.globant.imdb.model.homeFragment.HomeViewModel
import com.globant.imdb.model.homeFragment.HomeViewModelFactory
import com.globant.imdb.utils.NetworkResult
import com.globant.imdb.utils.ToastCreator
import com.globant.imdb.view.MyIMDbApp
import javax.inject.Inject

class HomeFragment : Fragment() {

    private var binding: FragmentHomeBinding? = null

    @Inject
    lateinit var homeViewModelFactory: HomeViewModelFactory
    private val homeViewModel by lazy {
        ViewModelProvider(this, homeViewModelFactory)[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val fragmentBinding = FragmentHomeBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireContext().applicationContext as MyIMDbApp).appComponent.injectHomeFragment(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            homeViewModel.mostPopularMovies.observe(viewLifecycleOwner){
                when (it){
                    is NetworkResult.MoviesSuccess -> {

                    }
                    is NetworkResult.MoviesError -> {
                        ToastCreator.showToastMessage(context, it.message)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}