package com.exam.home.wanandroid.customview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat

/**
 * Created by Yangxy on 2020-01-09
 * description --
 */
class MyBehaviour(var context: Context, var attr: AttributeSet? = null) :
    CoordinatorLayout.Behavior<View>(context, attr) {

    override fun layoutDependsOn(parent: CoordinatorLayout, child: View, dependency: View): Boolean {

        return dependency is TextView
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
        val offset = dependency.top - child.top
        ViewCompat.offsetTopAndBottom(child, offset)
        return true
    }
}