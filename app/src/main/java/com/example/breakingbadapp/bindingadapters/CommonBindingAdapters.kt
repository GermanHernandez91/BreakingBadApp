package com.example.breakingbadapp.bindingadapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.CircleCropTransformation
import com.example.breakingbadapp.models.Character
import com.example.breakingbadapp.utils.NetworkResult

class CommonBindingAdapters {

    companion object {

        @BindingAdapter("loadImageFromUrl")
        @JvmStatic
        fun loadImageFromUrl(imageView: ImageView, url: String) {
            imageView.load(url) {
                crossfade(600)
                transformations(CircleCropTransformation())
            }
        }

        @BindingAdapter("isLoading")
        @JvmStatic
        fun loadingViewVisibility(
            view: View,
            apiResponse: NetworkResult<Character>?
        ) {
            when (apiResponse) {
                is NetworkResult.Loading -> { view.visibility = View.VISIBLE }
                is NetworkResult.Success -> { view.visibility = View.INVISIBLE }
                is NetworkResult.Error -> { view.visibility = View.INVISIBLE }
            }
        }

        @BindingAdapter("shouldDisplayData")
        @JvmStatic
        fun detailsViewVisibility(
            view: View,
            apiResponse: NetworkResult<Character>?
        ) {
            when (apiResponse) {
                is NetworkResult.Loading -> { view.visibility = View.INVISIBLE }
                is NetworkResult.Success -> { view.visibility = View.VISIBLE }
                is NetworkResult.Error -> { view.visibility = View.INVISIBLE }
            }
        }

        @BindingAdapter("setSeasons")
        @JvmStatic
        fun setSeasons(textView: TextView, seasons: List<Float>?) {
            val listFormatted = seasons?.joinToString(separator = ", ")
            ("Seasons: $listFormatted").also { textView.text = it }
        }
    }
}