package com.wizeline.academy.hangman.ui.score

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.wizeline.academy.hangman.databinding.FragmentScoreBinding

class ScoreFragment : Fragment() {

    private var _binding: FragmentScoreBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return FragmentScoreBinding.inflate(layoutInflater, container, false)
            .apply { _binding = this }
            .root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}