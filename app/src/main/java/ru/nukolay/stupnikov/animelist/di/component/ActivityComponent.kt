package ru.nukolay.stupnikov.animelist.di.component

import dagger.Component
import ru.nukolay.stupnikov.animelist.di.module.ActivityModule
import ru.nukolay.stupnikov.animelist.di.scope.ActivityScope
import ru.nukolay.stupnikov.animelist.ui.detail.DetailActivity
import ru.nukolay.stupnikov.animelist.ui.filter.FilterActivity
import ru.nukolay.stupnikov.animelist.ui.main.MainActivity

@ActivityScope
@Component(modules = [ActivityModule::class], dependencies = [AppComponent::class])
interface ActivityComponent {

    fun inject(activity: MainActivity)

    fun inject(activity: FilterActivity)

    fun inject(activity: DetailActivity)
}