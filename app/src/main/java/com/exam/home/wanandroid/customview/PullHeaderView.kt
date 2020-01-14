package com.exam.home.wanandroid.customview

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import androidx.annotation.NonNull
import com.exam.home.wanandroid.R
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.constant.RefreshState
import com.scwang.smartrefresh.layout.internal.InternalAbstract
import kotlinx.android.synthetic.main.layout_pull_header.view.*

/**
 * Created by Yangxy on 2019-09-04
 * description -- 下拉刷新的实际HeaderView
 */
class PullHeaderView @JvmOverloads constructor(context: Context, attr: AttributeSet? = null, def: Int = 0) :
    InternalAbstract(context, attr, def), RefreshHeader {

    private var animation: AnimationDrawable? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_pull_header, this, true)
        val bgDrawable = pull_to_refresh_image.background
        if (bgDrawable is AnimationDrawable) {
            animation = bgDrawable
        }
    }

    override fun onStateChanged(@NonNull refreshLayout: RefreshLayout, @NonNull oldState: RefreshState, @NonNull newState: RefreshState) {
        Log.d("yxy", "state = $newState")
        if (newState == RefreshState.PullDownToRefresh) {
            //下拉过程
            if (animation?.isRunning == true) return
            animation?.start()

        } else if (newState == RefreshState.None) {
            animation?.stop()
        }
    }
}