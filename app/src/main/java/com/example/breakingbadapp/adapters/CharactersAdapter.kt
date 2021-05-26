package com.example.breakingbadapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.breakingbadapp.databinding.CharacterRowLayoutBinding
import com.example.breakingbadapp.models.Character
import com.example.breakingbadapp.models.Characters
import com.example.breakingbadapp.utils.ListDiffUtil

class CharactersAdapter : RecyclerView.Adapter<CharactersAdapter.CharactersViewHolder>() {

    private var characters = emptyList<Character>()

    class CharactersViewHolder(
        private val binding: CharacterRowLayoutBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(character: Character) {
            binding.character = character
            binding.executePendingBindings()
        }

        companion object {

            fun from(parent: ViewGroup): CharactersViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CharacterRowLayoutBinding.inflate(layoutInflater, parent, false)
                return CharactersViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        return CharactersViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        holder.bind(characters[position])
    }

    override fun getItemCount(): Int {
        return characters.size
    }

    fun setData(newData: Characters) {
        val listDiffUtil = ListDiffUtil(characters, newData)
        val diffUtilResult = DiffUtil.calculateDiff(listDiffUtil)
        characters = newData
        diffUtilResult.dispatchUpdatesTo(this)
    }
}