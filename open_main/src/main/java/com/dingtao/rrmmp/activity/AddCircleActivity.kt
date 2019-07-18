package com.dingtao.rrmmp.activity

import android.app.Activity
import android.content.Intent
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.EditText

import com.alibaba.android.arouter.facade.annotation.Route
import com.dingtao.common.util.Constant
import com.dingtao.rrmmp.R
import com.dingtao.rrmmp.adapter.ImageAdapter
import com.dingtao.common.core.DataCall
import com.dingtao.common.core.WDActivity
import com.dingtao.common.core.exception.ApiException
import com.dingtao.rrmmp.fragment.CircleFragment
import com.dingtao.rrmmp.presenter.PublishCirclePresenter

import com.dingtao.common.util.StringUtils
import com.dingtao.common.util.UIUtils
import kotlinx.android.synthetic.main.activity_add_circle.*

/**
 * @author dingtao
 * @date 2019/1/11 00:22
 * qq:1940870847
 */
@Route(path = Constant.ACTIVITY_URL_ADD_CIRCLE)
class AddCircleActivity : WDActivity() {

    lateinit var presenter: PublishCirclePresenter

    internal lateinit var mImageAdapter: ImageAdapter

    override val layoutId: Int = R.layout.activity_add_circle

    override fun initView() {
        mImageAdapter = ImageAdapter()
        mImageAdapter.setSign(1)
        mImageAdapter.add(R.drawable.mask_01)
        mImageList!!.layoutManager = GridLayoutManager(this, 3)
        mImageList!!.adapter = mImageAdapter
        back.setOnClickListener { finish() }
        send.setOnClickListener { publish() }

        presenter = PublishCirclePresenter(SendCircleCall())
    }

    override fun destoryData() {

    }

    fun publish() {
        presenter.reqeust(LOGIN_USER.userId,
                LOGIN_USER.sessionId,
                1, mText!!.text.toString(), mImageAdapter.list)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {//resultcode是setResult里面设置的code值
            val filePath = getFilePath(null, requestCode, data)
            if (!StringUtils.isEmpty(filePath)) {
                mImageAdapter.add(filePath)
                mImageAdapter.notifyDataSetChanged()
            }
        }

    }

    inner class SendCircleCall : DataCall<Any> {
        override fun success(data: Any?, args: List<Any>) {
            CircleFragment.addCircle = true
            finish()
        }

        override fun fail(e: ApiException, args: List<Any>) {
            UIUtils.showToastSafe(e.code + "  " + e.displayMessage)
        }
    }
}
