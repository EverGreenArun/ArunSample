package com.example.sample.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sample.R
import com.example.sample.base.BaseActivity
import com.example.sample.base.BaseFragment
import com.example.sample.base.BaseViewModel
import com.example.sample.databinding.FragmentPostersBinding
import com.example.sample.pojo.User
import com.example.sample.ui.adapter.OnItemClickLister
import com.example.sample.ui.adapter.PostAdapter

class PostersFragment : BaseFragment<ViewDataBinding, BaseViewModel>() {

    companion object {
        fun newInstance() = PostersFragment()
    }

    private lateinit var dataBinding: FragmentPostersBinding
    private lateinit var postAdapter: PostAdapter
    private val viewModel by lazy {
        ViewModelProviders.of(this,
            activity?.application?.let { PostersFragmentViewModel.PostersFragmentViewModelFactory(it) })
            .get(PostersFragmentViewModel::class.java)
    }
    private var isLoading: Boolean = false
    var loadDetailScreen: LoadDetailScreenFragment? = null

    override fun setActionBarTitle() {
        (activity as BaseActivity<*>).updateTitle(getString(R.string.posters))
        (activity as BaseActivity<*>).showActionBarBackButton(false)
    }

    override fun getViewModel(): BaseViewModel = viewModel

    override fun getDataBinding(inflater: LayoutInflater, container: ViewGroup?): ViewDataBinding {
        dataBinding = FragmentPostersBinding.inflate(inflater, container, false)
        return dataBinding
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initLiveDataObserver()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setPosterAdapter()
        setRecyclerViewDivider()
        setLoadMoreListener()
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun setLoadMoreListener() {
        dataBinding.recyclerPost.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            val visibleThreshold = 5
            val linearLayoutManager = dataBinding.recyclerPost.layoutManager as LinearLayoutManager
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = linearLayoutManager.itemCount
                val lastVisibleItemPOs = linearLayoutManager.findLastVisibleItemPosition()
                if (!isLoading && totalItemCount <= lastVisibleItemPOs + visibleThreshold) {
                    loadData()
                }
            }
        })
    }

    private fun setRecyclerViewDivider() {
        val itemDecorator = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        activity?.applicationContext?.let { context ->
            ContextCompat.getDrawable(context, R.drawable.divider)?.let { drawable ->
                itemDecorator.setDrawable(drawable)
                dataBinding.recyclerPost.addItemDecoration(itemDecorator)
            }
        }
    }

    private fun setPosterAdapter() {
        postAdapter = PostAdapter(viewModel.posters, object : OnItemClickLister {
            override fun onProfileClick(user: User) {
                user.profileImage?.large?.let {
                    user.name?.let { name ->
                        loadDetailScreen?.load(DetailScreenFragment.IMAGE_TYPE, it, name)
                    }
                }
            }

            override fun onWebClick(uri: String, user: User) {
                user.name?.let { loadDetailScreen?.load(DetailScreenFragment.HTML_TYPE, uri, it) }
            }
        })

        dataBinding.recyclerPost.adapter = postAdapter
    }

    private fun loadData() {
        if (isNetworkConnected()) {
            isLoading = true
            dataBinding.tVMessage.visibility = View.GONE
            viewModel.fetchPosters()
            Log.i("loadData", "isNetworkConnected true")
        } else {
            Log.i("loadData", "isNetworkConnected false")
            showOfflineMessage()
            if (!viewModel.offlineDataLoaded) {
                Log.i("loadData", "offlineDataLoaded false")
                viewModel.getAllPosters()
                viewModel.offlineDataLoaded = true
            }
        }
    }

    private fun initLiveDataObserver() {
        val nameObserver = Observer<Status> {
            it?.let { status ->
                when (status) {
                    Status.SUCCESS -> {
                        isLoading = false
                        postAdapter.notifyDataSetChanged()
                        dataBinding.tVMessage.visibility = View.GONE
                        viewModel.offlineDataLoaded = false
                    }
                    Status.FAILURE -> {
                        isLoading = false
                        dataBinding.tVMessage.visibility = View.VISIBLE
                        dataBinding.tVMessage.text = getString(R.string.api_call_failure)
                    }
                    Status.NO_MORE_DATA -> {
                        isLoading = false
                        dataBinding.tVMessage.visibility = View.VISIBLE
                        dataBinding.tVMessage.text = getString(R.string.no_more_data)
                    }
                    Status.OLD_DATA -> {
                        showOfflineMessage()
                    }
                }
            }
        }
        viewModel.liveData.observe(this, nameObserver)
    }

    private fun showOfflineMessage() {
        dataBinding.tVMessage.visibility = View.VISIBLE
        if (viewModel.posters.size == 0) {
            dataBinding.tVMessage.text = getString(R.string.empty_offline_data)
        } else {
            dataBinding.tVMessage.text = getString(R.string.showing_offline_data)
        }
        postAdapter.notifyDataSetChanged()
    }
}

interface LoadDetailScreenFragment {
    fun load(fileType: String, fileUri: String, title: String)
}