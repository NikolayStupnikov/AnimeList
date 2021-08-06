package ru.nukolay.stupnikov.animelist.di.module

import dagger.Module
import dagger.Provides
import ru.nukolay.stupnikov.animelist.data.DataManager
import ru.nukolay.stupnikov.animelist.ui.detail.DetailViewModel
import ru.nukolay.stupnikov.animelist.ui.filter.FilterViewModel
import ru.nukolay.stupnikov.animelist.ui.main.MainViewModel

@Module
class TestActivityModule {

    @Provides
    fun provideMainViewModel(dataManager: DataManager): MainViewModel {
        return MainViewModel(dataManager)
    }

    @Provides
    fun provideFilterViewModel(dataManager: DataManager): FilterViewModel {
        return FilterViewModel(dataManager)
    }

    @Provides
    fun provideDetailViewModel(dataManager: DataManager): DetailViewModel {
        return DetailViewModel(dataManager)
    }
}