package ru.nukolay.stupnikov.animelist.data.repository

import io.reactivex.Single
import ru.nukolay.stupnikov.animelist.data.database.entity.CategoryEntity

interface DatabaseRepository {

    fun getAllCategories(): Single<List<CategoryEntity>>
    fun insertCategories(categories: List<CategoryEntity>)
    fun getCountCategories(): Single<Int>
}