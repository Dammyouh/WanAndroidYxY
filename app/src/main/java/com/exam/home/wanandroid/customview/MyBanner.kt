package com.exam.home.wanandroid.customview

import android.content.Context
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.exam.home.wanandroid.R
import com.exam.home.wanandroid.base.logd
import com.exam.home.wanandroid.ui.home.BannerEntity
import com.exam.home.wanandroid.ui.home.IndicatorEntity
import kotlinx.android.synthetic.main.banner_view.view.*
import kotlinx.android.synthetic.main.item_banner.view.*
import java.lang.ref.WeakReference

/**
 * Created by Yangxy on 2020-01-07
 * description --
 */
class MyBanner @JvmOverloads constructor(context: Context, attr: AttributeSet? = null, def: Int = 0) :
    RelativeLayout(context, attr, def) {

    private val tags = this::class.java.simpleName

    private val indicatorList: MutableList<IndicatorEntity> = ArrayList()
    private val updateBanner: Int = -1

    private lateinit var mHandler: Handler

    init {
        LayoutInflater.from(context).inflate(R.layout.banner_view, this, true)
    }

    override fun onWindowVisibilityChanged(visibility: Int) {
        super.onWindowVisibilityChanged(visibility)
        if (!::mHandler.isInitialized) return
        if (visibility == View.VISIBLE) {
            //需要重新计时
            sendScrollMsg()
        } else {
            stopScrollMsg()
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val action = ev?.action
        Log.d(tags, "onTouchEvent = $action")
        when (action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                stopScrollMsg()
            }

            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                sendScrollMsg()
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    fun initBanner(listener: BannerClick, bannerList: List<BannerEntity>) {

        home_banner.adapter = object : RecyclerView.Adapter<BannerHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerHolder {
                val mView = LayoutInflater.from(context).inflate(R.layout.item_banner, parent, false)
                return BannerHolder(mView)
            }

            override fun getItemCount(): Int {
                return bannerList.size
            }

            override fun onBindViewHolder(holder: BannerHolder, position: Int) {
                val banner = bannerList[position]
                holder.itemView.sd_banner.setImageURI(banner.imagePath)
                holder.itemView.setOnClickListener {
                    listener.onBannerClick(bannerList[position])
                }
            }
        }

        mHandler = BannerHandler(home_banner, bannerList.size)
        home_banner.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                logd("MyBanner onPageSelected + $position")
                indicatorList.map {
                    it.select = it.id == bannerList[position].id
                }
                indicator.updateList(indicatorList)
                sendScrollMsg()
            }

        })


        bannerList.withIndex().forEach {
            if (it.index == 0) {
                indicatorList.add(IndicatorEntity(it.value.id, true))
            } else {
                indicatorList.add(IndicatorEntity(it.value.id, false))
            }
        }
        indicator.updateList(indicatorList)
    }


    private fun sendScrollMsg() {
        mHandler.removeCallbacksAndMessages(null)
        val msg = mHandler.obtainMessage()
        msg.what = updateBanner
        mHandler.sendMessageDelayed(msg, 2000)
    }

    private fun stopScrollMsg() {
        mHandler.removeCallbacksAndMessages(null)
    }


    inner class BannerHolder(mView: View) : RecyclerView.ViewHolder(mView)

    companion object {

        class BannerHandler(private var viewPager2: ViewPager2, private var bannerSize: Int) : Handler() {
            private var weakBanner: WeakReference<ViewPager2> = WeakReference(viewPager2)

            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                val banner = weakBanner.get()
                if (msg.what == -1) {
                    if (banner?.currentItem?.plus(1) ?: 0 >= bannerSize) {
                        banner?.currentItem = 0
                    } else {
                        banner?.currentItem = viewPager2.currentItem + 1
                    }
                }
            }
        }
    }

    interface BannerClick {
        fun onBannerClick(entity: BannerEntity)
    }
}