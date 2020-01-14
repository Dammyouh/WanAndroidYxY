package com.exam.home.wanandroid.customview

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.exam.home.wanandroid.R
import com.exam.home.wanandroid.ui.home.IndicatorEntity
import kotlinx.android.synthetic.main.banner_indicator.view.*
import kotlinx.android.synthetic.main.item_indicator.view.*

/**
 * Created by Yangxy on 2019-12-31
 * description --
 */
class BannerIndicatorView @JvmOverloads constructor(context: Context, attr: AttributeSet? = null, def: Int = 0) :
    RelativeLayout(context, attr, def) {

    val view: View = LayoutInflater.from(context).inflate(R.layout.banner_indicator, this, true)

    init {
        view.rv_indicator.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        view.rv_indicator.adapter = IndicatorAdapter()
        view.rv_indicator.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                super.getItemOffsets(outRect, view, parent, state)
                if (parent.getChildAdapterPosition(view) != 0) {//设置item间距
                    outRect.left = 20
                }
            }
        })
    }

    fun updateList(list: MutableList<IndicatorEntity>) {
        (view.rv_indicator.adapter as IndicatorAdapter).updateIndicator(list)
    }
}


class IndicatorAdapter : RecyclerView.Adapter<IndicatorAdapter.IndicatorHolder>() {

    private var list: MutableList<IndicatorEntity> = ArrayList()


    fun updateIndicator(list: MutableList<IndicatorEntity>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndicatorHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_indicator, parent, false)
        return IndicatorHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: IndicatorHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class IndicatorHolder(private var mView: View) : RecyclerView.ViewHolder(mView) {

        fun bind(entity: IndicatorEntity) {
            val drawable =
                if (entity.select) {
                    mView.context.resources.getDrawable(R.drawable.circle_red_indicator)
                } else {
                    mView.context.resources.getDrawable(R.drawable.circle_white_indicator)
                }

            itemView.iv_indicator.background = drawable
        }

    }
}