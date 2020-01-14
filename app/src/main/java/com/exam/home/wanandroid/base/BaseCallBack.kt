package com.exam.home.wanandroid.base

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Yangxy on 2019-12-27
 * description --
 */
open class BaseCallBack<T> : Callback<T> {

    override fun onFailure(call: Call<T>?, t: Throwable?) {
        onFailed()
    }


    override fun onResponse(call: Call<T>?, response: Response<T>?) {
        onSuccess(response?.body())
    }

    /**
     * 失败回调
     */
    open fun onFailed() {}

    /**
     * 接口请求成功回调
     */
    open fun onSuccess(response: T?) {

    }
}