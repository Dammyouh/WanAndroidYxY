package com.exam.home.wanandroid

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_test.*

class LaunchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        depentent.setOnClickListener {
            //            Toast.makeText(this, "我是红色View", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, HomeActivity::class.java))
        }

        view.setOnClickListener {
            Toast.makeText(this, "我是灰色View", Toast.LENGTH_SHORT).show()
        }
    }
}
