package com.wizeline.academy.hangman.ui.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.wizeline.academy.hangman.databinding.FragmentGameBinding

class GameFragment: Fragment() {

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return FragmentGameBinding.inflate(layoutInflater, container, false)
            .apply { _binding = this }
            .root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}