package com.exam.home.wanandroid.customview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.util.AttributeSet
import android.widget.TextView
import com.exam.home.wanandroid.R

/**
 * Created by Yangxy on 2019-12-25
 * description -- 渐变文字
 */
class GradientTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    def: Int = 0
) : TextView(context, attrs, def) {
    private var startColor: Int = 0
    private var endColor: Int = 0
    private var gradientMode: String? = null

    init {
        if (attrs != null) {
            val attr = context.obtainStyledAttributes(attrs, R.styleable.GradientTextColor, def, 0)
            startColor = attr.getColor(R.styleable.GradientTextColor_startColor, Color.parseColor("#f5f5f5"))
            endColor = attr.getColor(R.styleable.GradientTextColor_endColor, Color.parseColor("#ff7767"))
            gradientMode = attr.getString(R.styleable.GradientTextColor_gradientMode)
                ?: "horizontal"
            attr.recycle()
        }
    }

    @SuppressLint("DrawAllocation")
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (changed) {
            if (gradientMode == "vertical") {
                paint.shader = LinearGradient(
                    0f, 0f, 0f, height.toFloat(),
                    startColor,
                    endColor,
                    Shader.TileMode.CLAMP
                )
            } else {
                paint.shader = LinearGradient(
                    0f, 0f, width.toFloat(), 0f,
                    startColor,
                    endColor,
                    Shader.TileMode.CLAMP
                )
            }
        }
    }
}