package com.exam.home.wanandroid

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.exam.home.wanandroid.ui.nodecategory.NodeCategoryFragment
import com.exam.home.wanandroid.ui.home.HomeFragment
import com.exam.home.wanandroid.ui.mine.MineFragment

/**
 * Created by Yangxy on 2019-12-25
 * description --
 */
class HomePageAdapter(fm: FragmentActivity) : FragmentStateAdapter(fm) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
                return when (position) {
            0 -> HomeFragment()
            1 -> NodeCategoryFragment()
            2 -> MineFragment()
            else -> HomeFragment()
        }
    }
}