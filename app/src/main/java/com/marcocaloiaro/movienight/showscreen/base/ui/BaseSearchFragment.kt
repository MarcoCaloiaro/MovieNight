package com.marcocaloiaro.movienight.showscreen.base.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.marcocaloiaro.movienight.*
import com.marcocaloiaro.movienight.details.ui.DetailActivity
import com.marcocaloiaro.movienight.showscreen.data.ShowAdapter
import com.marcocaloiaro.movienight.showscreen.base.viewmodel.ShowsViewModel
import com.marcocaloiaro.movienight.showscreen.model.Show
import com.marcocaloiaro.movienight.base.utils.Constants
import com.marcocaloiaro.movienight.showscreen.base.utils.ShowClickListener
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject


@ExperimentalCoroutinesApi
abstract class BaseSearchFragment : Fragment(), SearchView.OnQueryTextListener,
    ShowClickListener {

    @Inject lateinit var searchManager: SearchManager
    lateinit var showAdapter: ShowAdapter
    lateinit var searchView: SearchView

    val showsViewModel: ShowsViewModel by viewModels {
        defaultViewModelProviderFactory
    }

    val omdbKey: String by lazy {
        resources.getString(R.string.omdb_api_key)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.invalidateOptionsMenu()
    }


    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        searchView = (menu.findItem(R.id.search).actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
        }
        updateHint()
        searchView.setOnQueryTextListener(this)
        searchView.setOnQueryTextFocusChangeListener { view: View, b: Boolean ->
            if(!b) {
                hideKeyboard(view)
            }
        }
        super.onPrepareOptionsMenu(menu)
    }

    private fun hideKeyboard(view: View) {
        val inputManager: InputMethodManager? = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        inputManager?.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.options_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    fun renderList(shows: List<Show>) {
        showAdapter.addData(shows)
        showAdapter.notifyDataSetChanged()
    }

    override fun onShowClicked(show: Show) {
        val intent = Intent(activity, DetailActivity::class.java)
        intent.putExtra(Constants.SHOW, show)
        startActivity(intent)
    }

    abstract override fun onQueryTextSubmit(query: String?) : Boolean

    abstract override fun onQueryTextChange(p0: String?): Boolean

    abstract fun updateHint()

}
