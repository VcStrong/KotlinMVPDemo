package com.dingtao.rrmmp.activity

import android.os.Handler
import android.os.Message

import com.alibaba.android.arouter.facade.annotation.Route
import com.dingtao.common.util.Constant
import com.dingtao.rrmmp.R
import com.dingtao.common.core.WDActivity

import kotlinx.android.synthetic.main.activity_welcome.*

/**
 * @author dingtao
 * @date 2018/12/29 16:29
 * qq:1940870847
 */
@Route(path = Constant.ACTIVITY_URL_WELCOME)
class WelcomeActivity : WDActivity() {

    private var count = 3

    private val handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            count--
            seekText.text = "跳过" + count + "s"
            if (count == 0) {
                if (LOGIN_USER != null) {
                    intentByRouter(Constant.ACTIVITY_URL_MAIN)//跳转到主页面
                } else {
                    intentByRouter(Constant.ACTIVITY_URL_LOGIN)//跳转到登录页
                }
                finish()
            } else {//消息不能复用，必须新建
                this.sendEmptyMessageDelayed(1, 1000)
            }
        }
    }

    override val layoutId: Int = R.layout.activity_welcome

    override fun initView() {
        seekText.text = "跳过" + count + "s"
        seekText.setOnClickListener{seek()}
        handler.sendEmptyMessageDelayed(1, 1000)
    }

    fun seek() {
        handler.removeMessages(1)
        if (LOGIN_USER != null) {
            intentByRouter(Constant.ACTIVITY_URL_MAIN)
        } else {
            intentByRouter(Constant.ACTIVITY_URL_LOGIN)
        }
        finish()
    }

    override fun destoryData() {

    }
}
