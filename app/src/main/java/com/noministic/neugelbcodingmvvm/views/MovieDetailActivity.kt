package com.noministic.neugelbcodingmvvm.views

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.noministic.neugelbcodingmvvm.model.Constants.IMDB_PRE_IMAGE_PATH
import com.noministic.neugelbcodingmvvm.model.Constants.MOVIE_ID_PARAM
import com.noministic.neugelbcodingmvvm.R
import com.noministic.neugelbcodingmvvm.databinding.ActivityMovieDetailBinding
import com.noministic.neugelbcodingmvvm.model.ProductionCountry
import com.noministic.neugelbcodingmvvm.viewmodel.MovieDetailViewModel
import com.squareup.picasso.Picasso

class MovieDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieDetailBinding
    lateinit var viewModel: MovieDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val actionBar = supportActionBar!!
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true)
        viewModel = ViewModelProvider(this)[MovieDetailViewModel::class.java]
        if (!intent.extras?.isEmpty!!) {
            val movie_id = intent.extras?.getInt(MOVIE_ID_PARAM)
            movie_id?.let { viewModel.getMovie(it) }
        }
        observeViewModel()
    }

    fun observeViewModel() {
        viewModel.movie.observe(this, { movie ->
            binding.textViewRatingCount.text =
                getString(R.string.rating).plus(movie.voteAverage.toString()).plus(" ")
                    .plus(getString(R.string.ratingCount)).plus(movie.voteCount)
            binding.textviewTitle.text = movie.title
            binding.textviewRevenue.text =
                getString(R.string.revenue).plus(movie.revenue.toString())
            binding.textviewReleaseDate.text =
                getString(R.string.release_date).plus(movie.releaseDate)
            binding.textviewStatus.text = getString(R.string.status).plus(movie.status)
            binding.textviewDesc.text = movie.overview
            Picasso.get().load(IMDB_PRE_IMAGE_PATH + movie.posterPath)
                .into(binding.imageviewMovie)
            binding.textviewPCountries.text =
                getString(R.string.product_countries).plus(getProductionCountriesString(movie.productionCountries))

        })
        viewModel.loadingError.observe(this, Observer { isError ->
            isError.let { binding.errorTextview.visibility = if (it) View.VISIBLE else View.GONE }
        })
        viewModel.loading.observe(this, Observer { isLoading ->
            isLoading.let { binding.progressbar.visibility = if (it) View.VISIBLE else View.GONE }
        })
    }

    private fun getProductionCountriesString(countries: List<ProductionCountry>): String {
        val sb = StringBuilder()
        for (s in countries) {
            sb.append(s.name).append(",")
        }
        return sb.toString()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}