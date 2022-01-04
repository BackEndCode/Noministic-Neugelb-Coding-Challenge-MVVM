package com.noministic.neugelbcodingmvvm.views

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.noministic.neugelbcodingmvvm.R
import com.noministic.neugelbcodingmvvm.databinding.ActivityMovieDetailBinding
import com.noministic.neugelbcodingmvvm.others.Constants.IMDB_PRE_IMAGE_PATH
import com.noministic.neugelbcodingmvvm.others.Constants.MOVIE_ID_PARAM
import com.noministic.neugelbcodingmvvm.model.ProductionCountry
import com.noministic.neugelbcodingmvvm.viewmodel.MovieDetailViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieDetailBinding
    private val viewModel: MovieDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        // showing the back button in action bar
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        if (!intent.extras?.isEmpty!!) {
            val movie_id = intent.extras?.getInt(MOVIE_ID_PARAM)
            movie_id?.let { viewModel.getMovie(it) }
        }
        binding.addToFavorite.setOnClickListener {
            viewModel.addMovieToFavorite()
        }
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.movie.observe(this, { movie ->
            binding.apply {
                textViewRatingCount.text =
                    getString(R.string.rating).plus(movie.voteAverage.toString()).plus(" ")
                        .plus(getString(R.string.ratingCount)).plus(movie.voteCount)
                textviewTitle.text = movie.title
                textviewRevenue.text =
                    getString(R.string.revenue).plus(movie.revenue.toString())
                textviewReleaseDate.text =
                    getString(R.string.release_date).plus(movie.releaseDate)
                textviewStatus.text = getString(R.string.status).plus(movie.status)
                textviewDesc.text = movie.overview
                Picasso.get().load(IMDB_PRE_IMAGE_PATH + movie.posterPath)
                    .into(imageviewMovie)
                /*textviewPCountries.text =
                    getString(R.string.product_countries).plus(getProductionCountriesString(movie.productionCountries))
*/
            }
        })
        viewModel.loadingError.observe(this, Observer { isError ->
            isError.let {
                if (it) {
                    binding.errorTextview.visibility = View.VISIBLE
                    binding.imageLayout.visibility = View.GONE
                    binding.detailsLayout.visibility = View.GONE
                    binding.progressbar.visibility = View.GONE
                } else {
                    binding.errorTextview.visibility = View.GONE
                    binding.imageLayout.visibility = View.VISIBLE
                    binding.detailsLayout.visibility = View.VISIBLE
                    binding.progressbar.visibility = View.GONE
                }
            }
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
        //Log.e("NOMI", item.itemId.toString().plus(" home ID:").plus(R.id.home))
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}