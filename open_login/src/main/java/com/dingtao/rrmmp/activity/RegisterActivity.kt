package com.dingtao.rrmmp.activity

import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.EditText

import com.alibaba.android.arouter.facade.annotation.Route
import com.dingtao.common.core.DataCall
import com.dingtao.common.core.WDActivity
import com.dingtao.common.core.exception.ApiException
import com.dingtao.common.util.Constant
import com.dingtao.rrmmp.R
import com.dingtao.rrmmp.presenter.RegisterPresenter

import com.dingtao.common.util.MD5Utils
import com.dingtao.common.util.UIUtils
import kotlinx.android.synthetic.main.activity_register.*

@Route(path = Constant.ACTIVITY_URL_REGISTER)
class RegisterActivity : WDActivity() {

    internal lateinit var requestPresenter: RegisterPresenter

    override val layoutId: Int = R.layout.activity_register

    private var pasVisibile = false

    override fun destoryData() {
    }

    override fun initView() {
        mRegister.setOnClickListener { register() }
        mPasEye.setOnClickListener{eyePas()}
        mLogin.setOnClickListener { finish() }
        requestPresenter = RegisterPresenter(RegisterCall())
    }

    fun register() {
        val m = mMobile!!.text.toString()
        val p = mPas!!.text.toString()
        if (TextUtils.isEmpty(m)) {
            UIUtils.showToastSafe("请输入正确的手机号")
            return
        }
        if (TextUtils.isEmpty(p)) {
            UIUtils.showToastSafe("请输入密码")
            return
        }
        mLoadDialog.show()
        requestPresenter.reqeust(m, MD5Utils.md5(p))
    }

    fun eyePas() {
        if (pasVisibile) {//密码显示，则隐藏
            mPas!!.transformationMethod = PasswordTransformationMethod.getInstance()
            pasVisibile = false
        } else {//密码隐藏则显示
            mPas!!.transformationMethod = HideReturnsTransformationMethod.getInstance()
            pasVisibile = true
        }
    }

    /**
     * @author dingtao
     * @date 2018/12/28 10:44 AM
     * 注册
     */
    internal inner class RegisterCall : DataCall<Any> {

        override fun success(result: Any?, args: List<Any>) {
            mLoadDialog.cancel()
            UIUtils.showToastSafe("注册成功，请登录")
            finish()
        }

        override fun fail(e: ApiException, args: List<Any>) {
            mLoadDialog.cancel()
            UIUtils.showToastSafe(e.code + " " + e.displayMessage)
        }
    }
}
