package com.exam.home.wanandroid.ui.home

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Yangxy on 2019-12-30
 * description -- 首页相关的网络请求
 */
interface IHomeRequest {

    @GET("article/list/{page}/json")
    fun getArticles(@Path("page") page: String): Call<HomeArticlesList>

    @GET("banner/json")
    fun getBanner(): Call<BannerList>
}