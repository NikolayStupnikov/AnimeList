package ru.nukolay.stupnikov.animelist.di.component

import dagger.Component
import ru.nukolay.stupnikov.animelist.di.module.AndroidTestAppModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidTestAppModule::class])
interface AndroidTestAppComponent: AppComponent