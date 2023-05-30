package com.example.realfinalgithubproject.ui

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.realfinalgithubproject.R
import com.example.realfinalgithubproject.ui.main.MainActivity
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class MainActivityTest {

    @Before
    fun setup(){
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun searchUser() {
        onView(withId(R.id.et_search)).perform(ViewActions.typeText("vsh2607"), ViewActions.closeSoftKeyboard())
        onView(withId(R.id.btn_search)).perform(ViewActions.click())
        onView(withId(R.id.pb_progress_bar)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Thread.sleep(5000)
        onView(withId(R.id.rv_user)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }

}