package ru.nukolay.stupnikov.animelist.vm

import androidx.databinding.ObservableField
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import ru.nukolay.stupnikov.animelist.TestAnimeApp
import ru.nukolay.stupnikov.animelist.di.component.DaggerViewModelComponent
import ru.nukolay.stupnikov.animelist.rule.RxImmediateSchedulerRule
import ru.nukolay.stupnikov.animelist.ui.detail.DetailViewModel
import javax.inject.Inject

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    @get:Rule
    val schedulers: RxImmediateSchedulerRule = RxImmediateSchedulerRule()

    @Inject
    lateinit var detailViewModel: DetailViewModel

    @Mock
    lateinit var description: ObservableField<String>

    @Before
    fun setUp() {
        DaggerViewModelComponent.builder()
                .testAppComponent(TestAnimeApp.getAppComponent())
                .build()
                .inject(this)

        MockitoAnnotations.initMocks(this)
        detailViewModel.description = description
    }

    @Test
    fun getDetailsAnimeOnSuccess() {
        detailViewModel.getDetailsAnime(1)
        verify(description).set(ArgumentMatchers.anyString())
    }

    @Test
    fun getDetailsAnimeUnSuccess() {
        detailViewModel.getDetailsAnime(0)
        verify(description, never()).set(ArgumentMatchers.anyString())
    }
}