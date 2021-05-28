package com.example.breakingbadapp.bindingadapters

import android.util.Log
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import coil.load
import com.example.breakingbadapp.models.Character
import com.example.breakingbadapp.ui.fragments.characters.CharactersListFragmentDirections
import java.lang.Exception

class CharactersBindingAdapters {

    companion object {

        @BindingAdapter("onCharacterClickListener")
        @JvmStatic
        fun onCharacterClickListener(rowLayout: ConstraintLayout, character: Character) {
            rowLayout.setOnClickListener {
                try {
                    val action = CharactersListFragmentDirections.actionCharactersListFragmentToCharacterDetailsFragment(character.charId)
                    rowLayout.findNavController().navigate(action)
                } catch (e: Exception) {
                    Log.d("onCharClickListener", e.toString())
                }
            }
        }
    }
}