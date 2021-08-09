package ru.nukolay.stupnikov.animelist.ui

import android.content.Intent
import androidx.test.InstrumentationRegistry
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.nukolay.stupnikov.animelist.AnimeApp
import ru.nukolay.stupnikov.animelist.R
import ru.nukolay.stupnikov.animelist.data.api.response.category.CategoryApi
import ru.nukolay.stupnikov.animelist.di.component.AppComponent
import ru.nukolay.stupnikov.animelist.di.component.DaggerAndroidTestAppComponent
import ru.nukolay.stupnikov.animelist.ui.filter.Filter
import ru.nukolay.stupnikov.animelist.ui.filter.FilterActivity
import ru.nukolay.stupnikov.animelist.ui.filter.FilterActivity.Companion.FILTER

@RunWith(AndroidJUnit4ClassRunner::class)
class FilterActivityTest {

    @get:Rule
    val testRule: ActivityTestRule<FilterActivity> = ActivityTestRule(FilterActivity::class.java, false, false)

    private fun withItemId(itemNameMatcher: Matcher<Int?>): Matcher<Any?> {
        return object : BoundedMatcher<Any?, CategoryApi>(CategoryApi::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("with item id: ")
                itemNameMatcher.describeTo(description)
            }

            override fun matchesSafely(item: CategoryApi): Boolean {
                return itemNameMatcher.matches(item.id)
            }
        }
    }

    @Before
    fun setUp() {
        val app = InstrumentationRegistry.getTargetContext().applicationContext as AnimeApp
        val appComponent: AppComponent = DaggerAndroidTestAppComponent.create()
        app.appComponent = appComponent
        val intent = Intent()
        intent.putExtra(FILTER, Filter(FilterActivity.seasons, "1984", "drama", listOf("R")))
        testRule.launchActivity(intent)
    }

    @Test
    fun test() {
        Espresso.onView(withId(R.id.etYear)).check(matches(ViewMatchers.withText("1984")))
        Espresso.onView(withId(R.id.categorySpinner)).check(matches(ViewMatchers.withSpinnerText(CoreMatchers.containsString("Drama"))))
        Espresso.onView(withId(R.id.categorySpinner)).perform(ViewActions.click())
        Espresso.onData(withItemId(`is`(4))).perform(ViewActions.click())
        Espresso.onView(withId(R.id.categorySpinner)).check(matches(ViewMatchers.withSpinnerText(CoreMatchers.containsString("Pirates"))))
    }
}