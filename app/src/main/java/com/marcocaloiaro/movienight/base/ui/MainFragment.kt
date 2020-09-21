package com.marcocaloiaro.movienight.base.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.marcocaloiaro.movienight.R
import com.marcocaloiaro.movienight.base.adapters.MainFragmentPagerAdapter
import com.marcocaloiaro.movienight.watchlist.ui.WatchListActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainFragment : Fragment() {

    @Inject lateinit var pagerAdapter: MainFragmentPagerAdapter
    private var viewPager: ViewPager2? = null
    private var tabLayout: TabLayout?  = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setUpListeners()
    }

    private fun setUpListeners() {
        watchlist_button.setOnClickListener {
            val intent = Intent(activity, WatchListActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initViews() {
        viewPager = activity?.findViewById(R.id.pager)
        tabLayout = activity?.findViewById(R.id.tab_layout)
        viewPager?.adapter = pagerAdapter
        viewPager?.let {
            tabLayout?.let { it1 ->
                TabLayoutMediator(it1, it) { tab, position ->
                    tab.text = when (position) {
                        0 -> activity?.resources?.getString(R.string.movies_tab_title)
                        1 -> activity?.resources?.getString(R.string.tv_series_tab_title)
                        else -> ""
                    }
                }.attach()
            }
        }
    }

}