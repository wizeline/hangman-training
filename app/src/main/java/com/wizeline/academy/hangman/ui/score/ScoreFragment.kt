package com.wizeline.academy.hangman.ui.score

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.wizeline.academy.hangman.BuildConfig
import com.wizeline.academy.hangman.R
import com.wizeline.academy.hangman.databinding.FragmentScoreBinding
import com.wizeline.academy.hangman.ui.game.GameAdapter
import com.wizeline.academy.hangman.ui.game.GameFragmentArgs
import com.wizeline.academy.hangman.ui.game.GameFragmentDirections
import com.wizeline.academy.hangman.ui.game.GameViewModel
import com.wizeline.academy.hangman.util.TIME_SECONDS
import com.wizeline.academy.hangman.util.setPosition
import com.wizeline.academy.hangman.util.toGameAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScoreFragment : Fragment() {

    private var _binding: FragmentScoreBinding? = null
    private val binding get() = _binding

    private lateinit var navController: NavController
    private lateinit var scoreAdapter: ScoreAdapter
    private val viewModel: ScoreViewModel by viewModels()
    private var userName = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return FragmentScoreBinding.inflate(layoutInflater, container, false)
            .apply { _binding = this }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = GameFragmentArgs.fromBundle(requireArguments())
        userName = args.name
        scoreAdapter = ScoreAdapter()
        initView()
        initObservers()
        buttonListeners()
        setFooter()
    }

    private fun initView(){
        navController = findNavController()
        viewModel.getScoreList()
    }

    private fun initObservers(){
        viewModel.apply {
            scoreList.observe(viewLifecycleOwner) {
                if (it?.isNotEmpty() == true) {
                    binding?.scoreList?.adapter = scoreAdapter
                    scoreAdapter.submitList(it.setPosition())
                }
            }
        }
    }

    private fun buttonListeners(){
        binding?.scoreBtnFinish?.setOnClickListener {
            ScoreFragmentDirections.actionScoreFragmentToHomeFragment().let {
                navController.navigate(it)
            }
        }
        binding?.scoreBtnRetry?.setOnClickListener {
            ScoreFragmentDirections.actionScoreFragmentToGameFragment(userName).let {
                navController.navigate(it)
            }
        }
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