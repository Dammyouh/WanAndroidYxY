package com.exam.home.wanandroid.base

import androidx.fragment.app.Fragment

/**
 * Created by Yangxy on 2020-01-13
 * description --
 */
abstract class LazyFragment : Fragment() {

    private var isFirstLoad = true


    override fun onResume() {
        super.onResume()
        if (isFirstLoad) {
            isFirstLoad = false
            lazyLoad()
        }
    }

    abstract fun lazyLoad()
}