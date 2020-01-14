package com.exam.home.wanandroid.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.exam.home.wanandroid.base.BaseCallBack
import com.exam.home.wanandroid.base.BaseRequest

class HomeViewModel : ViewModel() {

    private val tag = this::class.java.simpleName


    var articles: MutableList<Datas> = ArrayList()

    val finishLoad: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }

    val articleSize: MutableLiveData<Int> by lazy { MutableLiveData<Int>() }

    val bannerList: MutableLiveData<List<BannerEntity>> by lazy { MutableLiveData<List<BannerEntity>>() }

    var startPage = 0

    fun getHomeArticles() {
        val call = BaseRequest.homeRequest?.getArticles("$startPage")
        call?.enqueue(object : BaseCallBack<HomeArticlesList>() {

            override fun onSuccess(response: HomeArticlesList?) {
                super.onSuccess(response)
                Log.d(tag, "response = $response")
                if (startPage == 0) {
                    articles.clear()
                }

                startPage += 1

                finishLoad.value = true
                if (articles.isNullOrEmpty()) {
                    articles = response?.data?.datas as MutableList<Datas>
                } else {
                    articles.addAll(response?.data?.datas as MutableList<Datas>)
                }
                articleSize.value = articles.size
            }

            override fun onFailed() {
                super.onFailed()
                Log.d("yxy", "onFailed ")
                finishLoad.value = true
            }
        })

    }

    fun getBanner() {
        val call = BaseRequest.homeRequest?.getBanner()
        call?.enqueue(object : BaseCallBack<BannerList>() {
            override fun onSuccess(response: BannerList?) {
                super.onSuccess(response)
                Log.d(tag, "response  getBanner = $response")
                if (response?.data.isNullOrEmpty()) return
                bannerList.value = response!!.data
            }
        })

    }
}