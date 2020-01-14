package com.exam.home.wanandroid.ui.mine

import com.exam.home.wanandroid.base.BaseCallBackBean
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by Yangxy on 2019-12-25
 * description -- 我的页面的相关请求
 */
interface IMineRequest {

    @POST("user/register")
    @FormUrlEncoded
    fun registerUser(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("repassword") repassword: String
    ): Call<BaseCallBackBean>


    @POST("user/login")
    @FormUrlEncoded
    fun loginUser(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<BaseCallBackBean>



}