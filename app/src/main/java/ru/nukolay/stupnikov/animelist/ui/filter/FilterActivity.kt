package ru.nukolay.stupnikov.animelist.ui.filter

import android.content.Intent
import android.os.Bundle
import android.widget.CheckBox
import ru.nukolay.stupnikov.animelist.BR
import ru.nukolay.stupnikov.animelist.R
import ru.nukolay.stupnikov.animelist.data.api.response.category.CategoryApi
import ru.nukolay.stupnikov.animelist.data.api.response.category.CategoryAttribute
import ru.nukolay.stupnikov.animelist.databinding.ActivityFilterBinding
import ru.nukolay.stupnikov.animelist.di.component.ActivityComponent
import ru.nukolay.stupnikov.animelist.ui.base.BaseActivity
import ru.nukolay.stupnikov.animelist.ui.filter.category.CategoryListAdapter

class FilterActivity : BaseActivity<ActivityFilterBinding, FilterViewModel>() {

    companion object {
        const val FILTER = "filter"
        const val FILTER_REQUEST_CODE = 1000
        val seasons = listOf("winter", "spring", "summer", "fall")
        val ageRatingList = listOf("G", "PG", "R", "R18")
    }

    override val bindingVariable: Int
        get() = BR.viewModel

    override val layoutId: Int
        get() = R.layout.activity_filter

    override fun performDependencyInjection(buildComponent: ActivityComponent) {
        buildComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!mViewModel.vmIsWasAttached && intent.hasExtra(FILTER)) {
            val filter = intent.getSerializableExtra(FILTER) as Filter
            mViewModel.selectSeasons.addAll(filter.seasons)
            mViewModel.selectAgeRating.addAll(filter.ageRatingList)
            viewDataBinding.etYear.setText(filter.year)
            mViewModel.selectCategoryLiveData.value = filter.category
        }

        initToolbar()
        initSpinner()
        initSeasons()
        initAgeRating()

        viewDataBinding.btnSubmit.setOnClickListener {
            val intent = Intent()
            val filter = Filter(
                mViewModel.selectSeasons,
                viewDataBinding.etYear.text.toString(),
                if (viewDataBinding.categorySpinner.selectedItemPosition == 0) null
                else (viewDataBinding.categorySpinner.getItemAtPosition(viewDataBinding.categorySpinner.selectedItemPosition) as CategoryApi).attributes?.slug,
                mViewModel.selectAgeRating
            )
            intent.putExtra(FILTER, filter)
            setResult(RESULT_OK, intent)
            finish()
        }

        subscribeToLiveData()

        mViewModel.vmIsWasAttached = true
    }

    private fun initAgeRating() {
        for (ageRating in ageRatingList) {
            val checkBox = CheckBox(this)
            checkBox.text = ageRating
            if (mViewModel.selectAgeRating.contains(ageRating)) {
                checkBox.isChecked = true
            }
            checkBox.setOnClickListener{
                if (checkBox.isChecked) {
                    mViewModel.selectAgeRating.add(ageRating)
                } else {
                    mViewModel.selectAgeRating.remove(ageRating)
                }
            }
            viewDataBinding.layoutAgeRating.addView(checkBox)
        }
    }

    private fun initSeasons() {
        for (season in seasons) {
            val checkBox = CheckBox(this)
            checkBox.text = season
            if (mViewModel.selectSeasons.contains(season)) {
                checkBox.isChecked = true
            }
            checkBox.setOnClickListener{
                if (checkBox.isChecked) {
                    mViewModel.selectSeasons.add(season)
                } else {
                    mViewModel.selectSeasons.remove(season)
                }
            }
            viewDataBinding.layoutSeasons.addView(checkBox)
        }
    }

    private fun subscribeToLiveData() {
        mViewModel.categoryListLiveData.observe(this) { list ->
            mViewModel.setCategoryList(list)
        }
        mViewModel.selectCategoryLiveData.observe(this) {
            mViewModel.selectCategory.set(it)
        }
    }

    private fun initSpinner() {
        val adapter = CategoryListAdapter(this, arrayListOf(CategoryApi(CategoryAttribute(
                getString(R.string.choose_category), getString(R.string.choose_category)), 0)))
        viewDataBinding.categorySpinner.adapter = adapter
    }

    private fun initToolbar() {
        setSupportActionBar(viewDataBinding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        viewDataBinding.toolbar.setNavigationOnClickListener{
            onBackPressed()
        }
    }
}