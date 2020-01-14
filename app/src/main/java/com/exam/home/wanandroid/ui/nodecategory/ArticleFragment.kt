package com.exam.home.wanandroid.ui.nodecategory

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.exam.home.wanandroid.R
import com.exam.home.wanandroid.base.LazyFragment
import com.exam.home.wanandroid.ui.home.HomeArticleAdapter
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import kotlinx.android.synthetic.main.fragment_articles.*

class ArticleFragment : LazyFragment() {


    private lateinit var vModel: NodeCategoryVModel
    private var cid: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        vModel = ViewModelProviders.of(this).get(NodeCategoryVModel::class.java)
        return inflater.inflate(R.layout.fragment_articles, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getBundleData()
        initListener()
    }

    override fun lazyLoad() {
        vModel.getNodeArticle(cid)
    }

    private fun getBundleData() {
        cid = arguments?.getInt("cid") ?: 0
    }

    private fun initListener() {
        initRV()
        vModel.hasArticles.observe(this, Observer {
            iv_node_category.finishLoadMore()
            if (it == true) {
                (iv_node_category.recyclerView().adapter as HomeArticleAdapter).updateDatas(vModel.articles)
            }
        })

        iv_node_category.loadMore(OnLoadMoreListener {
            if (vModel.hasToEnd) {
                Toast.makeText(context, "已经到底啦", Toast.LENGTH_SHORT).show()
                iv_node_category.finishLoadMore()
                return@OnLoadMoreListener
            }
            vModel.getNodeArticle(cid)
        })
    }

    private fun initRV() {
        iv_node_category.setLayoutManager(LinearLayoutManager(context))
        iv_node_category.setAdapter(HomeArticleAdapter())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("yxy", "onDestroyView ArticleFragment")
    }
}