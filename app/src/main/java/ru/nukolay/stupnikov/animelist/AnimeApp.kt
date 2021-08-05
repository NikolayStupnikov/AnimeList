package ru.nukolay.stupnikov.animelist

import android.app.Application
import ru.nukolay.stupnikov.animelist.di.component.AppComponent
import ru.nukolay.stupnikov.animelist.di.component.DaggerAppComponent

class AnimeApp : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()
    }
}