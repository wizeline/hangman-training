package com.wizeline.academy.hangman.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.wizeline.academy.hangman.BuildConfig
import com.wizeline.academy.hangman.R
import com.wizeline.academy.hangman.databinding.FragmentHomeBinding
import com.wizeline.academy.hangman.ui.game.GameViewModel
import com.wizeline.academy.hangman.util.isValid
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(){

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding

    private lateinit var navController: NavController

    private val viewModel: HomeViewModel by viewModels()

    private var userName = ""


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
        buttonListener()
        setObservables()
        navController = findNavController()
    }

    private fun buttonListener(){

        binding?.homeStartBtn?.setOnClickListener {

            userName = binding?.homeEdittextExecutor?.text.toString()
            if(userName.isValid()) {
                viewModel.validateUserName(userName)
            } else {
                Toast.makeText(context, getString(R.string.invalid_username), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setObservables(){
        viewModel.apply {
            validUserName.observe(viewLifecycleOwner) { result ->
                if(result) {
                    viewModel.saveUserName(userName)
                    HomeFragmentDirections.actionHomeFragmentToGameFragment(userName).let {
                        navController.navigate(it)
                    }
                }else{
                    Toast.makeText(context, getString(R.string.exists_username), Toast.LENGTH_SHORT).show()
                }
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