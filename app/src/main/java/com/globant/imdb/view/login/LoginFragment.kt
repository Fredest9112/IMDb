package com.globant.imdb.view.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.globant.imdb.R
import com.globant.imdb.databinding.FragmentLoginBinding
import com.globant.imdb.model.loginFragment.LoginViewModel
import com.globant.imdb.model.loginFragment.LoginViewModelFactory
import com.globant.imdb.utils.AuthResult
import com.globant.imdb.utils.ToastCreator
import com.globant.imdb.view.MyIMDbApp
import javax.inject.Inject

class LoginFragment : Fragment() {

    private var binding: FragmentLoginBinding? = null
    private lateinit var launcher: ActivityResultLauncher<Intent>

    @Inject
    lateinit var loginViewModelFactory: LoginViewModelFactory
    private val loginViewModel by lazy {
        ViewModelProvider(this, loginViewModelFactory)[LoginViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireContext().applicationContext as MyIMDbApp).appComponent.injectLoginFragment(this)
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
        loginViewModel.apply {
            initSharedPreferences(requireActivity())
            binding?.apply {
                viewModel = loginViewModel
                lifecycleOwner = viewLifecycleOwner
            }
            //SignIn Google
            binding?.apply {
                googleLogin.setOnClickListener {
                    loginViewModel.signInGoogle(launcher)
                }
                launcher =
                    registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                        initResultHandler(result)
                    }
                initLoggingProcess(getString(R.string.default_web_client_id), requireActivity())
                googleLoginStatus.observe(viewLifecycleOwner) {
                    when (it) {
                        is AuthResult.Success -> {
                            ToastCreator.showToastMessage(context, it.message)
                            saveLoginPreferences(true)
                            goToBottomNavFragment()
                        }
                        is AuthResult.Error -> {
                            ToastCreator.showToastMessage(context, it.message)
                        }
                        else -> {}
                    }
                }
            }
            //SignIn with username/email password
            binding?.apply {
                emailTextInput.doOnTextChanged { enteredText, _, _, _ ->
                    setEmailInput(enteredText.toString())
                }
                passwordTextInput.doOnTextChanged { enteredText, _, _, _ ->
                    setPasswordInput(enteredText.toString())
                }
                loginButton.setOnClickListener {
                    signInWithEmailAndPassword()
                }
            }
            loginStatus.observe(viewLifecycleOwner) {
                when (it) {
                    is AuthResult.Success -> {
                        ToastCreator.showToastMessage(context, it.message)
                        saveLoginPreferences(true)
                        goToBottomNavFragment()
                    }
                    is AuthResult.Error -> ToastCreator.showToastMessage(context, it.message)
                }
            }
            //SignUp registration
            binding?.questionRegistration?.setOnClickListener {
                goToRegisterFragment()
            }
        }
    }

    private fun goToRegisterFragment() {
        val action = LoginFragmentDirections.actionLoginFragmentToRegistrationFragment()
        findNavController().navigate(action)
    }

    private fun goToBottomNavFragment() {
        val action = LoginFragmentDirections.actionLoginFragmentToBottomNavFragment()
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}