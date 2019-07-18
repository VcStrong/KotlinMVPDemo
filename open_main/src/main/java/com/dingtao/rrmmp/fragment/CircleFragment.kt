package com.dingtao.rrmmp.fragment

import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager

import com.dingtao.rrmmp.R
import com.dingtao.rrmmp.activity.AddCircleActivity
import com.dingtao.rrmmp.adapter.CircleAdpater
import com.dingtao.common.bean.Circle
import com.dingtao.common.core.DataCall
import com.dingtao.common.core.WDFragment
import com.dingtao.common.core.exception.ApiException
import com.dingtao.rrmmp.presenter.CirclePresenter
import com.dingtao.rrmmp.presenter.GreatPresenter
import com.dingtao.common.util.recyclerview.SpacingItemDecoration
import com.jcodecraeer.xrecyclerview.XRecyclerView

import com.dingtao.common.util.UIUtils
import kotlinx.android.synthetic.main.frag_circle.*
import kotlinx.android.synthetic.main.frag_circle.view.*

/**
 * @author dingtao
 * @date 2019/1/2 10:28
 * qq:1940870847
 */
class CircleFragment : WDFragment(), XRecyclerView.LoadingListener, CircleAdpater.GreatListener {

    private var mCircleAdapter: CircleAdpater? = null

    lateinit internal var circlePresenter: CirclePresenter

    override val pageName: String
        get() = "圈子Fragment"

    override val layoutId: Int = R.layout.frag_circle

    var mAddCircle: ImageView? = null
    var mCircleList:XRecyclerView? = null

    override fun initView(view: View) {
        mAddCircle = view.mAddCircle
        mAddCircle!!.setOnClickListener { addCircle() }

        mCircleAdapter = CircleAdpater(context!!)

        mCircleList = view.mCircleList
        mCircleList!!.layoutManager = GridLayoutManager(context, 1)

        val space = resources.getDimensionPixelSize(R.dimen.dip_20)

        mCircleList!!.addItemDecoration(SpacingItemDecoration(space))

        mCircleAdapter!!.setGreatListener(this)

        mCircleList!!.adapter = mCircleAdapter!!
        mCircleList!!.setLoadingListener(this)

        // 设置数据
        circlePresenter = CirclePresenter(CircleCall())

        mCircleList!!.refresh()
    }

    override fun onResume() {
        super.onResume()
        if (addCircle) {//publish new message,so you have to refresh
            addCircle = false
            mCircleList!!.refresh()
        }
    }

    override fun onRefresh() {
        if (circlePresenter.isRunning) {
            mCircleList!!.refreshComplete()
            return
        }
        circlePresenter.reqeust(true, LOGIN_USER.userId,
                LOGIN_USER.sessionId)
    }

    override fun onLoadMore() {
        if (circlePresenter.isRunning) {
            mCircleList!!.loadMoreComplete()
            return
        }
        circlePresenter.reqeust(false, LOGIN_USER.userId,
                LOGIN_USER.sessionId)
    }

    fun addCircle() {
        val intent = Intent(context, AddCircleActivity::class.java)
        startActivity(intent)
    }

    override fun great(position: Int, circle: Circle) {
        val greatPresenter = GreatPresenter(GreatCall())
        greatPresenter.reqeust(LOGIN_USER.userId.toString() + "", LOGIN_USER.sessionId, circle.id, position, circle)
    }

    /**
     * @author dingtao
     * @date 2019/1/3 9:23 AM
     * banner回调接口
     */
    inner class CircleCall : DataCall<List<Circle>> {

        override fun success(data: List<Circle>?, args:List<Any>) {
            mCircleList!!.refreshComplete()
            mCircleList!!.loadMoreComplete()
            //添加列表并刷新
            if (circlePresenter.page == 1) {
                mCircleAdapter!!.clear()
            }
            mCircleAdapter!!.addAll(data)
            mCircleAdapter!!.notifyDataSetChanged()
        }

        override fun fail(e: ApiException, args:List<Any>) {
            mCircleList!!.refreshComplete()
            mCircleList!!.loadMoreComplete()
        }
    }

    inner class GreatCall : DataCall<Any> {

        override fun success(data: Any?, args:List<Any>) {
            val position = args[3] as Int
            UIUtils.showToastSafe("点击" + position + "    adapter条目：" + mCircleAdapter!!.itemCount
                    + "    recycler条目：" + mCircleList!!.childCount)
            val circle = args[4] as Circle
            val nowCircle = mCircleAdapter!!.getItem(position)
            if (nowCircle.id == circle.id) {
                nowCircle.greatNum = nowCircle.greatNum + 1
                nowCircle.whetherGreat = 1
                mCircleAdapter!!.notifyItemChanged(position + 1, 0)
            }
        }

        override fun fail(e: ApiException, args:List<Any>) {

        }
    }

    companion object {

        var addCircle: Boolean = false//如果添加成功，则为true
    }

}
