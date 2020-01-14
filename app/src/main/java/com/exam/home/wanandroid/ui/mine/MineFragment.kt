package com.exam.home.wanandroid.ui.mine

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.exam.home.wanandroid.R
import com.exam.home.wanandroid.LaunchActivity
import com.exam.home.wanandroid.base.singleClick
import com.exam.home.wanandroid.login.LoginActivity
import com.exam.home.wanandroid.utils.AccountUtils
import kotlinx.android.synthetic.main.fragment_mine.*

class MineFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_mine, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initListener()
        initView()
    }

    private fun initListener() {
        btn_login.singleClick(5000) {
            if (!AccountUtils.getLoginStatus(context!!)) {
                val intent = Intent(context, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            } else {
//                Toast.makeText(context, "您已登录", Toast.LENGTH_SHORT).show()
                startActivity(Intent(context, LaunchActivity::class.java))
            }
        }
    }

    private fun initView() {
        val btnText = if (AccountUtils.getLoginStatus(context!!)) "欢迎来到WanAndroidYXY" else "暂未登录"
        btn_login.text = btnText
    }


}