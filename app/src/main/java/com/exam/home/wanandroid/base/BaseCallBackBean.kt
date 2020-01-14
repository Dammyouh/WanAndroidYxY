package com.exam.home.wanandroid.base

import org.json.JSONObject


/**
 * Created by Yangxy on 2019-12-25
 * description --
 */
data class BaseCallBackBean(
    var data: JSONObject,
    var errorCode: Int,
    var errorMsg: String
)
