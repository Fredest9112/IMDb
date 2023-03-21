package com.globant.imdb.view.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.globant.imdb.R
import com.globant.imdb.databinding.FragmentRegistrationBinding

class RegistrationFragment : Fragment() {

    private var binding: FragmentRegistrationBinding? = null

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
        binding?.apply {
            registrationFragment = this@RegistrationFragment
            usernameFrame.typeface = context?.let { ResourcesCompat.getFont(it,
                R.font.roboto_light_italic
            ) }
            usernameInput.typeface = context?.let { ResourcesCompat.getFont(it, R.font.roboto) }
        }
    }

    fun goToBottomNavFragment(){
        val action = RegistrationFragmentDirections.actionRegistrationFragmentToBottomNavFragment()
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}