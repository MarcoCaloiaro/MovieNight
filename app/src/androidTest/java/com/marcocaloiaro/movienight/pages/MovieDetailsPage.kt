package com.marcocaloiaro.movienight.pages

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import com.marcocaloiaro.movienight.R
import com.marcocaloiaro.movienight.base.TestPage


class MovieDetailsPage : TestPage() {

    override val rootLayoutId: Int
        get() = R.layout.activity_detail

    private val showCover : ViewInteraction
        get() = viewInteractionWithId(R.id.show_cover)

    private val showCardView : ViewInteraction
        get() = viewInteractionWithId(R.id.detail_cardview)

    val watchListIcon : ViewInteraction
        get() = viewInteractionWithId(R.id.watchlist_icon)

    val scrollView : ViewInteraction
        get() = viewInteractionWithId(R.id.scrollView)

    val backButton = onView(withContentDescription("Navigate up"))



    fun checkInformationDisplayed() {
        checkIfDisplayed(showCover)
        checkIfDisplayed(showCardView)
        scrollToPageBottom()
    }

    fun clickWatchListButton() {
        watchListIcon.perform(click())
    }

    fun scrollToPageBottom() {
        scrollView.perform(swipeUp())
    }

    fun clickCloseButton() {
        backButton.perform(click())
    }


}