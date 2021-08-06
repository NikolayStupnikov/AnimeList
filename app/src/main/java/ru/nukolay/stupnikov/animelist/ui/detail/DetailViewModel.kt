package ru.nukolay.stupnikov.animelist.ui.detail

import androidx.databinding.ObservableField
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ru.nukolay.stupnikov.animelist.data.DataManager
import ru.nukolay.stupnikov.animelist.data.api.response.category.CategoryApi
import ru.nukolay.stupnikov.animelist.ui.base.BaseViewModel
import ru.nukolay.stupnikov.animelist.util.zipWith
import java.lang.StringBuilder

class DetailViewModel(private val dataManager: DataManager): BaseViewModel<DetailNavigator>() {

    var vmIsWasAttached = false
    private var contentDetails: Disposable? = null

    var description: ObservableField<String> = ObservableField()
    var rating: ObservableField<Int> = ObservableField()
    var startDate: ObservableField<String> = ObservableField()
    var endDate: ObservableField<String> = ObservableField()
    var ageRating: ObservableField<String> = ObservableField()
    var episodeCount: ObservableField<Int> = ObservableField()
    var episodeLength: ObservableField<Int> = ObservableField()
    var categories: ObservableField<String> = ObservableField()
    var imageUrl: ObservableField<String> = ObservableField()

    fun getDetailsAnime(id: Int) {
        setIsLoading(true)
        compositeRemove(contentDetails)
        contentDetails = dataManager.getDetails(id)
                .zipWith(dataManager.getCategoriesForAnime(id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    setIsLoading(false)
                    if (it.first.result != null && it.first.result!!.attributes != null) {
                        description.set(it.first.result!!.attributes!!.description ?: "")
                        rating.set(it.first.result!!.attributes!!.ratingRank)
                        startDate.set(it.first.result!!.attributes!!.startDate ?: "-")
                        endDate.set(it.first.result!!.attributes!!.endDate ?: "-")
                        ageRating.set("${it.first.result!!.attributes!!.ageRating}, ${it.first.result!!.attributes!!.ageRatingGuide}")
                        episodeCount.set(it.first.result!!.attributes!!.episodeCount)
                        episodeLength.set(it.first.result!!.attributes!!.episodeLength)
                        categories.set(getStringCategories(it.second.result))
                        imageUrl.set(it.first.result!!.attributes!!.posterImage?.original ?: "")
                    } else {
                        getNavigator()?.onBackPressed()
                    }
                }, {
                    getNavigator()?.onBackPressed()
                })
        compositeAdd(contentDetails)
    }

    private fun getStringCategories(list: List<CategoryApi>?): String {
        if (list.isNullOrEmpty()) return "-"
        val builder = StringBuilder()
        for (category in list) {
            if (category.attributes != null && !category.attributes.title.isNullOrEmpty()) {
                builder.append("${category.attributes.title}, ")
            }
        }
        if (builder.isNotEmpty()) {
            return builder.toString().substring(0, builder.toString().length - 2)
        }
        return "-"
    }
}