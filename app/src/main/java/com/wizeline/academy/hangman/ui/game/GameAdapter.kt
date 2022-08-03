package com.wizeline.academy.hangman.ui.game

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.wizeline.academy.hangman.data.model.Letter
import com.wizeline.academy.hangman.databinding.ItemLetterBinding

class GameAdapter : ListAdapter<Letter, GameAdapter.GameViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        return LayoutInflater.from(parent.context)
            .let { inflater -> ItemLetterBinding.inflate(inflater, parent, false) }
            .let { binding -> GameViewHolder(binding) }
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class GameViewHolder(
        private val binding: ItemLetterBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(word: Letter) {
            binding.apply {
                if(word.letter != " "){
                    itemLetter.text = word.letter
                    itemLetter.visibility = if(word.isVisible) View.VISIBLE else View.INVISIBLE
                    itemLetterLy.visibility = View.VISIBLE
                }
                else {
                    itemLetter.text = ""
                    itemLetter.visibility = View.INVISIBLE
                    itemLetterLy.visibility = View.INVISIBLE
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Letter>() {
            override fun areItemsTheSame(oldItem: Letter, newItem: Letter): Boolean =
                oldItem.letter == newItem.letter

            override fun areContentsTheSame(oldItem: Letter, newItem: Letter): Boolean =
                oldItem == newItem
        }
    }

}