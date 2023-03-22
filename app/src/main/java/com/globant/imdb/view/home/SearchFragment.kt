package com.globant.imdb.view.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.globant.imdb.databinding.FragmentSearchBinding
import com.globant.imdb.repo.MoviesRepo
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

    private var binding: FragmentSearchBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val fragmentBinding = FragmentSearchBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            val topRatedMovies = MoviesRepo().getTopRatedMovies()
            Log.i("topRatedMovies", "$topRatedMovies")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}