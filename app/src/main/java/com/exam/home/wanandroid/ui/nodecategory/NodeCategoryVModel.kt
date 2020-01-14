package com.exam.home.wanandroid.ui.nodecategory

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.exam.home.wanandroid.base.BaseRequest
import com.exam.home.wanandroid.base.RxBaseCallBack
import com.exam.home.wanandroid.ui.home.Datas
import com.exam.home.wanandroid.ui.home.HomeArticlesList
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class NodeCategoryVModel(application: Application) : AndroidViewModel(application) {

    private val tag = this::class.java
    var nodeList: MutableList<NodeListEntity> = ArrayList()
    val hasNodeList: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }

    var articles: MutableList<Datas> = ArrayList()
    val hasArticles: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    private var startPage = 0

    //是否已经到底
    var hasToEnd: Boolean = false

    /**
     * 获取知识体系
     */
    fun getNodeTree() {

        val nodeCall = BaseRequest.nodeRequest?.getNodeTree()
        nodeCall?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread()) //主线程进行数据更新
            ?.subscribe(object : RxBaseCallBack<NodeTreeEntity?>() {
                override fun onSuccess(t: NodeTreeEntity?) {
                    super.onSuccess(t)
                    if (t?.data?.isNullOrEmpty() == true) {
                        hasNodeList.value = false
                    } else {
                        nodeList = t?.data as MutableList<NodeListEntity>
                        hasNodeList.value = true
                    }

                }
            })

    }


    /**
     * 获取某个知识点的文章
     */
    fun getNodeArticle(cid: Int) {
        val call = BaseRequest.nodeRequest?.getArticles("$startPage", cid)
        call?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : RxBaseCallBack<HomeArticlesList>() {
                override fun onSuccess(t: HomeArticlesList) {
                    super.onSuccess(t)

                    if (startPage == 0) {
                        articles.clear()
                    }
                    startPage += 1

                    if (t.data.datas.isNullOrEmpty()) {
                        hasArticles.value = false
                    } else {
                        if (articles.isNullOrEmpty()) {
                            articles = t.data.datas as MutableList<Datas>
                        } else {
                            t.data.datas?.let { articles.addAll(it) }
                        }
                        hasArticles.value = true
                    }

                    if (t.data.total == articles.size) {
                        hasToEnd = true
                    }

                }
            })
    }
}

