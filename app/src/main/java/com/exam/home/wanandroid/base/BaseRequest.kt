package com.exam.home.wanandroid.base

import com.exam.home.wanandroid.ui.home.IHomeRequest
import com.exam.home.wanandroid.ui.mine.IMineRequest
import com.exam.home.wanandroid.ui.nodecategory.INodeTreeRequest
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Yangxy on 2019-12-25
 * description --
 */
object BaseRequest {

    private val retrofit: Retrofit? = Retrofit.Builder().baseUrl("https://www.wanandroid.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    /**
     * 我的页面的网络请求
     */
    val request = retrofit?.create(IMineRequest::class.java)

    /**
     * 首页相关的网络请求
     */
    val homeRequest = retrofit?.create(IHomeRequest::class.java)

    /**
     * 知识体系的网络请求
     */
    val nodeRequest = retrofit?.create(INodeTreeRequest::class.java)
}