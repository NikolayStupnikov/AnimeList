package ru.nukolay.stupnikov.animelist.data.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap
import ru.nukolay.stupnikov.animelist.StaticConfig.PAGE_LIMIT
import ru.nukolay.stupnikov.animelist.data.api.response.anime.AnimeResponse
import ru.nukolay.stupnikov.animelist.data.api.response.category.CategoryResponse
import ru.nukolay.stupnikov.animelist.data.api.response.detail.DetailResponse

interface BackendApi {

    @GET("anime")
    fun requestAnimeList(@QueryMap params: Map<String, String>): Single<AnimeResponse>

    @GET("categories?page[limit]=$PAGE_LIMIT")
    fun requestCategoryList(@Query("page[offset]") offset: Int): Single<CategoryResponse>

    @GET("anime/{id}")
    fun getDetails(@Path("id") id: Int): Single<DetailResponse>

    @GET("anime/{id}/categories?page[limit]=$PAGE_LIMIT")
    fun getCategoriesForAnime(@Path("id") id: Int): Single<CategoryResponse>
}