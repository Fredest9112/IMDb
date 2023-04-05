package com.globant.imdb.view.splash

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.globant.imdb.R
import com.globant.imdb.model.splashFragment.SplashViewModel
import com.globant.imdb.model.splashFragment.SplashViewModelFactory
import com.globant.imdb.utils.DatabaseResult
import com.globant.imdb.utils.ToastCreator
import com.globant.imdb.view.MyIMDbApp
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject
import kotlinx.coroutines.launch

class SplashFragment : Fragment() {

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    @Inject
    lateinit var splashViewModelFactory: SplashViewModelFactory
    private val splashViewModel by lazy {
        ViewModelProvider(this, splashViewModelFactory)[SplashViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireContext().applicationContext as MyIMDbApp).appComponent.injectSplashFragment(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        splashViewModel.apply {
            lifecycleScope.launch {
                deleteMoviesOnDB()
                saveTopRatedMoviesToDB()
                isDataSaved.observe(viewLifecycleOwner) {
                    when(it){
                        is DatabaseResult.DatabaseSuccess -> {
                            initSharedPreferences(requireActivity())
                            val currentUser = firebaseAuth.currentUser
                            if (currentUser != null || checkLoginPreferences()) {
                                val action =
                                    SplashFragmentDirections.actionSplashFragmentToBottomNavFragment()
                                findNavController().navigate(action)
                            } else {
                                val action =
                                    SplashFragmentDirections.actionSplashFragmentToLoginFragment()
                                findNavController().navigate(action)
                            }
                        }
                        is DatabaseResult.DatabaseError -> {
                            if(it.message.isNotEmpty()){
                                ToastCreator.showToastMessage(context, it.message)
                            }
                        }
                    }
                }
            }
        }
    }
}