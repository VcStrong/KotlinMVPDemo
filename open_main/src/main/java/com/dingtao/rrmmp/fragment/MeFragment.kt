package com.dingtao.rrmmp.fragment

import android.content.Intent
import android.net.Uri
import android.view.View

import com.dingtao.common.util.Constant
import com.dingtao.rrmmp.R
import com.dingtao.rrmmp.activity.SetActivity
import com.dingtao.rrmmp.adapter.CircleAdpater
import com.dingtao.common.core.WDFragment
import com.dingtao.common.core.db.DaoMaster
import com.dingtao.common.core.db.UserInfoDao
import com.dingtao.rrmmp.activity.MainActivity
import com.dingtao.rrmmp.presenter.CirclePresenter
import kotlinx.android.synthetic.main.frag_me.view.*

/**
 * @author dingtao
 * @date 2019/1/2 10:28
 * qq:1940870847
 */
class MeFragment : WDFragment() {

    private val mCircleAdapter: CircleAdpater? = null

    internal var circlePresenter: CirclePresenter? = null

    override val pageName: String = "我的Fragment"

    override val layoutId: Int = R.layout.frag_me

    override fun initView(view: View) {
        view.mMeBg.setImageURI(Uri.parse(LOGIN_USER.headPic))
        view.mMeAvatar.setImageURI(Uri.parse(LOGIN_USER.headPic))
        view.mMeName.text = LOGIN_USER.nickName
        view.mLogout.setOnClickListener { logout() }
        view.mMeSetting.setOnClickListener { setting() }
    }

    fun logout() {
        val userInfoDao = DaoMaster.newDevSession(activity, UserInfoDao.TABLENAME).userInfoDao
        userInfoDao.delete(LOGIN_USER)//删除用户

        intentByRouter(Constant.ACTIVITY_URL_LOGIN)
        activity!!.finish()//本页面关闭
    }

    fun setting() {
        startActivity(Intent(activity, SetActivity::class.java))
    }

}
