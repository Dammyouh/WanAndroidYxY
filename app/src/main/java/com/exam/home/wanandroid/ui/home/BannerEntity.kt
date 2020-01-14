package com.exam.home.wanandroid.ui.home

/**
 * Created by Yangxy on 2019-12-31
 * description -- 首页的banner实现
 */

data class BannerList(
    var data: List<BannerEntity>,
    var errorCode: Int,
    var errorMsg: String
)

data class BannerEntity(
    var desc: String,
    var id: Int,
    var imagePath: String,
    var isVisible: Int,
    var order: Int,
    var title: String,
    var type: Int,
    var url: String
)