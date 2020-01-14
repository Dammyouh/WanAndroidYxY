package com.exam.home.wanandroid.customview

import android.content.Context
import android.util.AttributeSet
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.exam.home.wanandroid.R
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import kotlinx.android.synthetic.main.pull_recyclerview.view.*

/**
 * Created by Yangxy on 2019-09-03
 *
 * 下拉刷新：loadRefresh
 *
 * 上拉加载：loadMore
 *
 * 预加载使用： onScroll监听
 */
class PullRecyclerView @JvmOverloads constructor(context: Context,
                                                 attr: AttributeSet? = null,
                                                 def: Int = 0) : FrameLayout(context, attr, def) {

    private val itemSps = SparseArray<ItemSpace?>(0)

    private var onScrollStateChanged: OnScrollStateChanged? = null
    private var onScrollListener: OnScroll? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.pull_recyclerview, this, true)
        rv.layoutManager = LinearLayoutManager(context)
        pull_view.setRefreshHeader(PullHeaderView(context))

        rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                onScrollStateChanged?.onScrollStateChanged(this@PullRecyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (onScrollListener == null) return
                val manager = recyclerView.layoutManager
                if (manager is LinearLayoutManager) {
                    val firstVisibleItem = manager.findFirstVisibleItemPosition()
                    val lastVisibleItem = manager.findLastVisibleItemPosition()
                    val visibleItemCount = manager.childCount
                    val totalItemCount = manager.itemCount
                    val firstView: View? = recyclerView.getChildAt(0)

                    var itemSpace: ItemSpace? = itemSps.get(firstVisibleItem)
                    if (itemSpace == null) {
                        itemSpace = ItemSpace(firstView?.height ?: 0, firstView?.top ?: 0)
                    }

                    itemSps.append(firstVisibleItem, itemSpace)
                    val scrollHeight = getScrollHeight(firstVisibleItem)
                    onScrollListener?.onScroll(this@PullRecyclerView, firstVisibleItem, lastVisibleItem, visibleItemCount, totalItemCount, scrollHeight)
                }
            }
        })
    }

    /**
     * 上拉加载
     */
    fun loadMore(listener: OnLoadMoreListener) {
        pull_view.setOnLoadMoreListener(listener)
    }

    /**
     * 关闭上拉加载
     */
    fun finishLoadMore() {
        pull_view.finishLoadMore()
    }

    /**
     * 下拉刷新
     */
    fun loadRefresh(listener: OnRefreshListener) {
        pull_view.setOnRefreshListener(listener)
    }

    /**
     * 关闭下拉刷新
     */
    fun finishRefresh() {
        pull_view.finishRefresh()
    }

    fun refreshView(): SmartRefreshLayout {
        return pull_view
    }

    fun recyclerView(): RecyclerView {
        return rv
    }

    fun setAdapter(adapter: RecyclerView.Adapter<*>?) {
        rv.adapter = adapter
    }

    fun setLayoutManager(layout: RecyclerView.LayoutManager?) {
        rv.layoutManager = layout
    }

    /**
     * 滑动状态监听
     */
    fun addOnScrollStateChanged(on: OnScrollStateChanged?) {
        this.onScrollStateChanged = on
    }

    /**
     * 滑动监听
     */
    fun addOnScroll(on: OnScroll?) {
        onScrollListener = on
    }

    /**
     * 精确计算ListView滚动距离
     *
     * @return
     */
    fun getScrollHeight(firstVisiblePosition: Int): Int {
        var height = 0
        //不包含 firstVisiblePosition
        for (i in 0 until firstVisiblePosition) {
            val itemSpace: ItemSpace? = itemSps.get(i)
            height += itemSpace?.height ?: 0
        }
        val itemSpace: ItemSpace? = itemSps.get(firstVisiblePosition)
        return height - (itemSpace?.top ?: 0)
    }

    /**
     * 用于计算的占位item
     */
    internal class ItemSpace(var height: Int, var top: Int)

    interface OnScrollStateChanged {
        fun onScrollStateChanged(view: PullRecyclerView, scrollState: Int)
    }

    interface OnScroll {
        fun onScroll(view: PullRecyclerView, firstVisibleItem: Int, lastVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int, scrollHeight: Int)
    }

}

