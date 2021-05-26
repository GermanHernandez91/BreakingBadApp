package com.example.breakingbadapp.bindingadapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load

class CharactersBindingAdapters {

    companion object {

        @BindingAdapter("loadImageFromUrl")
        @JvmStatic
        fun loadImageFromUrl(imageView: ImageView, url: String) {
            imageView.load(url) {
                crossfade(600)
            }
        }
    }
}