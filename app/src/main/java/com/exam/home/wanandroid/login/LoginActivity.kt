package com.exam.home.wanandroid.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.exam.home.wanandroid.HomeActivity
import com.exam.home.wanandroid.R
import com.exam.home.wanandroid.utils.AccountUtils
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var loginVModel: LoginVModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initVModel()
        initListener()
    }

    private fun initVModel() {
        loginVModel = ViewModelProviders.of(this).get(LoginVModel::class.java)
    }

    private fun initListener() {
        text_notifications.setOnClickListener {
            loginVModel.registerUser()
        }

        btn_login.setOnClickListener {
            loginVModel.loginUser(
                et_user_name.text?.trim()?.toString() ?: return@setOnClickListener,
                et_pwd.text?.trim()?.toString() ?: return@setOnClickListener
            )
        }

        loginVModel.registerSuccess.observe(this, Observer {
            val tip = if (it == true) "注册成功" else "您已注册"
            Toast.makeText(this, tip, Toast.LENGTH_SHORT).show()
        })

        loginVModel.loginSuccess.observe(this, Observer {
            val tip = if (it == true) "登录成功" else "账号密码错误，请重新登录"
            Toast.makeText(this, tip, Toast.LENGTH_SHORT).show()
            AccountUtils.saveLoginStatus(this, it == true)
            if (it == true) {
                startActivity(Intent(this, HomeActivity::class.java))
            }
        })
    }
}
