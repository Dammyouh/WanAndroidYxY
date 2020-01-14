package com.exam.home.wanandroid.ui.nodecategory

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.exam.home.wanandroid.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_node_detail.*

class NodeDetailActivity : AppCompatActivity() {

    private lateinit var vModel: NodeCategoryVModel
    private var nodeList: ArrayList<NodeListEntity> = ArrayList()
    private var nodeTitle: String? = null

    companion object {
        fun goDetailActivity(context: Context, nodeList: ArrayList<NodeListEntity>, nodeTitle: String): Intent {
            val intent = Intent()
            intent.putParcelableArrayListExtra("nodeList", nodeList)
            intent.putExtra("nodeTitle", nodeTitle)
            intent.setClass(context, NodeDetailActivity::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_node_detail)
        vModel = ViewModelProviders.of(this)[NodeCategoryVModel::class.java]
        getBundleData()
        initView()
    }

    private fun getBundleData() {
        nodeList = intent?.getParcelableArrayListExtra("nodeList") ?: ArrayList()
        nodeTitle = intent?.getStringExtra("nodeTitle") ?: ""
    }

    private fun initView() {
        bar_title.text = nodeTitle
        vp_node.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return nodeList.size
            }

            override fun createFragment(position: Int): Fragment {
                val fragment = ArticleFragment()
                val bundle = bundleOf("cid" to nodeList[position].id)
                fragment.arguments = bundle
                return fragment
            }
        }
        vp_node.offscreenPageLimit = nodeList.size

        //建立关联
        TabLayoutMediator(tabLayout, vp_node) { tab, position ->
            tab.text = nodeList[position].name
        }.attach()

        //以下监听保证点击Tab时，直接划到其他页面
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                vp_node.setCurrentItem(tab?.position ?: 0, false)
            }

        })


    }

}
