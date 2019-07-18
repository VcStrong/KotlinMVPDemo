package com.dingtao.common.util.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator

/**
 * @author dingtao
 * @date 2019/1/16 11:35
 * qq:1940870847
 */
class MyView : View, ValueAnimator.AnimatorUpdateListener {

    private var valueAnimator: ValueAnimator? = null
    private var radius: Int = 0//半径

    private var mPaint: Paint? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        valueAnimator = ValueAnimator.ofInt(0, 200)
        valueAnimator!!.duration = 2000
        valueAnimator!!.interpolator = LinearInterpolator()
        valueAnimator!!.addUpdateListener(this)

        mPaint = Paint()
        mPaint!!.style = Paint.Style.STROKE
        mPaint!!.strokeWidth = 2f
        mPaint!!.color = Color.BLACK
    }

    override fun onAnimationUpdate(animation: ValueAnimator) {
        radius = animation.animatedValue as Int
        invalidate()
    }

    fun startDraw() {
        valueAnimator!!.start()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mPaint!!.alpha = 255 - (radius * (255.0 / 200)).toInt()
        canvas.drawCircle((width / 2).toFloat(), (width / 2).toFloat(), radius.toFloat(), mPaint!!)
        canvas.drawCircle((width / 2).toFloat(), (width / 2).toFloat(), (radius * (150.0 / 200)).toInt().toFloat(), mPaint!!)
    }
}
