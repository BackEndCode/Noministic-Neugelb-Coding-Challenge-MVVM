package com.noministic.neugelbcodingmvvm.views.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.noministic.neugelbcodingmvvm.model.Constants.MOVIE_ID_PARAM
import com.noministic.neugelbcodingmvvm.R
import com.noministic.neugelbcodingmvvm.model.Movie
import com.noministic.neugelbcodingmvvm.views.MovieDetailActivity
import com.squareup.picasso.Picasso

class MoviesAdapter(var movies: ArrayList<Movie>) :
    RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {
    lateinit var context: Context

    class MoviesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val movieImageView = view.findViewById<ImageView>(R.id.movie_imageView)
        private val movieTitle = view.findViewById<TextView>(R.id.movie_title)
        private val movieRating = view.findViewById<TextView>(R.id.movie_rating)
        private val movieDesc = view.findViewById<TextView>(R.id.movie_desc)
        private val movieReleaseDate = view.findViewById<TextView>(R.id.movie_release_date)
        fun bind(movie: Movie) {
            movieTitle.text = movie.title
            movieRating.text = movie.voteAverage.toString()
            movieDesc.text = movie.overview
            movieReleaseDate.text = movie.releaseDate
            Picasso.get().load("http://image.tmdb.org/t/p/w500/" + movie.posterPath)
                .into(movieImageView)

        }
    }

    fun updateMovies(movies: List<Movie>) {
        this.movies.addAll(movies)
        notifyItemRangeInserted(itemCount, movies.size - 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MoviesViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.movies_recycle_item, parent, false)
    )

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.bind(movies[position])
        holder.itemView.setOnClickListener(View.OnClickListener {
            gotoMovieDetailActivity(it.context, movies[position].id)
        })
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    fun gotoMovieDetailActivity(context: Context, movie_id: Int) {
        val intent = Intent(context, MovieDetailActivity::class.java)
        intent.putExtra(MOVIE_ID_PARAM, Integer.valueOf(movie_id))
        context.startActivity(intent)
    }
}