package com.exam.home.wanandroid.base

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco

/**
 * Created by Yangxy on 2019-12-31
 * description --
 */
class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initTools()
    }

    private fun initTools() {
        initFresco()
    }

    private fun initFresco() {
        Fresco.initialize(this)
    }
}