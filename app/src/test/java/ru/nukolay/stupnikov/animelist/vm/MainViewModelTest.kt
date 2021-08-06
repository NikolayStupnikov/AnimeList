package ru.nukolay.stupnikov.animelist.vm

import androidx.lifecycle.MutableLiveData
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.never
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import ru.nukolay.stupnikov.animelist.TestAnimeApp
import ru.nukolay.stupnikov.animelist.data.api.response.anime.AnimeApi
import ru.nukolay.stupnikov.animelist.di.component.DaggerViewModelComponent
import ru.nukolay.stupnikov.animelist.rule.RxImmediateSchedulerRule
import ru.nukolay.stupnikov.animelist.ui.filter.Filter
import ru.nukolay.stupnikov.animelist.ui.main.MainViewModel
import javax.inject.Inject

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    val schedulers: RxImmediateSchedulerRule = RxImmediateSchedulerRule()

    @Inject
    lateinit var mainViewModel: MainViewModel

    @Mock
    lateinit var animeListLiveData: MutableLiveData<MutableList<AnimeApi>>

    @Before
    fun setUp() {
        DaggerViewModelComponent.builder()
            .testAppComponent(TestAnimeApp.getAppComponent())
            .build()
            .inject(this)

        MockitoAnnotations.initMocks(this)

        val liveData = MainViewModel::class.java.getDeclaredField("animeListLiveData")
        liveData.isAccessible = true
        liveData.set(mainViewModel, animeListLiveData)
    }

    @Test
    fun restart() {
        mainViewModel.restart()
        Mockito.verify(animeListLiveData).value
    }

    @Test
    fun searchOnSuccess() {
        mainViewModel.search("Death note")
        Mockito.verify(animeListLiveData).value
    }

    @Test
    fun searchUnSuccess() {
        mainViewModel.search("12345678")
        Mockito.verify(animeListLiveData, never()).value
    }

    @Test
    fun setFilterOnSuccess() {
        mainViewModel.setFilter(Filter(listOf("winter", "summer"), 2018.toString(), "pirate", listOf("PG", "R")))
        Mockito.verify(animeListLiveData).value
    }

    @Test
    fun setFilterUnSuccess() {
        mainViewModel.setFilter(Filter(listOf("winter", "summer"), 2018.toString(), "pirate", listOf("R18")))
        Mockito.verify(animeListLiveData, never()).value
    }
}