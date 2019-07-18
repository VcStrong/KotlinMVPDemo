package com.dingtao.common.core

import com.alibaba.android.arouter.launcher.ARouter
import com.dingtao.common.bean.UserInfo
import com.dingtao.common.core.db.DaoMaster
import com.dingtao.common.core.db.UserInfoDao
import com.google.gson.Gson

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.dingtao.common.util.LogUtils

abstract class WDFragment : Fragment() {
    var mGson = Gson()
    var mShare = WDApplication.share

    lateinit var LOGIN_USER: UserInfo

    //	@Override
    //	public void onResume() {
    //		super.onResume();
    //		if (!MTStringUtils.isEmpty(getPageName()))
    //			MobclickAgent.onPageStart(getPageName()); // 统计页面
    //	}
    //
    //	@Override
    //	public void onPause() {
    //		super.onPause();
    //		if (!MTStringUtils.isEmpty(getPageName()))
    //			MobclickAgent.onPageEnd(getPageName());// 统计页面
    //	}

    /**
     * 设置页面名字 用于友盟统计
     */
    abstract val pageName: String
    /**
     * 设置layoutId
     * @return
     */
    protected abstract val layoutId: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val userInfoDao = DaoMaster.newDevSession(activity!!, UserInfoDao.TABLENAME).userInfoDao
        val userInfos = userInfoDao.queryBuilder().where(UserInfoDao.Properties.Status.eq(1)).list()
        LOGIN_USER = userInfos[0]//读取第一项

        // 每次ViewPager要展示该页面时，均会调用该方法获取显示的View
        val time = System.currentTimeMillis()
        val view = inflater.inflate(layoutId, container, false)
        ARouter.getInstance().inject(this)
        initView(view)
        LogUtils.e(this.toString() + "页面加载使用：" + (System.currentTimeMillis() - time))
        return view
    }

    /**
     * 初始化视图
     */
    protected abstract fun initView(view:View)

    /**
     * @param mActivity 传送Activity的
     */
    fun intent(mActivity: Class<*>) {
        val intent = Intent(context, mActivity)
        startActivity(intent)
    }

    /**
     * @param path 传送Activity的
     */
    fun intentByRouter(path: String) {
        ARouter.getInstance().build(path)
                .navigation(context)
    }

    /**
     * @param mActivity 传送Activity的
     * @param bundle
     */
    fun intent(mActivity: Class<*>, bundle: Bundle) {
        val intent = Intent(context, mActivity)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    /**
     * @param path 传送Activity的
     * @param bundle
     */
    fun intentByRouter(path: String, bundle: Bundle) {
        ARouter.getInstance().build(path)
                .with(bundle)
                .navigation(context)
    }

}
