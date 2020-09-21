package com.marcocaloiaro.movienight.pages

import android.view.KeyEvent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.uiautomator.UiObject
import androidx.test.uiautomator.UiSelector
import com.marcocaloiaro.movienight.R
import com.marcocaloiaro.movienight.base.TestPage

class MainPage : TestPage() {

    override val rootLayoutId: Int
        get() = R.layout.fragment_main

    private val searchIcon : ViewInteraction
        get() = viewInteractionWithId(R.id.search)

    private val searchButton : UiObject? = device?.findObject(UiSelector().resourceId("android:id/search_button"))

    private val pager : ViewInteraction
        get() = viewInteractionWithId(R.id.pager)

    private val watchListButton : ViewInteraction
        get() = viewInteractionWithId(R.id.watchlist_button)

    private val searchEditText : ViewInteraction? =  onView(withId(
        android.content.res.Resources.getSystem().getIdentifier("search_src_text",
        "id", "android")))

    fun performSearchFlow() {
        searchIcon.perform(click())
        searchButton?.click()
    }

    fun typeShow(show: String) {
        searchEditText?.perform(replaceText(show))
        searchEditText?.perform(pressKey(KeyEvent.KEYCODE_ENTER))
    }

    fun clickWatchListButton() {
        watchListButton.perform(click())
    }

    fun swipeViewPager() {
        pager.perform(swipeLeft())
    }
}