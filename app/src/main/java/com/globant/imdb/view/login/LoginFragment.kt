package com.globant.imdb.view.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.globant.imdb.R
import com.globant.imdb.databinding.FragmentLoginBinding
import com.globant.imdb.model.loginfragment.LoginViewModel
import com.globant.imdb.model.loginfragment.LoginViewModelFactory
import com.globant.imdb.repo.LoginRepo

class LoginFragment : Fragment() {

    private var binding: FragmentLoginBinding? = null
    private lateinit var launcher: ActivityResultLauncher<Intent>
    private val loginViewModelFactory = LoginViewModelFactory(LoginRepo())
    private val loginViewModel by lazy {
        ViewModelProvider(this, loginViewModelFactory)[LoginViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentLoginBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            loginFragment = this@LoginFragment
            googleLogin.setOnClickListener {
                loginViewModel.signInGoogle(launcher)
            }
        }
        loginViewModel.apply {
            launcher =
                registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                    initResultHandler(result)
                }
            initLoggingProcess(getString(R.string.default_web_client_id), requireActivity())
            googleLoginStatus.observe(viewLifecycleOwner) {
                if (it) {
                    goToBottomNavFragment()
                } else {
                    Toast.makeText(
                        context,
                        "There was a problem signing you in...",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    fun goToRegisterFragment() {
        val action = LoginFragmentDirections.actionLoginFragmentToRegistrationFragment()
        findNavController().navigate(action)
    }

    private fun goToBottomNavFragment(){
        val action = LoginFragmentDirections.actionLoginFragmentToBottomNavFragment()
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}