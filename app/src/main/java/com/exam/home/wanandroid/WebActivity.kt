package com.exam.home.wanandroid

import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.exam.home.wanandroid.utils.Util
import kotlinx.android.synthetic.main.activity_web.*

class WebActivity : AppCompatActivity() {


    private var pageUrl: String? = null
    private var screenWidth: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        getBundle()
        initView()
    }

    private fun getBundle() {
        pageUrl = intent.getStringExtra("pageUrl")
    }

    private fun initView() {
        screenWidth = Util.getScreenWidth(this)
        iv_back.setOnClickListener {
            onBackPressed()
        }

        initWebView()

        wv_content.loadUrl(pageUrl)

    }

    private fun initWebView() {
        val webSettings = wv_content.settings
        webSettings.javaScriptEnabled = true
//        webSettings.loadsImagesAutomatically = true
//        webSettings.defaultTextEncodingName = "utf-8"
        webSettings.domStorageEnabled = true //开启本地DOM存储,解决加载一些链接出现空白页面的现象
        webSettings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK


        webSettings.setSupportZoom(true) //支持缩放，默认为true。是下面那个的前提。
        webSettings.builtInZoomControls = true //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.displayZoomControls = true //是否展示原生的缩放控件

        wv_content.webViewClient = object : WebViewClient() {
            //打开网页时不会吊起浏览器，在本地的webView打开
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }
        }

        wv_content.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                val lp = divider.layoutParams
                lp.width = screenWidth * newProgress / 100
                divider.layoutParams = lp
                divider.visibility = if (newProgress == 100) View.GONE else View.VISIBLE
            }
        }
    }

    //否则点击返回键将关闭WebActivity
    override fun onBackPressed() {
        if (wv_content.canGoBack()) {
            wv_content.goBack()
            return
        }
        super.onBackPressed()
    }

    override fun onDestroy() {
        wv_content.loadDataWithBaseURL(null, "", "text/html", "utf-8", null)
        wv_content.clearHistory()
        wv_content.destroy()
        super.onDestroy()
    }
}
