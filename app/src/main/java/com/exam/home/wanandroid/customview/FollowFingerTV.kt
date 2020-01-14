package com.exam.home.wanandroid.customview

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.TextView

/**
 * Created by Yangxy on 2020-01-10
 * description --
 */
class FollowFingerTV @JvmOverloads constructor(
    var mContext: Context,
    var attr: AttributeSet? = null,
    var def: Int = 0
) : TextView(mContext, attr, def) {

    private var lastX: Float = 0f
    private var lastY: Float = 0f


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.d("yxy", "onTouchEvent = ${super.onTouchEvent(event)}")
        if (event == null) return super.onTouchEvent(event)

        val x = event.rawX
        val y = event.rawY
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
            }

            MotionEvent.ACTION_MOVE -> {
                val deltaX = x.minus(lastX).toInt()
                val deltaY = y.minus(lastY).toInt()
                //动画方式,不能改变View真正位置
//                val tranx = translationX + deltaX
//                val tranY = translationY + deltaY
//                translationX = tranx
//                translationY = tranY

                //layout方式，能改变View的真正位置
//                layout(left + deltaX, top + deltaY, right + deltaX, bottom + deltaY)

                //改变View的真正位置
                offsetLeftAndRight(deltaX)
                offsetTopAndBottom(deltaY)

            }

            MotionEvent.ACTION_UP -> {
            }
        }
        lastX = x
        lastY = y
        return true
    }
}