package com.dingtao.common.core

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import android.view.KeyEvent

import com.alibaba.android.arouter.launcher.ARouter
import com.dingtao.common.bean.UserInfo
import com.dingtao.common.core.db.DaoMaster
import com.dingtao.common.core.db.UserInfoDao
import com.dingtao.common.util.LogUtils


/**
 * @author dingtao
 * @date 2018/12/29 14:00
 * qq:1940870847
 */
abstract class WDActivity : AppCompatActivity() {
    lateinit var mLoadDialog: Dialog// 加载框

    lateinit var LOGIN_USER: UserInfo

    /**
     * 设置layoutId
     *
     * @return
     */
    protected abstract val layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //查询登录用户，方便每个页面使用
        val userInfoDao = DaoMaster.newDevSession(this, UserInfoDao.TABLENAME).userInfoDao
        val userInfos = userInfoDao.queryBuilder().where(UserInfoDao.Properties.Status.eq(1)).list()
        if (userInfos != null && userInfos.size > 0) {
            LOGIN_USER = userInfos[0]//读取第一项
        }
        //打印堆栈ID
        LogUtils.e("getTaskId = $taskId")
        initLoad()
        setContentView(layoutId)
        ARouter.getInstance().inject(this)
        initView()
    }

    /**
     * 初始化视图
     */
    protected abstract fun initView()

    /**
     * 清除数据
     */
    protected abstract fun destoryData()

    /**
     * @param mActivity 传送Activity的
     */
    fun intent(mActivity: Class<*>) {
        val intent = Intent(this, mActivity)
        startActivity(intent)
    }

    /**
     * @param path 传送Activity的
     */
    fun intentByRouter(path: String) {
        ARouter.getInstance().build(path)
                .navigation(this)
    }

    /**
     * @param mActivity 传送Activity的
     * @param bundle
     */
    fun intent(mActivity: Class<*>, bundle: Bundle) {
        val intent = Intent(this, mActivity)
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
                .navigation(this)
    }

    /**
     * 初始化加载框
     */
    private fun initLoad() {
        mLoadDialog = ProgressDialog(this)// 加载框
        mLoadDialog.setCanceledOnTouchOutside(false)
        mLoadDialog.setOnKeyListener { dialog, keyCode, event ->
            if (mLoadDialog.isShowing && keyCode == KeyEvent.KEYCODE_BACK) {
                cancelLoadDialog()//加载消失的同时
                mLoadDialog.cancel()
            }
            false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        destoryData()
    }

    //取消操作：请求或者其他
    open fun cancelLoadDialog() {

    }

    override fun onStart() {
        super.onStart()
        foregroundActivity = this
    }

    /**
     * 得到图片的路径
     *
     * @param fileName
     * @param requestCode
     * @param data
     * @return
     */
    fun getFilePath(fileName: String?, requestCode: Int, data: Intent?): String? {
        if (requestCode == CAMERA) {
            return fileName
        } else if (requestCode == PHOTO) {
            val uri = data!!.data
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            val actualimagecursor = managedQuery(uri, proj, null, null, null)
            val actual_image_column_index = actualimagecursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            actualimagecursor.moveToFirst()
            val img_path = actualimagecursor
                    .getString(actual_image_column_index)
            // 4.0以上平台会自动关闭cursor,所以加上版本判断,OK
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH)
                actualimagecursor.close()
            return img_path
        }
        return null
    }

    companion object {

        val PHOTO = 0// 相册选取
        val CAMERA = 1// 拍照

        /**
         * 记录处于前台的Activity
         */
        /**
         * 获取当前处于前台的activity
         */
        lateinit var foregroundActivity: WDActivity
    }
}
