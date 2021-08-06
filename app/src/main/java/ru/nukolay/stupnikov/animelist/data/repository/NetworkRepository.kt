package ru.nukolay.stupnikov.animelist.data.repository

import io.reactivex.Single
import ru.nukolay.stupnikov.animelist.data.api.response.anime.AnimeResponse
import ru.nukolay.stupnikov.animelist.data.api.response.category.CategoryResponse
import ru.nukolay.stupnikov.animelist.data.api.response.detail.DetailResponse
import ru.nukolay.stupnikov.animelist.ui.filter.Filter

interface NetworkRepository {

    fun requestAnimeList(offset: Int, search: String, filter: Filter?): Single<AnimeResponse>
    fun requestCategoryList(offset: Int): Single<CategoryResponse>
    fun getDetails(id: Int): Single<DetailResponse>
    fun getCategoriesForAnime(id: Int): Single<CategoryResponse>
}