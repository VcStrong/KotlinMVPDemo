package com.dingtao.rrmmp.activity

import android.content.Intent

import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.dingtao.common.util.Constant
import com.dingtao.rrmmp.R
import com.dingtao.common.core.WDActivity
import com.dingtao.common.core.db.DaoMaster
import com.dingtao.common.core.db.UserInfoDao

import kotlinx.android.synthetic.main.activity_set.*

/**
 * @author dingtao
 * @date 2019/1/8 14:22
 * qq:1940870847
 */
@Route(path = Constant.ACTIVITY_URL_SET)
class SetActivity : WDActivity() {
    override val layoutId: Int = R.layout.activity_set

    override fun initView() {
        logout_btn.setOnClickListener { logout() }
    }

    override fun destoryData() {

    }

    fun logout() {
        val userInfoDao = DaoMaster.newDevSession(this, UserInfoDao.TABLENAME).userInfoDao
        userInfoDao.delete(LOGIN_USER)
        //Intent清除栈FLAG_ACTIVITY_CLEAR_TASK会把当前栈内所有Activity清空；
        //FLAG_ACTIVITY_NEW_TASK配合使用，才能完成跳转
        ARouter.getInstance().build(Constant.ACTIVITY_URL_LOGIN)
                .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                .navigation()
    }
}
