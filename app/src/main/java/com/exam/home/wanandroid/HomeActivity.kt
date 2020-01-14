package com.exam.home.wanandroid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private val tag = HomeActivity::class.java.simpleName


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initVp2()
        initListener()
    }

    private fun initVp2() {
        vp_home.adapter = HomePageAdapter(this)
        vp_home.isUserInputEnabled = false
        //不设置的话会默认0，为懒加载
        vp_home.offscreenPageLimit = 1
    }

    private fun initListener() {

        nav_view.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> vp_home.setCurrentItem(0, false)
                R.id.navigation_dashboard -> vp_home.setCurrentItem(1, false)
                R.id.navigation_notifications -> vp_home.setCurrentItem(2, false)
                else -> vp_home.setCurrentItem(0, false)
            }
            true
        }

    }
}
