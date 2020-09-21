package com.marcocaloiaro.movienight.base

import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice

abstract class TestPage {

    abstract val rootLayoutId: Int

    val device: UiDevice? = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    fun checkIfDisplayed(viewInteraction: ViewInteraction) : ViewInteraction? {
        return viewInteraction.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    fun viewInteractionWithId(id: Int): ViewInteraction {
        return Espresso.onView(ViewMatchers.withId(id))
    }

}