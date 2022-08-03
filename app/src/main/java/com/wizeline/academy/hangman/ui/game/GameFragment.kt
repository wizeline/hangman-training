package com.wizeline.academy.hangman.ui.game

import android.app.Dialog
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.flexbox.*
import com.wizeline.academy.hangman.BuildConfig
import com.wizeline.academy.hangman.R
import com.wizeline.academy.hangman.databinding.FragmentGameBinding
import com.wizeline.academy.hangman.util.*
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class GameFragment: Fragment() {

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding

    private val viewModel: GameViewModel by viewModels()
    private lateinit var navController: NavController

    private lateinit var gameAdapter: GameAdapter
    private var userName = ""
    private var score = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return FragmentGameBinding.inflate(layoutInflater, container, false)
            .apply { _binding = this }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = GameFragmentArgs.fromBundle(requireArguments())
        userName = args.name
        gameAdapter = GameAdapter()
        setFooter()
        initView(userName)
        buttonListeners()
        initObservers()
        getMovie()
    }

    private fun initObservers(){
        viewModel.apply {
            movie.observe(viewLifecycleOwner) {
                if (it?.isNotEmpty() == true) {
                    binding?.word?.text = it.joinToString("")
                    binding?.wordList?.adapter = gameAdapter
                    gameAdapter.submitList(it.toGameAdapter())
                }
            }
            error.observe(viewLifecycleOwner){
                Toast.makeText(context, "Error fetching -> $it", Toast.LENGTH_SHORT).show()

            }

            timer.observe((viewLifecycleOwner)){
                setCounter(it)
            }

            //Word Counter
            wordCounter.observe(viewLifecycleOwner){
                setWordCounter(it)
            }

            endGame.observe(viewLifecycleOwner){
                endGame()
            }
        }
    }
    private fun getMovie() = viewModel.getMovie()

    private fun buttonListeners(){

        binding?.gameHintBtn?.setOnClickListener {  }
        binding?.gameNextBtn?.setOnClickListener {
            getMovie()
        }
    }

    fun onKeyUp(event: KeyEvent){
        val key = event.unicodeChar.toChar().toString()
        searchLetter(key)
    }

    private fun checkWordGuessed(){
        val word = gameAdapter.currentList.toList()
        if(word.isWordVisible()){
            getMovie()
        }
    }
    private fun initView(userName: String){
        navController = findNavController()
        binding?.gameUsername?.text = userName
        setLayoutManager()
        viewModel.runTimer()
        setTime(TIME_SECONDS)
        setWordCounter(0)
        setScore(0)
    }

    private fun setLayoutManager(){
        val layoutManager = FlexboxLayoutManager(context)
        layoutManager.apply {
            flexDirection = FlexDirection.ROW
            justifyContent = JustifyContent.CENTER
            flexWrap = FlexWrap.WRAP
        }
        binding?.wordList?.layoutManager = layoutManager
    }


    private fun setTime(time: Long){
        binding?.gameTime?.text = time.timerFormat()
    }

    private fun setWordCounter(wordCounter: Int = 0){
        binding?.gameWord?.text = getString(R.string.game_word_counter, wordCounter, MAX_WORDS)

    }
    private fun setScore(addScore: Int = 0){
        score = if((score + addScore) > 0) score + addScore else 0
        binding?.gameScore?.text = getString(R.string.game_score, score)
    }

    private fun endGame(){
        viewModel.saveScore(userName, score)
        viewModel.resetData()
        showDialog()
    }

    private fun searchLetter(letter: String){

        val filterData = gameAdapter.currentList.toList()
        if(filterData.hasLetter(letter)){
            if(!filterData.isVisible(letter)){
                setScore(CORRECT_GUESS)
            }
            gameAdapter.submitList(filterData.setVisibleLetter(letter))
            gameAdapter.notifyDataSetChanged()
            checkWordGuessed()
        }else{
            setScore(INCORRECT_GUESS)
        }
    }

    private fun setFooter(){
        binding?.footerView?.footerDate?.text = getString(R.string.app_date)
        binding?.footerView?.footerVersion?.text = BuildConfig.VERSION_NAME;
    }

    private fun showDialog(){
        val dialog = context?.let { Dialog(it) }

        dialog?.setContentView(R.layout.dialog_game)
        dialog?.setCancelable(false);
        dialog?.show()
        val tryAgainBtn: Button = dialog?.findViewById(R.id.dialog_again) as Button
        tryAgainBtn.setOnClickListener(View.OnClickListener {
            dialog.dismiss()
        })
        val leaderboardBtn: Button = dialog.findViewById(R.id.dialog_leaderboard) as Button
        leaderboardBtn.setOnClickListener(View.OnClickListener {
            dialog.dismiss()
            GameFragmentDirections.actionGameFragmentToScoreFragment(userName).let {
                navController.navigate(it)
            }
        })
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}