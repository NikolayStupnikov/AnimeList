package ru.nukolay.stupnikov.animelist

import android.app.Application
import org.mockito.Mockito
import ru.nukolay.stupnikov.animelist.di.component.DaggerTestAppComponent
import ru.nukolay.stupnikov.animelist.di.component.TestAppComponent

object TestAnimeApp {

    private lateinit var appComponent: TestAppComponent

    fun getAppComponent(): TestAppComponent {
        if (!this::appComponent.isInitialized) {
            appComponent = DaggerTestAppComponent.builder()
                .application(Mockito.mock(Application::class.java))
                .build()
        }
        return appComponent
    }
}