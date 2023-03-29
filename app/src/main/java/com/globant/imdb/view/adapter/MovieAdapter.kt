package com.globant.imdb.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.globant.imdb.database.MovieInDB
import com.globant.imdb.databinding.MovieSearchItemBinding
import javax.inject.Inject

class MovieAdapter @Inject constructor() :
    ListAdapter<MovieInDB, MovieAdapter.MovieViewHolder>(DiffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            MovieSearchItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)
    }

    class MovieViewHolder(private val binding: MovieSearchItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieInDB) {
            binding.movieData = movie
            binding.executePendingBindings()
        }
    }

    companion object DiffCallBack : DiffUtil.ItemCallback<MovieInDB>() {
        override fun areItemsTheSame(oldItem: MovieInDB, newItem: MovieInDB): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: MovieInDB, newItem: MovieInDB): Boolean {
            return oldItem.id == newItem.id
        }
    }
}