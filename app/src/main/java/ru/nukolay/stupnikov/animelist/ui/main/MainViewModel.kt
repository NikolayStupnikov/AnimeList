package ru.nukolay.stupnikov.animelist.ui.main

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ru.nukolay.stupnikov.animelist.StaticConfig
import ru.nukolay.stupnikov.animelist.data.DataManager
import ru.nukolay.stupnikov.animelist.data.api.response.anime.AnimeApi
import ru.nukolay.stupnikov.animelist.ui.base.BaseViewModel
import ru.nukolay.stupnikov.animelist.ui.filter.Filter
import ru.nukolay.stupnikov.animelist.util.addAll
import ru.nukolay.stupnikov.animelist.util.clear

class MainViewModel(private val dataManager: DataManager): BaseViewModel<MainNavigator>() {

    private var contentAnimeList: Disposable? = null

    val animeListLiveData: MutableLiveData<MutableList<AnimeApi>> = MutableLiveData()
    val animeList: ObservableList<AnimeApi> = ObservableArrayList()

    private var offset = 0
    private var maxCount = 0
    private var search: String = ""
    var filter: Filter? = null
    private set

    init {
        getAnimeList()
    }

    fun setAnimeList(animeList: List<AnimeApi>) {
        this.animeList.clear()
        this.animeList.addAll(animeList)
    }

    private fun getAnimeList() {
        setIsLoading(true)
        compositeRemove(contentAnimeList)
        contentAnimeList = dataManager.requestAnimeList(offset, search, filter)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    setIsLoading(false)
                    if (it != null) {
                        if (!it.result.isNullOrEmpty()) {
                            animeListLiveData.addAll(it.result!!)
                            offset += StaticConfig.PAGE_LIMIT
                        }
                        if (it.meta != null) {
                            maxCount = it.meta!!.count
                        }
                    }
                }, {
                    setIsLoading(false)
                    getNavigator()?.showError(showErrorMessage(it))
                })
        compositeAdd(contentAnimeList)
    }

    fun restart() {
        offset = 0
        animeListLiveData.clear()
        getAnimeList()
    }

    fun search(search: String) {
        this.search = search
        restart()
    }

    fun setFilter(filter: Filter) {
        this.filter = filter
        restart()
    }

    fun doOnScroll(totalItemCount: Int, lastVisibleItemPosition: Int) {
        if (totalItemCount == lastVisibleItemPosition + 1 && offset < maxCount) {
            getAnimeList()
        }
    }
}