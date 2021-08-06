package ru.nukolay.stupnikov.animelist.di.component

import dagger.Component
import ru.nukolay.stupnikov.animelist.di.module.TestActivityModule
import ru.nukolay.stupnikov.animelist.di.scope.ActivityScope
import ru.nukolay.stupnikov.animelist.vm.DetailViewModelTest
import ru.nukolay.stupnikov.animelist.vm.FilterViewModelTest
import ru.nukolay.stupnikov.animelist.vm.MainViewModelTest

@ActivityScope
@Component(modules = [TestActivityModule::class], dependencies = [TestAppComponent::class])
interface ViewModelComponent {

    fun inject(viewModel : MainViewModelTest)

    fun inject(viewModel: FilterViewModelTest)

    fun inject(viewModel: DetailViewModelTest)
}