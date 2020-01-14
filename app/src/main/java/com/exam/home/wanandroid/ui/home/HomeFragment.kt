package com.exam.home.wanandroid.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.exam.home.wanandroid.R
import com.exam.home.wanandroid.WebActivity
import com.exam.home.wanandroid.customview.MyBanner
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(), MyBanner.BannerClick {

    private val tags = this::class.java.simpleName

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var banner: MyBanner

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRv()
        observerModel()
        initListener()
    }

    private fun observerModel() {
        homeViewModel.articleSize.observe(this, Observer {
            Log.d(tags, "文章列表 = $it")
            (rv_article.recyclerView().adapter as HomeArticleAdapter).updateDatas(homeViewModel.articles)
        })

        homeViewModel.finishLoad.observe(this, Observer {
            rv_article.finishLoadMore()
            rv_article.finishRefresh()
        })

        homeViewModel.bannerList.observe(this, Observer {
            Log.d(tags, "bannerList 变化了")
            banner.initBanner(this, it)
        })

        homeViewModel.getHomeArticles()
        homeViewModel.getBanner()
    }

    private fun initListener() {
        rv_article.loadMore(OnLoadMoreListener {
            Log.d(tags, "load了")
            homeViewModel.getHomeArticles()
        })

        rv_article.loadRefresh(OnRefreshListener {
            Log.d(tags, "refresh了")
            homeViewModel.startPage = 0
            homeViewModel.getHomeArticles()

        })
    }


    private fun initRv() {
        banner = MyBanner(context!!)
        rv_article.setLayoutManager(LinearLayoutManager(context))

        val adapter = HomeArticleAdapter()
        adapter.addHeader(banner)
        rv_article.setAdapter(adapter)
    }

    /**
     * 点击了Banner的item
     */
    override fun onBannerClick(entity: BannerEntity) {
        val intent = Intent(context, WebActivity::class.java)
        intent.putExtra("pageUrl", entity.url)
        startActivity(intent)
    }

}