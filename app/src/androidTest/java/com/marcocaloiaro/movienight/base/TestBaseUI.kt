package com.marcocaloiaro.movienight.base

import android.graphics.Point
import android.os.RemoteException
import android.os.SystemClock
import android.util.Log
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.intent.Intents
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import com.marcocaloiaro.movienight.base.ui.MainActivity
import com.marcocaloiaro.movienight.pages.MainPage
import com.marcocaloiaro.movienight.pages.MovieDetailsPage
import com.marcocaloiaro.movienight.pages.MoviesPage
import com.marcocaloiaro.movienight.pages.WatchListPage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before

@ExperimentalCoroutinesApi
@Suppress("unused")
open class TestBaseUI {

    @Suppress("HasPlatformType")
    val targetContext
        get() = InstrumentationRegistry.getInstrumentation().targetContext

    lateinit var scenario: ActivityScenario<MainActivity>

    val mainPage = MainPage()
    val moviesPage = MoviesPage()
    val movieDetailsPage = MovieDetailsPage()
    val watchListPage = WatchListPage()

    val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    val selector = UiSelector()

    @Before
    fun initIntents() {
        initDevice()
        Intents.init()
    }

    @After
    fun releaseIntents() {
        Intents.release()
    }

    fun waitForAction(milliseconds: Long = 2000L) {
        SystemClock.sleep(milliseconds)
    }

    fun initDevice() {
        val coordinates: Array<Point?> = arrayOfNulls<Point>(4)
        coordinates[0] = Point(248, 1520)
        coordinates[1] = Point(248, 929)
        coordinates[2] = Point(796, 1520)
        coordinates[3] = Point(796, 929)
        try {
            if (!device.isScreenOn) {
                device.wakeUp()
                device.swipe(coordinates, 10)
            }
        } catch (e: RemoteException) {
            Log.e("Error in device init", e.message.toString())
        }
    }
}