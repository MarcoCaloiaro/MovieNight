package com.marcocaloiaro.movienight.showscreen.series.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.marcocaloiaro.movienight.base.intent.MainIntent
import com.marcocaloiaro.movienight.R
import com.marcocaloiaro.movienight.showscreen.base.utils.ShowClickListener
import com.marcocaloiaro.movienight.showscreen.base.ui.BaseSearchFragment
import com.marcocaloiaro.movienight.showscreen.adapters.ShowAdapter
import com.marcocaloiaro.movienight.showscreen.model.Show
import com.marcocaloiaro.movienight.showscreen.model.ShowsState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_tv_series.*
import kotlinx.android.synthetic.main.fragment_tv_series.progressBar
import kotlinx.android.synthetic.main.shows_presentation_view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class TVSeriesFragment : BaseSearchFragment(),
    ShowClickListener {

    companion object {
        const val SEARCH_TYPE = "series"
        const val PRESENTATION_VIEW_SUBTITLE_VALUE = "tv series"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        return inflater.inflate(R.layout.fragment_tv_series, container, false)
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
        series_list.layoutManager = GridLayoutManager(requireContext(), 2)
        series_list.addItemDecoration(
            DividerItemDecoration(
            series_list.context, (series_list.layoutManager as GridLayoutManager).orientation)
        )
        showAdapter = ShowAdapter(
            requireContext(),
            arrayListOf(),
            this
        )
        series_list.adapter = showAdapter
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            showsViewModel.state.collect {
                when (it) {
                    is ShowsState.Idle -> {
                    }
                    is ShowsState.Loading -> {
                        series_presentation_view.visibility = View.INVISIBLE
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

    private fun updateUI(tvSeries: List<Show>) {
        progressBar.visibility = View.INVISIBLE
        series_presentation_view.visibility = View.GONE
        if (tvSeries.isEmpty()) {
            showEmptyResultsView()
            return
        }
        series_empty_results_view.visibility = View.GONE
        series_list.visibility = View.VISIBLE
        renderList(tvSeries)
    }

    private fun showEmptyResultsView() {
        series_list.visibility = View.GONE
        series_empty_results_view.visibility = View.VISIBLE
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query.isNullOrEmpty()) {
            return false
        }
        lifecycleScope.launch {
            searchView.clearFocus()
            showsViewModel.userIntent.send(
                MainIntent.FetchSeries(
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
        searchView.queryHint = getString(R.string.query_hint_tv_series)
    }


}