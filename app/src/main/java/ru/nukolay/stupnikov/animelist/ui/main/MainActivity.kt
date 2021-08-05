package ru.nukolay.stupnikov.animelist.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ru.nukolay.stupnikov.animelist.BR
import ru.nukolay.stupnikov.animelist.R
import ru.nukolay.stupnikov.animelist.databinding.ActivityMainBinding
import ru.nukolay.stupnikov.animelist.di.component.ActivityComponent
import ru.nukolay.stupnikov.animelist.ui.base.BaseActivity
import ru.nukolay.stupnikov.animelist.ui.filter.Filter
import ru.nukolay.stupnikov.animelist.ui.filter.FilterActivity
import ru.nukolay.stupnikov.animelist.ui.filter.FilterActivity.Companion.FILTER
import ru.nukolay.stupnikov.animelist.ui.filter.FilterActivity.Companion.FILTER_REQUEST_CODE
import ru.nukolay.stupnikov.animelist.ui.main.anime.AnimeRecyclerViewAdapter
import javax.inject.Inject

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(), MainNavigator, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    lateinit var animeAdapter: AnimeRecyclerViewAdapter

    @Inject
    lateinit var layoutManager: LinearLayoutManager

    override val bindingVariable: Int
        get() = BR.viewModel

    override val layoutId: Int
        get() = R.layout.activity_main

    override fun performDependencyInjection(buildComponent: ActivityComponent) {
        buildComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewModel.setNavigator(this)

        setSupportActionBar(viewDataBinding.toolbar)
        viewDataBinding.refreshLayout.setOnRefreshListener(this)
        initRecycler()
        subscribeToLiveData()
    }

    override fun onResume() {
        super.onResume()
        viewDataBinding.searchForm.addTextChangedListener {
            mViewModel.search(it.toString())
        }
    }

    private fun initRecycler() {
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        viewDataBinding.rvAnimeList.layoutManager = layoutManager
        viewDataBinding.rvAnimeList.adapter = animeAdapter
        viewDataBinding.rvAnimeList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val totalItemCount = recyclerView.layoutManager!!.itemCount
                val lastVisibleItemPosition =
                    (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                mViewModel.doOnScroll(
                    totalItemCount,
                    lastVisibleItemPosition
                )
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.filter -> {
                val intent = Intent(this, FilterActivity::class.java)
                if (mViewModel.filter != null) {
                    intent.putExtra(FILTER, mViewModel.filter)
                }
                startActivityForResult(intent, FILTER_REQUEST_CODE)
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onRefresh() {
        mViewModel.restart()
        viewDataBinding.refreshLayout.isRefreshing = false
    }

    private fun subscribeToLiveData() {
        mViewModel.animeListLiveData.observe(this) { list ->
            mViewModel.setAnimeList(list)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == FILTER_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null && data.hasExtra(FILTER)) {
                mViewModel.setFilter(data.getSerializableExtra(FILTER) as Filter)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}