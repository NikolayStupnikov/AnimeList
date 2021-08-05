package ru.nukolay.stupnikov.animelist.ui.filter

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.databinding.ObservableList
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ru.nukolay.stupnikov.animelist.StaticConfig
import ru.nukolay.stupnikov.animelist.data.DataManager
import ru.nukolay.stupnikov.animelist.data.api.response.category.CategoryApi
import ru.nukolay.stupnikov.animelist.data.api.response.category.CategoryAttribute
import ru.nukolay.stupnikov.animelist.data.database.entity.CategoryEntity
import ru.nukolay.stupnikov.animelist.ui.base.BaseViewModel
import ru.nukolay.stupnikov.animelist.util.addAll
import kotlin.concurrent.thread

class FilterViewModel(private val dataManager: DataManager): BaseViewModel<Any>() {

    private var contentCategoryCount: Disposable? = null
    private var contentCategoryList: Disposable? = null

    val selectSeasons: ArrayList<String> = ArrayList()
    val selectAgeRating: ArrayList<String> = ArrayList()

    val categoryListLiveData: MutableLiveData<MutableList<CategoryApi>> = MutableLiveData()
    val categoryList: ObservableList<CategoryApi> = ObservableArrayList()

    val selectCategoryLiveData: MutableLiveData<String?> = MutableLiveData()
    var selectCategory: ObservableField<String?> = ObservableField()

    var vmIsWasAttached = false

    private var offset = 0
    private var maxCount = 0

    init {
        getCategoryList()
    }

    private fun getCategoryListFromNetwork() {
        compositeRemove(contentCategoryList)
        contentCategoryList = dataManager.requestCategoryList(offset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it != null) {
                        if (it.meta != null) {
                            maxCount = it.meta!!.count
                        }
                        if (!it.result.isNullOrEmpty()) {
                            categoryListLiveData.addAll(it.result!!)
                            offset += StaticConfig.PAGE_LIMIT

                            if (offset < maxCount) {
                                getCategoryList()
                            } else {
                                thread {
                                    dataManager.insertCategories(categoryListLiveData.value!!.map {
                                            category -> CategoryEntity(
                                            category.id,
                                            category.attributes?.title,
                                            category.attributes?.slug
                                        )
                                    }.toList())
                                }
                            }
                        }
                    }
                }, {})
        compositeAdd(contentCategoryList)
    }

    private fun getCategoryListFromDb() {
        compositeRemove(contentCategoryList)
        contentCategoryList = dataManager.getAllCategories()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                categoryListLiveData.addAll(it.map {
                        category -> CategoryApi(
                        CategoryAttribute(category.title, category.slug),
                        category.id
                    )
                }.toList())
            }, {})
        compositeAdd(contentCategoryList)
    }

    private fun getCategoryList() {
        compositeRemove(contentCategoryCount)
        contentCategoryCount = dataManager.getCountCategories()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it > 0) {
                    getCategoryListFromDb()
                } else {
                    getCategoryListFromNetwork()
                }
            }, {})
        compositeAdd(contentCategoryCount)
    }

    fun setCategoryList(categories: List<CategoryApi>) {
        this.categoryList.clear()
        this.categoryList.addAll(categories)
    }
}