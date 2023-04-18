package com.globant.imdb.view.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.globant.imdb.database.asDBMovie
import com.globant.imdb.databinding.FragmentProfileBinding
import com.globant.imdb.model.profileFragment.ProfileViewModel
import com.globant.imdb.model.profileFragment.ProfileViewModelFactory
import com.globant.imdb.view.MyIMDbApp
import com.globant.imdb.view.adapter.MostPopularMovieAdapter
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class ProfileFragment : Fragment() {

    private var binding: FragmentProfileBinding? = null

    @Inject
    lateinit var profileViewModelFactory: ProfileViewModelFactory

    @Inject
    lateinit var recentWatchListMovieAdapter: MostPopularMovieAdapter

    @Inject
    lateinit var watchListMovieAdapter: MostPopularMovieAdapter

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

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
        val currentUser = firebaseAuth.currentUser
        binding?.apply {
            recentWatchRv.adapter = recentWatchListMovieAdapter
            watchListRv.adapter = watchListMovieAdapter
            profileViewModel.apply {
                recentWatchedMovies.observe(viewLifecycleOwner){
                    when {
                        !it.isNullOrEmpty() -> {
                            recentwatchDesc.visibility = View.GONE
                            recentWatchRv.visibility = View.VISIBLE
                            recentWatchListMovieAdapter.submitList(it.asDBMovie())
                        }
                        else -> {
                            recentwatchDesc.visibility = View.VISIBLE
                            recentWatchRv.visibility = View.INVISIBLE
                        }
                    }
                }
                watchListMovies.observe(viewLifecycleOwner){
                    when {
                        !it.isNullOrEmpty() -> {
                            watchlistDesc.visibility = View.GONE
                            watchListRv.visibility = View.VISIBLE
                            watchListMovieAdapter.submitList(it.asDBMovie())
                        }
                        else -> {
                            watchlistDesc.visibility = View.VISIBLE
                            watchListRv.visibility = View.INVISIBLE
                        }
                    }
                }
            }
            if(currentUser != null){
                profileUsername.text = currentUser.email
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}