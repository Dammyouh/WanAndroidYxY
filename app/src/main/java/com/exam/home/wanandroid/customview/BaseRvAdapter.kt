package com.exam.home.wanandroid.customview


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Yangxy on 2020-01-14
 * description -- 最多添加Header16个,使用BaseRvAdapter时，自定义的ViewType需要为正数
 */
abstract class BaseRvAdapter<VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    companion object {
        const val NORMAL = 1

        /*二进制1111=15*/
        const val HEADER = 0
        /*二进制11111=31*/
        const val FOOTER = -16
    }

    private var headList: MutableList<View> = ArrayList()
    private var footList: MutableList<View> = ArrayList()


    fun addHeader(headView: View) {
        headList.add(headView)
        notifyDataSetChanged()
    }

    fun addFoot(footView: View) {
        footList.add(footView)
        notifyDataSetChanged()
    }

    fun _getItemViewType(position: Int): Int {
        return NORMAL
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            position < headList.size -> HEADER - position
            position > headList.size + _getItemCount() -> FOOTER - position
            else -> NORMAL
        }
    }


    abstract fun _getItemCount(): Int

    override fun getItemCount(): Int {
        return headList.size + _getItemCount() + footList.size
    }

    abstract fun _onCreateViewHolder(parent: ViewGroup, viewType: Int): VH

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType <= 0 && viewType >= -16) {
            HeadHolder(headList[HEADER - viewType])
        } else if (viewType < -16) {
            FootHolder(footList[FOOTER - viewType])
        } else {
            _onCreateViewHolder(parent, viewType)
        }

    }

    abstract fun _onBindViewHolder(holder: VH, position: Int)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position < headList.size) {
            return
        } else if (position > headList.size + _getItemCount()) {
            return
        }

        val realPosition = position - headList.size
        _onBindViewHolder(holder as VH, realPosition)
    }

    inner class HeadHolder(view: View) : RecyclerView.ViewHolder(view)
    inner class FootHolder(view: View) : RecyclerView.ViewHolder(view)
}
