package com.globant.imdb.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.globant.imdb.R
import com.globant.imdb.data.Constants.BASE_IMAGE_URL

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
    textView.text = textView.context.getString(R.string.movie_popularity, "$vote/10")
}