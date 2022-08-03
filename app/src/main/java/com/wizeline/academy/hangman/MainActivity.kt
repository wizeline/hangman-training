package com.wizeline.academy.hangman

import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.wizeline.academy.hangman.databinding.ActivityMainBinding
import com.wizeline.academy.hangman.ui.game.GameFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent): Boolean {

        val navController = findNavController(R.id.nav_host)

        val id = navController.currentDestination?.id

        if(id == R.id.gameFragment){

            val fragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
            val gameFragment = fragment.childFragmentManager.fragments[0] as GameFragment
            gameFragment.onKeyUp(event)
        }

        return true
    }
}