package com.exam.home.wanandroid.utils

import android.content.Context
import com.exam.home.wanandroid.base.user_sp

/**
 * Created by Yangxy on 2019-12-27
 * description -- sp的封装类，防止每次写的时候都获取getSharedPreferences，edit,进行apply
 */
class PreferenceUtil private constructor(context: Context) {
    private var sp = context.getSharedPreferences(user_sp, Context.MODE_PRIVATE)


    companion object {

        @Volatile
        private var preferenceUtil: PreferenceUtil? = null

        fun getInstance(context: Context): PreferenceUtil {
            if (preferenceUtil == null) {
                synchronized(this) {
                    if (preferenceUtil == null) {
                        preferenceUtil = PreferenceUtil(context)
                    }
                }
            }
            return preferenceUtil!!
        }
    }


    fun getString(key: String): String {
        return sp.getString(key, "") ?: ""
    }

    fun putString(key: String, value: String) {
        save(key, value)
    }

    fun getInt(key: String): Int {
        return sp.getInt(key, 0)
    }

    fun putInt(key: String, value: Int) {
        save(key, value)
    }

    fun getLong(key: String): Long {
        return sp.getLong(key, 0L)
    }

    fun putLong(key: String, value: Long) {
        save(key, value)
    }


    fun getFloat(key: String): Float {
        return sp.getFloat(key, 0F)
    }

    fun putFloat(key: String, value: Float) {
        save(key, value)
    }


    fun getBoolean(key: String): Boolean {
        return sp.getBoolean(key, false)
    }

    fun putBoolean(key: String, value: Boolean) {
        save(key, value)
    }

    fun remove(key: String) {
        sp.edit().remove(key).apply()
    }

    private fun save(key: String, value: Any) {

        val edit = sp.edit()
        when (value) {
            is String -> edit.putString(key, value)
            is Int -> edit.putInt(key, value)
            is Long -> edit.putLong(key, value)
            is Float -> edit.putFloat(key, value)
            is Boolean -> edit.putBoolean(key, value)
        }
        edit.apply()
    }

}