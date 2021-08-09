package ru.nukolay.stupnikov.animelist.ui

import android.content.Intent
import androidx.test.InstrumentationRegistry
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.nukolay.stupnikov.animelist.AnimeApp
import ru.nukolay.stupnikov.animelist.R
import ru.nukolay.stupnikov.animelist.data.api.response.anime.Titles
import ru.nukolay.stupnikov.animelist.di.component.AppComponent
import ru.nukolay.stupnikov.animelist.di.component.DaggerAndroidTestAppComponent
import ru.nukolay.stupnikov.animelist.ui.detail.DetailActivity
import ru.nukolay.stupnikov.animelist.ui.detail.DetailActivity.Companion.ID_ANIME
import ru.nukolay.stupnikov.animelist.ui.detail.DetailActivity.Companion.TITLES

@RunWith(AndroidJUnit4ClassRunner::class)
class DetailActivityTest {

    companion object {
        const val DESCRIPTION = "In the year 2071, humanity has colonized several of the planets and moons of the solar system leaving the now uninhabitable surface of planet Earth behind. The Inter Solar System Police attempts to keep peace in the galaxy, aided in part by outlaw bounty hunters, referred to as \\\"Cowboys\\\". The ragtag team aboard the spaceship Bebop are two such individuals.\\nMellow and carefree Spike Spiegel is balanced by his boisterous, pragmatic partner Jet Black as the pair makes a living chasing bounties and collecting rewards. Thrown off course by the addition of new members that they meet in their travels—Ein, a genetically engineered, highly intelligent Welsh Corgi; femme fatale Faye Valentine, an enigmatic trickster with memory loss; and the strange computer whiz kid Edward Wong—the crew embarks on thrilling adventures that unravel each member's dark and mysterious past little by little. \\nWell-balanced with high density action and light-hearted comedy, Cowboy Bebop is a space Western classic and an homage to the smooth and improvised music it is named after.\\n\\n(Source: MAL Rewrite)"
        const val ANIME_ID = 1
    }

    @get:Rule
    val testRule: ActivityTestRule<DetailActivity> = ActivityTestRule(DetailActivity::class.java, false, false)

    @Before
    fun setUp() {
        val app = InstrumentationRegistry.getTargetContext().applicationContext as AnimeApp
        val appComponent: AppComponent = DaggerAndroidTestAppComponent.create()
        app.appComponent = appComponent
        val intent = Intent()
        intent.putExtra(TITLES, Titles("Cowboy Bebop", "Cowboy Bebop", "カウボーイビバップ"))
        intent.putExtra(ID_ANIME, ANIME_ID)
        testRule.launchActivity(intent)
    }

    @Test
    fun getDetails() {
        onView(withId(R.id.toolbar)).check(matches(hasDescendant(withText("Cowboy Bebop"))))
        onView(withId(R.id.description)).check(matches(withText(DESCRIPTION)))
    }
}