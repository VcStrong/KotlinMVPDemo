package com.dingtao.rrmmp.activity

import android.Manifest
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast

import com.alibaba.android.arouter.facade.annotation.Route
import com.dingtao.common.bean.UserInfo
import com.dingtao.common.core.DataCall
import com.dingtao.common.core.WDActivity
import com.dingtao.common.core.WDApplication
import com.dingtao.common.core.db.DaoMaster
import com.dingtao.common.core.db.UserInfoDao
import com.dingtao.common.core.exception.ApiException
import com.dingtao.common.util.Constant
import com.dingtao.rrmmp.R
import com.dingtao.rrmmp.presenter.LoginPresenter

import com.dingtao.common.util.MD5Utils
import com.dingtao.common.util.UIUtils
import kotlinx.android.synthetic.main.activity_login.*
import pub.devrel.easypermissions.EasyPermissions

@Route(path = Constant.ACTIVITY_URL_LOGIN)
class LoginActivity : WDActivity() {
    override fun destoryData() {
    }

    internal lateinit var requestPresenter: LoginPresenter

    override val layoutId: Int = R.layout.activity_login

    private var pasVisibile = false

    override fun initView() {
        mLogin.setOnClickListener { login() }
        mRemPas.setOnClickListener { remPas() }
        mPasEye.setOnClickListener { eyePas() }
        mRegister.setOnClickListener { register() }
        requestPresenter = LoginPresenter(LoginCall())
        val remPas = WDApplication.share.getBoolean("remPas", true)
        if (remPas) {
            mRemPas!!.isChecked = true
            mMobile!!.setText(WDApplication.share.getString("mobile", ""))
            mPas!!.setText(WDApplication.share.getString("pas", ""))
        }
        if (!EasyPermissions.hasPermissions(this,
                        *arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE))) {
            EasyPermissions.requestPermissions(this,
                    "发布图片", 10,
                    *arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE))
        }
    }

    fun login() {
        val m = mMobile!!.text.toString()
        val p = mPas!!.text.toString()
        if (TextUtils.isEmpty(m)) {
            Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_LONG).show()
            return
        }
        if (TextUtils.isEmpty(p)) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_LONG).show()
            return
        }
        if (mRemPas!!.isChecked) {
            WDApplication.share.edit().putString("mobile", m)
                    .putString("pas", p).commit()
        }
        mLoadDialog.show()
        requestPresenter.reqeust(m, MD5Utils.md5(p))
    }

    /**
     * 重写onRequestPermissionsResult，用于接受请求结果
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //将请求结果传递EasyPermission库处理
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun cancelLoadDialog() {
        super.cancelLoadDialog()
        requestPresenter.cancelRequest()
    }

    fun remPas() {
        WDApplication.share.edit()
                .putBoolean("remPas", mRemPas!!.isChecked).commit()
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

    fun register() {
        intentByRouter(Constant.ACTIVITY_URL_REGISTER)
    }


    /**
     * @author dingtao
     * @date 2018/12/28 10:44 AM
     * 登录
     */
    internal inner class LoginCall : DataCall<UserInfo> {

        override fun success(data: UserInfo?, args: List<Any>) {
            mLoadDialog.cancel()
            data!!.status = 1//设置登录状态，保存到数据库
            val userInfoDao = DaoMaster.newDevSession(baseContext, UserInfoDao.TABLENAME).userInfoDao
            userInfoDao.insertOrReplace(data)//保存用户数据
            intentByRouter(Constant.ACTIVITY_URL_MAIN)
            finish()
        }

        override fun fail(e: ApiException, args: List<Any>) {
            mLoadDialog.cancel()
            UIUtils.showToastSafe(e.code + " " + e.displayMessage)
        }
    }
}
