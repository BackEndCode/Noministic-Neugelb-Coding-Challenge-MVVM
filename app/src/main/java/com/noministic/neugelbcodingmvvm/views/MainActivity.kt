package com.noministic.neugelbcodingmvvm.views

import android.app.SearchManager
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.noministic.neugelbcodingmvvm.R
import com.noministic.neugelbcodingmvvm.databinding.ActivityMainBinding
import com.noministic.neugelbcodingmvvm.model.Constants
import com.noministic.neugelbcodingmvvm.viewmodel.MoviesListViewModel
import com.noministic.neugelbcodingmvvm.views.Adapters.MoviesAdapter
import com.noministic.neugelbcodingmvvm.views.Adapters.SearchCursorAdaptor

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MoviesListViewModel
    private val moviesAdapter = MoviesAdapter(arrayListOf())
    var suggestionAdapter: SearchCursorAdaptor? = null
    private lateinit var binding: ActivityMainBinding
    var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel = ViewModelProvider(this)[MoviesListViewModel::class.java]
        viewModel.refresh()


        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = moviesAdapter
            val scrollListener: EndlessRecyclerViewScrollListener =
                object : EndlessRecyclerViewScrollListener(layoutManager) {
                    override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                        //checks if current page is less the total page count that we can fetch
                        viewModel.refresh()

                    }
                }
            addOnScrollListener(scrollListener)
        }
        observeViewModel()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)
        val mSearchView = menu!!.findItem(R.id.search).actionView as SearchView
        suggestionAdapter = SearchCursorAdaptor(
            this,
            R.layout.cursor_layout_item,
            null,
            arrayOf(SearchManager.SUGGEST_COLUMN_TEXT_1),
            intArrayOf(R.id.textView_search_title)
        )
        mSearchView.apply { suggestionsAdapter = suggestionAdapter }
        mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                if (query.isNotEmpty())
                    viewModel.searchMovie(query)
                return true
            }
        })
        mSearchView.setOnSuggestionListener(object : SearchView.OnSuggestionListener {
            override fun onSuggestionSelect(position: Int): Boolean {
                return false
            }

            override fun onSuggestionClick(position: Int): Boolean {
                mSearchView.setQuery(viewModel.suggestions.value?.get(position), false)
                mSearchView.clearFocus()
                val cursor = suggestionAdapter!!.getItem(position) as Cursor
                val movie_id = cursor.getString(0)
                //Log.e("NOMI", cursor.getString(0));
                gotoMovieDetailActivity(movie_id.toInt())
                return true
            }
        })
        return true
    }

    fun gotoMovieDetailActivity(movie_id: Int) {
        val intent = Intent(this, MovieDetailActivity::class.java)
        intent.putExtra(Constants.MOVIE_ID_PARAM, Integer.valueOf(movie_id))
        startActivity(intent)
    }

    fun observeViewModel() {
        viewModel.movies.observe(this, {
            it.let { moviesAdapter.updateMovies(it) }
            isLoading = false
        })
        viewModel.loadingError.observe(this, Observer { isError ->
            isError.let { binding.errorTextview.visibility = if (it) View.VISIBLE else View.GONE }
        })
        viewModel.loading.observe(this, Observer { isLoading ->
            isLoading.let { binding.progressbar.visibility = if (it) View.VISIBLE else View.GONE }
        })
        viewModel.suggestions.observe(this, {
            suggestionAdapter?.swapCursor(viewModel.cursor)
        })

    }
}