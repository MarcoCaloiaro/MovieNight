package com.marcocaloiaro.movienight.watchlist.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.marcocaloiaro.movienight.R
import com.marcocaloiaro.movienight.base.intent.MainIntent
import com.marcocaloiaro.movienight.details.model.ShowDetails
import com.marcocaloiaro.movienight.details.ui.DetailActivity
import com.marcocaloiaro.movienight.base.utils.Constants
import com.marcocaloiaro.movienight.watchlist.utils.WatchListClickListener
import com.marcocaloiaro.movienight.watchlist.adapters.WatchListAdapter
import com.marcocaloiaro.movienight.watchlist.model.WatchListState
import com.marcocaloiaro.movienight.watchlist.viewmodel.WatchListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_watch_list.*
import kotlinx.android.synthetic.main.activity_watch_list.app_bar
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class WatchListActivity : AppCompatActivity(), WatchListClickListener {

    private val watchListViewModel: WatchListViewModel by viewModels {
        defaultViewModelProviderFactory
    }

    private lateinit var watchListAdapter: WatchListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_watch_list)
        setupUI()
        setupViewModel()
        observeViewModel()
    }

    private fun setupUI() {
        setSupportActionBar(app_bar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        watchlist_list.layoutManager = GridLayoutManager(this, 2)
        watchlist_list.addItemDecoration(
            DividerItemDecoration(
            watchlist_list.context, (watchlist_list.layoutManager as GridLayoutManager).orientation
        )
        )
        watchListAdapter = WatchListAdapter(
            this,
            arrayListOf(),
            this
        )
        watchlist_list.adapter = watchListAdapter

    }

    private fun setupViewModel() {
        lifecycleScope.launch {
            watchListViewModel.userIntent.send(MainIntent.FetchWatchListMovies)
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            watchListViewModel.state.collect {
                when (it) {
                    is WatchListState.Idle -> {
                    }
                    is WatchListState.Shows -> {
                        updateUI(it.shows)
                    }
                    is WatchListState.Error -> {
                        progressBar.visibility = View.INVISIBLE
                        it.error?.let { errorMessage ->
                            Snackbar.make(findViewById(android.R.id.content), errorMessage, Snackbar.LENGTH_SHORT).show()
                        }
                    }
                    is WatchListState.ShowRemoved -> {
                        progressBar.visibility = View.INVISIBLE
                    }
                    is WatchListState.Loading -> {
                        progressBar.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun updateUI(shows: List<ShowDetails>) {
        progressBar.visibility = View.INVISIBLE
        renderList(shows)
    }

    private fun renderList(shows: List<ShowDetails>) {
        if (shows.isEmpty()) {
            watchlist_empty_view.visibility = View.VISIBLE
            return
        }
        watchlist_list.visibility = View.VISIBLE
        watchListAdapter.addData(shows)
        watchListAdapter.notifyDataSetChanged()
    }

    override fun onShowClicked(show: ShowDetails) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(Constants.SHOW_DETAILS, show)
        startActivity(intent)
    }

    override fun onDeleteIconClicked(shows: List<ShowDetails>, show: ShowDetails) {
        if (listIsEmpty(shows)) {
            watchlist_list.visibility = View.INVISIBLE
            watchlist_empty_view.visibility = View.VISIBLE
        }
        lifecycleScope.launch {
            watchListViewModel.userIntent.send(MainIntent.RemoveShowFromDatabase(show))
        }
    }

    private fun listIsEmpty(shows: List<ShowDetails>): Boolean {
        return shows.size - 1 == 0
    }


}