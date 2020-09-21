package com.marcocaloiaro.movienight.showscreen.movies.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.marcocaloiaro.movienight.*
import com.marcocaloiaro.movienight.showscreen.base.ui.BaseSearchFragment
import com.marcocaloiaro.movienight.base.intent.MainIntent
import com.marcocaloiaro.movienight.showscreen.data.ShowAdapter
import com.marcocaloiaro.movienight.showscreen.model.Show
import com.marcocaloiaro.movienight.showscreen.model.ShowsState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_movies.*
import kotlinx.android.synthetic.main.shows_presentation_view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MoviesFragment : BaseSearchFragment() {

    companion object {
        const val SEARCH_TYPE = "movie"
        const val PRESENTATION_VIEW_SUBTITLE_VALUE = "movie"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        observeViewModel()
    }

    private fun setupUI() {
        shows_presentation_subtitle.text = String.format(getString(R.string.shows_presentation_subtitle),
            PRESENTATION_VIEW_SUBTITLE_VALUE
        )
        movies_list.layoutManager = GridLayoutManager(requireContext(), 2)
        movies_list.addItemDecoration(DividerItemDecoration(
                movies_list.context, (movies_list.layoutManager as GridLayoutManager).orientation
            ))
        showAdapter = ShowAdapter(
            requireContext(),
            arrayListOf(),
            this
        )
        movies_list.adapter = showAdapter
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            showsViewModel.state.collect {
                when (it) {
                    is ShowsState.Idle -> {
                    }
                    is ShowsState.Loading -> {
                        movies_presentation_view.visibility = View.INVISIBLE
                        progressBar.visibility = View.VISIBLE
                    }
                    is ShowsState.Shows -> {
                        updateUI(it.movies)
                    }
                    is ShowsState.Error -> {
                        progressBar.visibility = View.INVISIBLE
                        it.error?.let { errorMessage ->
                            Snackbar.make(requireView(), errorMessage, Snackbar.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun updateUI(movies: List<Show>) {
        progressBar.visibility = View.INVISIBLE
        movies_presentation_view.visibility = View.GONE
        if (movies.isEmpty()) {
            showEmptyResultsView()
            return
        }
        movies_empty_results_view.visibility = View.GONE
        movies_list.visibility = View.VISIBLE
        renderList(movies)
    }

    private fun showEmptyResultsView() {
        movies_list.visibility = View.GONE
        movies_empty_results_view.visibility = View.VISIBLE
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query.isNullOrEmpty()) {
            return false
        }
        lifecycleScope.launch {
            searchView.clearFocus()
            showsViewModel.userIntent.send(
                MainIntent.FetchMovies(
                    omdbKey,
                    SEARCH_TYPE,
                    query
                )
            )
        }
        return true
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        return false
    }

    override fun updateHint() {
        searchView.queryHint = getString(R.string.query_hint_movies)
    }
}