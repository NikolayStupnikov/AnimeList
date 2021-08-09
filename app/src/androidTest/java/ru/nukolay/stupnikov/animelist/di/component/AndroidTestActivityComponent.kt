package ru.nukolay.stupnikov.animelist.di.component

import dagger.Component
import ru.nukolay.stupnikov.animelist.di.module.ActivityModule
import ru.nukolay.stupnikov.animelist.di.scope.ActivityScope

@ActivityScope
@Component(modules = [ActivityModule::class], dependencies = [AndroidTestAppComponent::class])
interface AndroidTestActivityComponent: ActivityComponent