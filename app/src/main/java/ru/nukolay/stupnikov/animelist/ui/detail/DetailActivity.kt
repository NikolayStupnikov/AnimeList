package ru.nukolay.stupnikov.animelist.ui.detail

import android.os.Bundle
import ru.nukolay.stupnikov.animelist.BR
import ru.nukolay.stupnikov.animelist.R
import ru.nukolay.stupnikov.animelist.data.api.response.anime.Titles
import ru.nukolay.stupnikov.animelist.databinding.ActivityDetailBinding
import ru.nukolay.stupnikov.animelist.di.component.ActivityComponent
import ru.nukolay.stupnikov.animelist.ui.base.BaseActivity

class DetailActivity : BaseActivity<ActivityDetailBinding, DetailViewModel>(), DetailNavigator {

    companion object {
        const val ID_ANIME = "id_anime"
        const val TITLES = "titles"
    }

    override val bindingVariable: Int
        get() = BR.viewModel

    override val layoutId: Int
        get() = R.layout.activity_detail

    override fun performDependencyInjection(buildComponent: ActivityComponent) {
        buildComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewModel.setNavigator(this)
        initToolbar()

        if (!mViewModel.vmIsWasAttached) {
            mViewModel.getDetailsAnime(intent.getIntExtra(ID_ANIME, 0))
        }

        mViewModel.vmIsWasAttached = true
    }

    private fun initToolbar() {
        setSupportActionBar(viewDataBinding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        viewDataBinding.toolbar.setNavigationOnClickListener{
            onBackPressed()
        }
        val titles: Titles? = intent.getSerializableExtra(TITLES) as Titles?
        if (titles != null) {
            if (!titles.en.isNullOrEmpty()) {
                supportActionBar!!.title = titles.en
            } else if (!titles.enJp.isNullOrEmpty()) {
                supportActionBar!!.title = titles.enJp
            } else if (!titles.jp.isNullOrEmpty()) {
                supportActionBar!!.title = titles.jp
            }
        }
    }
}