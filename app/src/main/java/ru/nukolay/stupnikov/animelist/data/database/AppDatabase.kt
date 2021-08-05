package ru.nukolay.stupnikov.animelist.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.nukolay.stupnikov.animelist.data.database.dao.CategoryDao
import ru.nukolay.stupnikov.animelist.data.database.entity.CategoryEntity

@Database(entities = [CategoryEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun categoryDao(): CategoryDao
}