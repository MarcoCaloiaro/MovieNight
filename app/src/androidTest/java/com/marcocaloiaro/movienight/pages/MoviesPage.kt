package com.marcocaloiaro.movienight.pages

import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import com.marcocaloiaro.movienight.R
import com.marcocaloiaro.movienight.base.TestPage
import com.marcocaloiaro.movienight.showscreen.data.ShowViewHolder

class MoviesPage : TestPage() {

    override val rootLayoutId: Int
        get() = R.layout.fragment_movies

    private val moviesList : ViewInteraction
        get() = viewInteractionWithId(R.id.movies_list)

    fun clickShow() {
        moviesList.perform(RecyclerViewActions.actionOnItemAtPosition<ShowViewHolder>(0, click()));
    }


}