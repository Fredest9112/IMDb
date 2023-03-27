package com.globant.imdb.view.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.globant.imdb.R
import com.globant.imdb.databinding.FragmentRegistrationBinding
import com.globant.imdb.model.registrationFragment.RegistrationViewModel
import com.globant.imdb.model.registrationFragment.RegistrationViewModelFactory
import com.globant.imdb.repo.LoginRepo

class RegistrationFragment : Fragment() {

    private var binding: FragmentRegistrationBinding? = null
    private val registrationViewModelFactory = RegistrationViewModelFactory(LoginRepo())
    private val registrationViewModel by lazy {
        ViewModelProvider(this, registrationViewModelFactory)[RegistrationViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentRegistrationBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registrationViewModel.apply {
            binding?.apply {
                viewModel = registrationViewModel
                lifecycleOwner = viewLifecycleOwner
                usernameFrame.typeface = context?.let { ResourcesCompat.getFont(it, R.font.roboto) }
                usernameInput.typeface = context?.let { ResourcesCompat.getFont(it, R.font.roboto_light_italic) }
                emailFrame.typeface = context?.let { ResourcesCompat.getFont(it, R.font.roboto) }
                emailInput.typeface = context?.let { ResourcesCompat.getFont(it, R.font.roboto_light_italic) }
                passwordFrame.typeface = context?.let { ResourcesCompat.getFont(it, R.font.roboto) }
                passwordInput.typeface = context?.let { ResourcesCompat.getFont(it, R.font.roboto_light_italic) }
            }
            //Registration with user/email
            binding?.apply {
                usernameInput.doOnTextChanged { enteredText, _, _, _ ->
                    setUsernameInput(enteredText.toString())
                }
                emailInput.doOnTextChanged { enteredText, _, _, _ ->
                    setEmailInput(enteredText.toString().trim())
                }
                passwordInput.doOnTextChanged { enteredText, _, _, _ ->
                    setPasswordInput(enteredText.toString())
                }
                acceptButton.setOnClickListener {
                    signUpWithEmailAndPassword()
                }
            }
            loginStatus.observe(viewLifecycleOwner) {
                if (it) {
                    goToLoginFragment()
                } else {
                    Toast.makeText(context, "There was a problem signing you up...", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun goToLoginFragment(){
        val action = RegistrationFragmentDirections.actionRegistrationFragmentToLoginFragment()
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}