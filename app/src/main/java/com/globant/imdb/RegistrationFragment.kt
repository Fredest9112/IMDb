package com.globant.imdb

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
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
            usernameFrame.typeface = context?.let { ResourcesCompat.getFont(it, R.font.roboto_light_italic) }
            usernameInput.typeface = context?.let { ResourcesCompat.getFont(it, R.font.roboto) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}