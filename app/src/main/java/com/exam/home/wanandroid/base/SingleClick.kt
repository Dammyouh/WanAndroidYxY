package com.exam.home.wanandroid.base

import android.util.Log
import android.view.View
import android.widget.Checkable

/**
 * Created by Yangxy on 2020-01-08
 * description -- 防止View重复点击
 */
var <T : View> T.lastClickTime: Long
    set(value) = setTag(1766613352, value)
    get() = getTag(1766613352) as? Long ?: 0

inline fun <T : View> T.singleClick(time: Long = 800, crossinline block: (T) -> Unit) {
    setOnClickListener {
        val currentTimeMillis = System.currentTimeMillis()
        if (currentTimeMillis - lastClickTime > time || this is Checkable) {
            lastClickTime = currentTimeMillis
            block(this)
        }
    }
}


fun logd(content: String) {
    Log.d("yxy", content)
}