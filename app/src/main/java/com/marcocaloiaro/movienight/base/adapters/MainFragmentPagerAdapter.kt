package com.marcocaloiaro.movienight.base.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.marcocaloiaro.movienight.showscreen.movies.ui.MoviesFragment
import com.marcocaloiaro.movienight.showscreen.series.ui.TVSeriesFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class MainFragmentPagerAdapter @Inject constructor(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        if (position == 0) {
            return MoviesFragment()
        }
        return TVSeriesFragment()
    }


}