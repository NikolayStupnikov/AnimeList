package ru.nukolay.stupnikov.animelist.vm

import androidx.lifecycle.MutableLiveData
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import ru.nukolay.stupnikov.animelist.TestAnimeApp
import ru.nukolay.stupnikov.animelist.data.api.response.category.CategoryApi
import ru.nukolay.stupnikov.animelist.di.component.DaggerViewModelComponent
import ru.nukolay.stupnikov.animelist.rule.RxImmediateSchedulerRule
import ru.nukolay.stupnikov.animelist.ui.filter.FilterViewModel
import javax.inject.Inject

@RunWith(MockitoJUnitRunner::class)
class FilterViewModelTest {

    @get:Rule
    val schedulers: RxImmediateSchedulerRule = RxImmediateSchedulerRule()

    @Inject
    lateinit var filterViewModel: FilterViewModel

    @Mock
    lateinit var categoryListLiveData: MutableLiveData<MutableList<CategoryApi>>

    @Before
    fun setUp() {
        DaggerViewModelComponent.builder()
            .testAppComponent(TestAnimeApp.getAppComponent())
            .build()
            .inject(this)

        MockitoAnnotations.initMocks(this)

        val liveData = FilterViewModel::class.java.getDeclaredField("categoryListLiveData")
        liveData.isAccessible = true
        liveData.set(filterViewModel, categoryListLiveData)
    }

    @Test
    fun getCategoryList() {
        val method = FilterViewModel::class.java.getDeclaredMethod("getCategoryList");
        method.isAccessible = true;
        method.invoke(filterViewModel)

        Mockito.verify(categoryListLiveData, times(12)).value
    }
}