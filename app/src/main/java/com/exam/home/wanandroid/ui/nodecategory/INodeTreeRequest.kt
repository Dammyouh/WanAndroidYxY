package com.exam.home.wanandroid.ui.nodecategory

import com.exam.home.wanandroid.ui.home.HomeArticlesList
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Yangxy on 2020-01-07
 * description -- 知识体系页面的接口请求汇总
 */
interface INodeTreeRequest {

    @GET("tree/json")
    fun getNodeTree(): Observable<NodeTreeEntity>


    @GET("article/list/{page}/json")
    fun getArticles(@Path("page") page: String, @Query("cid") cid: Int): Observable<HomeArticlesList>
}
