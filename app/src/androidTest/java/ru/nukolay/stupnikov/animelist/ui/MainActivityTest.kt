package ru.nukolay.stupnikov.animelist.ui

import androidx.test.InstrumentationRegistry
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.nukolay.stupnikov.animelist.AnimeApp
import ru.nukolay.stupnikov.animelist.R
import ru.nukolay.stupnikov.animelist.di.component.AppComponent
import ru.nukolay.stupnikov.animelist.di.component.DaggerAndroidTestAppComponent
import ru.nukolay.stupnikov.animelist.ui.main.MainActivity
import ru.nukolay.stupnikov.animelist.util.RvUtils

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {

    @get:Rule
    val testRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java, false, false)

    companion object {
        const val SEARCH_INPUT = "Z"
    }

    @Before
    fun setUp() {
        val app = InstrumentationRegistry.getTargetContext().applicationContext as AnimeApp
        val appComponent: AppComponent = DaggerAndroidTestAppComponent.create()
        app.appComponent = appComponent
        testRule.launchActivity(null)
    }

    @Test
    fun listTest() {
        onView(RvUtils.withRecyclerView(R.id.rvAnimeList).atPosition(0, R.id.name)).check(ViewAssertions.matches(
            ViewMatchers.withText("Death note")
        ))
        onView(RvUtils.withRecyclerView(R.id.rvAnimeList).atPosition(2, R.id.name)).check(ViewAssertions.matches(
            ViewMatchers.withText("Cowboy Bebop")
        ))
    }

    @Test
    fun emptyText() {
        onView(withId(R.id.emptyText))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
        onView(withId(R.id.searchForm)).perform(ViewActions.typeText(SEARCH_INPUT), ViewActions.closeSoftKeyboard())
        onView(withId(R.id.emptyText))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
    }
}