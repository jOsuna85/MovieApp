package com.josuna85.movieapp.ui.ui.movie.adapters.concat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.josuna85.movieapp.databinding.TopRatetMoviesRowBinding
import com.josuna85.movieapp.databinding.UpcomingMovieRowBinding
import com.josuna85.movieapp.ui.core.BaseConcatHolder
import com.josuna85.movieapp.ui.ui.movie.adapters.MovieAdapter

class TopRatedConcatAdapter (private val moviesAdapter: MovieAdapter): RecyclerView.Adapter<BaseConcatHolder<*>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseConcatHolder<*> {
        val itemBinding = TopRatetMoviesRowBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return ConcatViewHolder(itemBinding)
    }

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(holder: BaseConcatHolder<*>, position: Int) {
        when(holder){
            is ConcatViewHolder -> holder.bind(moviesAdapter)
        }
    }

    private inner class ConcatViewHolder(val binding: TopRatetMoviesRowBinding): BaseConcatHolder<MovieAdapter>(binding.root){
        override fun bind(adapter: MovieAdapter) {
            binding.rvTopRatedMovies.adapter = adapter
        }

    }
}