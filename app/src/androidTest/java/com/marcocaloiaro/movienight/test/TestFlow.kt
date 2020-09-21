package com.marcocaloiaro.movienight.test

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import com.marcocaloiaro.movienight.base.TestBaseUI
import com.marcocaloiaro.movienight.base.ui.MainActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test


@ExperimentalCoroutinesApi
class TestFlow : TestBaseUI() {

    companion object {
        const val SHOW_NAME = "Inception"
    }

    @Test
    fun testFlow() {
        val intent = Intent(ApplicationProvider.getApplicationContext(), MainActivity::class.java)
            .putExtra("title", "Testing rules!")
        scenario = ActivityScenario.launch(intent)
        waitForAction(5000)
        mainPage.performSearchFlow()
        mainPage.typeShow(SHOW_NAME)
        waitForAction(5000)
        moviesPage.clickShow()
        waitForAction(5000)
        movieDetailsPage.checkInformationDisplayed()
        waitForAction(5000)
        movieDetailsPage.clickWatchListButton()
        waitForAction()
        movieDetailsPage.clickCloseButton()
        waitForAction()
        mainPage.clickWatchListButton()
        waitForAction(3000)
        watchListPage.removeMovie()
        waitForAction()
        watchListPage.clickCloseButton()
        mainPage.swipeViewPager()
        waitForAction()
    }
}