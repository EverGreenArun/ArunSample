package com.example.sample.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import com.example.sample.base.BaseActivity
import com.example.sample.base.BaseFragment
import com.example.sample.base.BaseViewModel
import com.example.sample.databinding.FragmentDetailScreenBinding
import com.example.sample.pojo.DetailScreenData
import android.webkit.WebViewClient


class DetailScreenFragment : BaseFragment<ViewDataBinding, BaseViewModel>() {
    companion object {
        private const val DATA = "data"
        const val IMAGE_TYPE = "image"
        const val HTML_TYPE = "html"

        fun newInstance(detailScreenData: DetailScreenData): DetailScreenFragment {
            val fragment = DetailScreenFragment()
            val bundle = Bundle()
            bundle.putParcelable(DATA, detailScreenData)
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var dataBinding: FragmentDetailScreenBinding

    private val viewModel by lazy {
        ViewModelProviders.of(this,
            activity?.application?.let { DetailScreenFragmentViewModel.DetailScreenFragmentViewModelFactory(it) })
            .get(DetailScreenFragmentViewModel::class.java)
    }

    override fun setActionBarTitle() {
        viewModel.detailScreenData?.title?.let { (activity as BaseActivity<*>).updateTitle(it) }
        (activity as BaseActivity<*>).showActionBarBackButton(true)
    }

    override fun getViewModel(): BaseViewModel = viewModel

    override fun getDataBinding(inflater: LayoutInflater, container: ViewGroup?): ViewDataBinding {
        dataBinding = FragmentDetailScreenBinding.inflate(inflater, container, false)
        return dataBinding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getParcelable<DetailScreenData>(DATA)?.let { viewModel.detailScreenData = it }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.detailScreenData?.let {
            if (isNetworkConnected()) {
                dataBinding.webPage.visibility = View.VISIBLE
                dataBinding.webPage.settings.javaScriptEnabled = true
                dataBinding.webPage.settings.useWideViewPort = true
                dataBinding.webPage.settings.loadWithOverviewMode = true
                dataBinding.webPage.webViewClient = WebViewClient()
                dataBinding.webPage.loadUrl(it.fileUri)
            }
        }
    }
}
