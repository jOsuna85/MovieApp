package com.josuna85.movieapp.ui.ui.movie

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import com.josuna85.movieapp.R
import com.josuna85.movieapp.databinding.FragmentMovieBinding
import com.josuna85.movieapp.ui.core.Resource
import com.josuna85.movieapp.ui.data.local.AppDatabase
import com.josuna85.movieapp.ui.data.local.LocalMovieDataSource
import com.josuna85.movieapp.ui.data.model.Movie
import com.josuna85.movieapp.ui.data.remote.RemoteMovieDataSource
import com.josuna85.movieapp.ui.presentation.MovieViewModelFactory
import com.josuna85.movieapp.ui.presentation.MoviewViewModel
import com.josuna85.movieapp.ui.repository.MovieRepositoryImpl
import com.josuna85.movieapp.ui.repository.RetrofiClient
import com.josuna85.movieapp.ui.ui.movie.adapters.MovieAdapter
import com.josuna85.movieapp.ui.ui.movie.adapters.concat.PopularConcatAdapter
import com.josuna85.movieapp.ui.ui.movie.adapters.concat.TopRatedConcatAdapter
import com.josuna85.movieapp.ui.ui.movie.adapters.concat.UpcomingConcatAdapter



class MovieFragment : Fragment(R.layout.fragment_movie,), MovieAdapter.OnMovieClickListener {

    private lateinit var binding: FragmentMovieBinding
    private val viewModel by viewModels<MoviewViewModel> {
        MovieViewModelFactory(
            MovieRepositoryImpl(
                RemoteMovieDataSource(RetrofiClient.webservice),
                LocalMovieDataSource(AppDatabase.getDatabase(requireContext()).movieDao())
            )
        )
    }

    private lateinit var concatAdapter: ConcatAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieBinding.bind(view)

        concatAdapter = ConcatAdapter()

        viewModel.fetchMainScreenMovies().observe(viewLifecycleOwner, Observer { result->
            when (result) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    concatAdapter.apply {
                        addAdapter(0,UpcomingConcatAdapter(MovieAdapter(result.data.first.results, this@MovieFragment)))
                        addAdapter(1,TopRatedConcatAdapter(MovieAdapter(result.data.second.results, this@MovieFragment)))
                        addAdapter(2,PopularConcatAdapter(MovieAdapter(result.data.third.results, this@MovieFragment)))
                    }

                    binding.rvMovies.adapter = concatAdapter

                }
                is Resource.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    Log.d("Error", "${result.exception}")
                }
            }
        })
    }

    override fun onMovieClick(movie: Movie) {
        val action = MovieFragmentDirections.actionMovieFragmentToMovieDetailFragment(
            movie.poster_path,
            movie.backdrop_path,
            movie.vote_average.toFloat(),
            movie.vote_count,
            movie.overview,
            movie.original_language,
            movie.release_date,
            movie.title
        )
        findNavController().navigate(action)
    }
}


