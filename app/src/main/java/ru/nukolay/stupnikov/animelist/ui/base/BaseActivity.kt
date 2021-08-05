package ru.nukolay.stupnikov.animelist.ui.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import ru.nukolay.stupnikov.animelist.AnimeApp
import ru.nukolay.stupnikov.animelist.di.component.ActivityComponent
import ru.nukolay.stupnikov.animelist.di.component.DaggerActivityComponent
import ru.nukolay.stupnikov.animelist.di.module.ActivityModule
import javax.inject.Inject

abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel<out Any>> :
    AppCompatActivity() {

    lateinit var viewDataBinding: T

    @Inject
    lateinit var mViewModel: V

    abstract val bindingVariable: Int

    @get:LayoutRes
    abstract val layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        performDependencyInjection(buildComponent)
        super.onCreate(savedInstanceState)
        performDataBinding()
    }

    private val buildComponent: ActivityComponent
        get() = DaggerActivityComponent.builder()
            .appComponent((application as AnimeApp).appComponent)
            .activityModule(ActivityModule(this))
            .build()

    abstract fun performDependencyInjection(buildComponent: ActivityComponent)

    private fun performDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView(this, layoutId)
        viewDataBinding.setVariable(bindingVariable, mViewModel)
        viewDataBinding.executePendingBindings()
    }
}