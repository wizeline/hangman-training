package com.wizeline.academy.hangman.ui.score

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.wizeline.academy.hangman.data.model.Score
import com.wizeline.academy.hangman.databinding.ItemScoreBinding

class ScoreAdapter : ListAdapter<Score, ScoreAdapter.ScoreViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreViewHolder {
        return LayoutInflater.from(parent.context)
            .let { inflater -> ItemScoreBinding.inflate(inflater, parent, false) }
            .let { binding -> ScoreViewHolder(binding) }
    }

    override fun onBindViewHolder(holder: ScoreViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ScoreViewHolder(
        private val binding: ItemScoreBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(score: Score) {
            binding.apply {

            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Score>() {
            override fun areItemsTheSame(oldItem: Score, newItem: Score): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Score, newItem: Score): Boolean =
                oldItem == newItem
        }
    }

}