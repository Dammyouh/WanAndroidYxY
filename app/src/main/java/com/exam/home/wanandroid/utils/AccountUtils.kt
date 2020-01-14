package com.exam.home.wanandroid.utils

import android.content.Context
import com.exam.home.wanandroid.base.isLogin

/**
 * Created by Yangxy on 2019-12-27
 * description --
 */
class AccountUtils {

    companion object {

        fun saveLoginStatus(context: Context, loginSuccess: Boolean) {
            val preference = PreferenceUtil.getInstance(context)
            preference.putBoolean(isLogin, loginSuccess)
        }

        fun getLoginStatus(context: Context): Boolean {
            return PreferenceUtil.getInstance(context).getBoolean(isLogin)
        }
    }
}