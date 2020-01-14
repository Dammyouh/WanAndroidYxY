package com.exam.home.wanandroid.utils

import android.content.Context
import android.util.Log

/**
 * Created by Yangxy on 2019-12-31
 * description --
 */
class Util {


    companion object {
        private val tag: String = this::class.java.simpleName

        fun getScreenWidth(context: Context): Int {
            val displayMetrics = context.resources.displayMetrics
            val density = displayMetrics.density
            Log.d(tag, "density = $density  + widthPixels = ${displayMetrics.widthPixels}")
            return displayMetrics.widthPixels
        }
    }
}