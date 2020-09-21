package com.marcocaloiaro.movienight.details.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.marcocaloiaro.movienight.R
import com.marcocaloiaro.movienight.base.intent.MainIntent
import com.marcocaloiaro.movienight.details.model.ShowDetails
import com.marcocaloiaro.movienight.details.model.ShowDetailsState
import com.marcocaloiaro.movienight.details.viemodel.ShowDetailsViewModel
import com.marcocaloiaro.movienight.showscreen.model.Show
import com.marcocaloiaro.movienight.base.utils.Constants
import com.marcocaloiaro.movienight.base.utils.ImageUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private val showDetailsViewModel: ShowDetailsViewModel by viewModels {
        defaultViewModelProviderFactory
    }

    private val omdbKey: String by lazy {
        resources.getString(R.string.omdb_api_key)
    }

    companion object {
        const val PLOT_TYPE = "full"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setUpUI()
        // since it will be launched by 2 fragments, only one of these 2 values won't be null.
        val show: Show? = intent.extras?.get(Constants.SHOW) as Show?
        val showDetails : ShowDetails? = intent.extras?.get(Constants.SHOW_DETAILS) as ShowDetails?
        showDetails?.let {
            updateUI(showDetails)
        }
        show?.let {
            supportActionBar?.title = it.title
            setUpViewModel(it.imdbID)
        }
        observeViewModel()
    }

    private fun setUpViewModel(movieId: String) {
        lifecycleScope.launch {
            showDetailsViewModel.userIntent.send(
                MainIntent.FetchShowDetails(
                    omdbKey,
                    movieId,
                    PLOT_TYPE
                )
            )
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            showDetailsViewModel.state.collect {
                when (it) {
                    is ShowDetailsState.Idle -> {
                    }
                    is ShowDetailsState.ShowDetails -> {
                        updateUI(it.showDetails)
                        setupListeners(it.showDetails)
                    }
                    is ShowDetailsState.Loading -> {
                        progressBar.visibility = View.VISIBLE
                    }
                    is ShowDetailsState.Error -> {
                        progressBar.visibility = View.INVISIBLE
                        it.error?.let { errorMessage ->
                            showMessage(errorMessage)
                            return@collect
                        }
                        showMessage(getString(R.string.show_details_not_found_message))
                    }
                    is ShowDetailsState.ShowStored -> {
                        progressBar.visibility = View.INVISIBLE
                        showMessage(getString(R.string.show_stored_success_message))
                    }
                }
            }
        }
    }

    private fun showMessage(message: String) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show()
    }

    private fun setupListeners(showDetails: ShowDetails) {
        watchlist_icon.setOnClickListener {
            lifecycleScope.launch {
                showDetailsViewModel.userIntent.send(MainIntent.StoreShowInDatabase(showDetails))
            }
        }
    }

    private fun updateUI(showDetails: ShowDetails) {
        progressBar.visibility = View.INVISIBLE
        watchlist_icon.visibility = View.VISIBLE
        add_to_watchlist_title.visibility = View.VISIBLE
        show_title.text = showDetails.title
        show_plot.text = String.format(getString(R.string.show_plot_text, showDetails.plot))
        show_release_year.text = String.format(getString(R.string.show_release_year_text, showDetails.released))
        show_actors.text = String.format(getString(R.string.show_actors_text, showDetails.actors))
        show_director.text = String.format(getString(R.string.show_director_text, showDetails.director))
        show_genre.text = String.format(getString(R.string.show_genre_text, showDetails.genre))
        rating_bar.rating = showDetails.imdbRating.toFloat()
        if (ImageUtils.isShowPosterAvailable(showDetails.poster)) {
            Glide.with(this).load(showDetails.poster).into(show_cover)
            return
        }
        Glide.with(this).load(
            ContextCompat.getDrawable(this, R.drawable.not_found)).into(show_cover)
    }

    private fun setUpUI() {
        setSupportActionBar(app_bar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }


}