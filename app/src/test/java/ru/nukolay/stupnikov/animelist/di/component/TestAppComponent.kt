package ru.nukolay.stupnikov.animelist.di.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import ru.nukolay.stupnikov.animelist.data.DataManager
import ru.nukolay.stupnikov.animelist.di.module.TestAppModule
import javax.inject.Singleton

@Singleton
@Component(modules = [TestAppModule::class])
interface TestAppComponent {

    fun getDataManager(): DataManager

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): TestAppComponent
    }
}