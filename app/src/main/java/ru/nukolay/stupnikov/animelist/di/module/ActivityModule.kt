package ru.nukolay.stupnikov.animelist.di.module

import androidx.core.util.Supplier
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.Module
import dagger.Provides
import ru.nukolay.stupnikov.animelist.ViewModelProviderFactory
import ru.nukolay.stupnikov.animelist.data.DataManager
import ru.nukolay.stupnikov.animelist.ui.base.BaseActivity
import ru.nukolay.stupnikov.animelist.ui.base.BaseViewModel
import ru.nukolay.stupnikov.animelist.ui.detail.DetailViewModel
import ru.nukolay.stupnikov.animelist.ui.filter.FilterViewModel
import ru.nukolay.stupnikov.animelist.ui.main.anime.AnimeRecyclerViewAdapter
import ru.nukolay.stupnikov.animelist.ui.main.MainViewModel
import java.util.*

@Module
class ActivityModule(private val activity: BaseActivity<out ViewDataBinding, out BaseViewModel<out Any>>) {

    @Provides
    fun provideMainViewModel(dataManager: DataManager): MainViewModel {
        val supplier: Supplier<MainViewModel> = Supplier { MainViewModel(dataManager) }
        val factory: ViewModelProviderFactory<MainViewModel> = ViewModelProviderFactory(
            MainViewModel::class.java,
            supplier
        )
        return ViewModelProvider(activity, factory).get(MainViewModel::class.java)
    }

    @Provides
    fun provideFilterViewModel(dataManager: DataManager): FilterViewModel {
        val supplier: Supplier<FilterViewModel> = Supplier { FilterViewModel(dataManager) }
        val factory: ViewModelProviderFactory<FilterViewModel> = ViewModelProviderFactory(
                FilterViewModel::class.java,
                supplier
        )
        return ViewModelProvider(activity, factory).get(FilterViewModel::class.java)
    }

    @Provides
    fun provideDetailViewModel(dataManager: DataManager): DetailViewModel {
        val supplier: Supplier<DetailViewModel> = Supplier { DetailViewModel(dataManager) }
        val factory: ViewModelProviderFactory<DetailViewModel> = ViewModelProviderFactory(
                DetailViewModel::class.java,
                supplier
        )
        return ViewModelProvider(activity, factory).get(DetailViewModel::class.java)
    }

    @Provides
    fun provideAnimeAdapter(): AnimeRecyclerViewAdapter {
        return AnimeRecyclerViewAdapter(ArrayList())
    }

    @Provides
    fun provideLinearLayoutManager(): LinearLayoutManager {
        return LinearLayoutManager(activity)
    }
}