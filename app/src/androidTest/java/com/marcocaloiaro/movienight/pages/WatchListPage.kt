package com.marcocaloiaro.movienight.pages

import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import com.marcocaloiaro.movienight.R
import com.marcocaloiaro.movienight.base.TestPage
import com.marcocaloiaro.movienight.utils.CustomRecyclerViewAction
import com.marcocaloiaro.movienight.watchlist.data.WatchListViewHolder

class WatchListPage : TestPage() {

    override val rootLayoutId: Int
        get() = R.layout.activity_watch_list

    private val showsWatchList : ViewInteraction
        get() = viewInteractionWithId(R.id.watchlist_list)

    private val backButton = Espresso.onView(ViewMatchers.withContentDescription("Navigate up"))

    fun removeMovie() {
        showsWatchList.perform(RecyclerViewActions.actionOnItemAtPosition<WatchListViewHolder>(0, CustomRecyclerViewAction.clickChildViewWithId(R.id.delete_icon)))
    }

    fun clickCloseButton() {
        backButton.perform(ViewActions.click())
    }

}