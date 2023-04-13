package com.globant.imdb.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.globant.imdb.database.Movie
import com.globant.imdb.databinding.MovieSearchItemBinding
import javax.inject.Inject

class MovieAdapter @Inject constructor() :
    ListAdapter<Movie, MovieAdapter.MovieViewHolder>(DiffCallBack) {

    private lateinit var onItemClickListener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            MovieSearchItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onItemClickListener
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)
    }

    interface OnItemClickListener {
        fun onItemClick(movie: Movie?)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        onItemClickListener = listener
    }

    class MovieViewHolder(
        private val binding: MovieSearchItemBinding,
        onItemClickListener: OnItemClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) {

        private var movieClicked: Movie? = null

        init {
            binding.root.setOnClickListener {
                onItemClickListener.onItemClick(movieClicked)
            }
        }

        fun bind(movie: Movie) {
            movieClicked = movie
            binding.movieData = movie
            binding.executePendingBindings()
        }
    }

    companion object DiffCallBack : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }
    }
}