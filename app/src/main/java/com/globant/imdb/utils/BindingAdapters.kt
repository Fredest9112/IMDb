package com.globant.imdb.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.globant.imdb.R
import com.globant.imdb.data.Constants.BASE_IMAGE_URL
import com.google.android.material.textfield.TextInputLayout
import java.text.DecimalFormat

@BindingAdapter("movieImage")
fun bindMovieImageToMovieSearchImageView(imageView: ImageView, url: String?) {
    url?.let {
        if (it.isNotEmpty()) {
            Glide.with(imageView.context).load(BASE_IMAGE_URL + url).into(imageView)
        }
    }
}

@BindingAdapter("appendVoteAverage")
fun appendPopularityToTextView(textView: TextView, vote: Double) {
    val decimalFormat = DecimalFormat("#.#")
    val formattedVote = decimalFormat.format(vote)
    textView.text = textView.context.getString(R.string.movie_popularity, "$formattedVote/10")
}

@BindingAdapter("isPasswordValid")
fun isPasswordValid(passwordFrameInput: TextInputLayout, isPasswordValid: Boolean){
    when(isPasswordValid){
        true -> passwordFrameInput.error = null
        false -> passwordFrameInput.error = "Invalid password"
    }
}

@BindingAdapter("isEmailValid")
fun isEmailValid(emailFrameInput: TextInputLayout, isEmailValid: Boolean){
    when(isEmailValid){
        true -> emailFrameInput.error = null
        false -> emailFrameInput.error = "Invalid email"
    }
}

@BindingAdapter("isUsernameValid")
fun isUsernameValid(emailFrameInput: TextInputLayout, isUsernameValid: Boolean){
    when(isUsernameValid){
        true -> emailFrameInput.error = null
        false -> emailFrameInput.error = "Invalid username"
    }
}