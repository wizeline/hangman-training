package com.wizeline.academy.hangman.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.wizeline.academy.hangman.BuildConfig
import com.wizeline.academy.hangman.R
import com.wizeline.academy.hangman.databinding.FragmentHomeBinding

class HomeFragment : Fragment(){

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return FragmentHomeBinding.inflate(layoutInflater, container, false)
            .apply { _binding = this }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFooter()
    }

    private fun setFooter(){
        binding?.footerView?.footerDate?.text = getString(R.string.app_date)
        binding?.footerView?.footerVersion?.text = BuildConfig.VERSION_NAME;
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}