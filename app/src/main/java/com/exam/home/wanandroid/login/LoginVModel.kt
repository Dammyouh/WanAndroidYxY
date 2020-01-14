package com.exam.home.wanandroid.login

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.exam.home.wanandroid.base.BaseCallBack
import com.exam.home.wanandroid.base.BaseCallBackBean
import com.exam.home.wanandroid.base.BaseRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val TAG = "LoginVModel"

class LoginVModel(application: Application) : AndroidViewModel(application) {

    val registerSuccess: MutableLiveData<Boolean> = MutableLiveData()

    val loginSuccess: MutableLiveData<Boolean> = MutableLiveData()

    fun registerUser() {
        //对 发送请求 进行封装
        val call = BaseRequest.request?.registerUser("yangxiaoyu123", "123456", "123456")
        call?.enqueue(object : Callback<BaseCallBackBean> {
            override fun onFailure(call: Call<BaseCallBackBean>?, t: Throwable?) {
                Log.d("yxy", "注册接口请求失败")
                registerSuccess.value = false
            }

            override fun onResponse(call: Call<BaseCallBackBean>?, response:
            Response<BaseCallBackBean>?) {
                Log.d("yxy", "注册接口请求成功 call = $call ,response =  ${response?.body()} ")
                val body: BaseCallBackBean? = response?.body()
                val code = body?.errorCode
                registerSuccess.value = code != -1
            }
        })
    }

    fun loginUser(userName: String, pwd: String) {
        Log.d(TAG, "userName = $userName pwd = $pwd")
        val call = BaseRequest.request?.loginUser(userName, pwd)
        call?.enqueue(object : BaseCallBack<BaseCallBackBean>() {
            override fun onSuccess(response: BaseCallBackBean?) {
                super.onSuccess(response)
                Log.d("yxy", "登录接口请求成功 response =  $response ")
                val code = response?.errorCode
                loginSuccess.value = code != -1
            }
        })

    }

}