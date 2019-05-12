package com.example.sample.ui

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentManager
import com.example.sample.R
import com.example.sample.base.BaseActivity
import com.example.sample.pojo.DetailScreenData


class HomeScreenActivity : BaseActivity<ViewDataBinding>(), FragmentManager.OnBackStackChangedListener {

    private val loadDetailScreenFragment = object : LoadDetailScreenFragment {
        override fun load(fileType: String, fileUri: String, title: String) {
            addFragment(
                R.id.container,
                DetailScreenFragment.newInstance(DetailScreenData(fileType, fileUri, title)),
                DetailScreenFragment::class.java.name
            )
        }
    }

    override fun getContentView(): Int = R.layout.activity_home_screen

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val posterFragment = PostersFragment.newInstance()
        posterFragment.loadDetailScreen = loadDetailScreenFragment
        addFragment(R.id.container, posterFragment, PostersFragment::class.java.name)
        supportFragmentManager.addOnBackStackChangedListener(this)
    }

    override fun onBackStackChanged() {
        val fragment = supportFragmentManager?.findFragmentById(com.example.sample.R.id.container)
        if (fragment is PostersFragment)
            fragment.setActionBarTitle()

    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager?.findFragmentById(com.example.sample.R.id.container)
        if (fragment is PostersFragment) {
            finish()
        } else {
            super.onBackPressed()
        }
    }
}