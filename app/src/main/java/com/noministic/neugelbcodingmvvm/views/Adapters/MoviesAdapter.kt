package com.noministic.neugelbcodingmvvm.views.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.noministic.neugelbcodingmvvm.R
import com.noministic.neugelbcodingmvvm.others.Constants.IMDB_PRE_IMAGE_PATH
import com.noministic.neugelbcodingmvvm.others.Constants.MOVIE_ID_PARAM
import com.noministic.neugelbcodingmvvm.model.Movie
import com.noministic.neugelbcodingmvvm.model.MovieDetailModel
import com.noministic.neugelbcodingmvvm.views.MovieDetailActivity
import com.squareup.picasso.Picasso

class MoviesAdapter(var movies: ArrayList<MovieDetailModel>) :
    RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {

    class MoviesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val movieImageView = view.findViewById<ImageView>(R.id.movie_imageView)
        private val movieTitle = view.findViewById<TextView>(R.id.movie_title)
        private val movieRating = view.findViewById<TextView>(R.id.movie_rating)
        private val movieDesc = view.findViewById<TextView>(R.id.movie_desc)
        private val movieReleaseDate = view.findViewById<TextView>(R.id.movie_release_date)
        fun bind(movie: MovieDetailModel) {
            movieTitle.text = movie.title
            movieRating.text = movie.voteAverage.toString()
            movieDesc.text = movie.overview
            movieReleaseDate.text = movie.releaseDate
            Picasso.get().load(IMDB_PRE_IMAGE_PATH + movie.posterPath)
                .into(movieImageView)

        }
    }

    fun updateMovies(movies: List<MovieDetailModel>) {
        this.movies.addAll(movies)
        notifyItemRangeInserted(itemCount, movies.size - 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MoviesViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.movies_recycle_item, parent, false)
    )

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.bind(movies[position])
        holder.itemView.setOnClickListener {
            gotoMovieDetailActivity(it.context, movies[position].id)
        }
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    fun gotoMovieDetailActivity(context: Context, movie_id: Int) {
        val intent = Intent(context, MovieDetailActivity::class.java)
        intent.apply { putExtra(MOVIE_ID_PARAM, Integer.valueOf(movie_id)) }
        context.startActivity(intent)
    }
}