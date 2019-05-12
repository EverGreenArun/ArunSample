package com.example.sample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.sample.R
import com.example.sample.base.BaseActivity
import com.example.sample.base.BaseFragment
import com.example.sample.base.BaseViewModel
import com.example.sample.databinding.FragmentPostBinding
import com.example.sample.ui.adapter.PostAdapter


class PostFragment : BaseFragment<ViewDataBinding, BaseViewModel>() {

    companion object {
        fun newInstance() = PostFragment()
    }

    private lateinit var dataBinding: FragmentPostBinding

    private lateinit var postAdapter: PostAdapter

    private val viewModel by lazy {
        ViewModelProviders.of(this, PostFragmentViewModel.PostFragmentViewModelFactory())
            .get(PostFragmentViewModel::class.java)
    }

    override fun setTitle() {
        (activity as BaseActivity<*>).setTitle("Post")
    }

    override fun getViewModel(): BaseViewModel = viewModel

    override fun getDataBinding(inflater: LayoutInflater, container: ViewGroup?): ViewDataBinding {
        dataBinding = FragmentPostBinding.inflate(inflater, container, false)
        return dataBinding
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initLiveDataObserver()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postAdapter = PostAdapter(viewModel.posts)
        val itemDecorator = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        activity?.applicationContext?.let { context ->
            ContextCompat.getDrawable(context, R.drawable.divider)?.let { drawable ->
                itemDecorator.setDrawable(drawable)
                dataBinding.recyclerPost.addItemDecoration(itemDecorator)
            }
        }
        dataBinding.recyclerPost.adapter = postAdapter
    }

    override fun onResume() {
        super.onResume()
        if (isNetworkConnected()) {
            viewModel.fetchPosts()
        } else {
            dataBinding.tVMessage.visibility = View.VISIBLE
            dataBinding.tVMessage.text = "Network error"
        }
    }

    private fun initLiveDataObserver() {
        val nameObserver = Observer<Status> {
            it?.let { status ->
                when (status) {
                    Status.SUCCESS -> {
                        postAdapter.notifyDataSetChanged()
                        dataBinding.tVMessage.visibility = View.GONE
                    }
                    Status.FAILURE -> {
                        dataBinding.tVMessage.visibility = View.VISIBLE
                        dataBinding.tVMessage.text = "Network error"
                    }
                }
            }
        }
        viewModel.liveData.observe(this, nameObserver)
    }
}