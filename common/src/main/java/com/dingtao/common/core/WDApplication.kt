package com.dingtao.common.core

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.os.Handler
import android.os.Looper

import com.alibaba.android.arouter.launcher.ARouter
import com.facebook.common.internal.Supplier
import com.facebook.common.util.ByteConstants
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.cache.MemoryCacheParams
import com.facebook.imagepipeline.core.ImagePipelineConfig


/**
 * @name: MyApplication
 * @remark:
 */
class WDApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        context = this
        mainThread = Thread.currentThread()
        mainThreadHandler = Handler()
        mainThreadLooper = mainLooper
        share = getSharedPreferences("share.xml", Context.MODE_PRIVATE)

        ARouter.openLog()     // Print log
        ARouter.openDebug()
        ARouter.init(this@WDApplication)//阿里路由初始化

        Fresco.initialize(this, getConfigureCaches(this))//图片加载框架初始化
        //定位
        //推送
        //统计
    }

    override fun onTerminate() {
        super.onTerminate()
        ARouter.getInstance().destroy()
    }

    private fun getConfigureCaches(context: Context): ImagePipelineConfig {
        val bitmapCacheParams = MemoryCacheParams(
                MAX_MEM, // 内存缓存中总图片的最大大小,以字节为单位。
                Integer.MAX_VALUE, // 内存缓存中图片的最大数量。
                MAX_MEM, // 内存缓存中准备清除但尚未被删除的总图片的最大大小,以字节为单位。
                Integer.MAX_VALUE, // 内存缓存中准备清除的总图片的最大数量。
                Integer.MAX_VALUE)// 内存缓存中单个图片的最大大小。

        val mSupplierMemoryCacheParams = Supplier { bitmapCacheParams }
        val builder = ImagePipelineConfig.newBuilder(context)
        builder.setBitmapMemoryCacheParamsSupplier(mSupplierMemoryCacheParams)
        return builder.build()
    }

    companion object {
        lateinit var context: Context
        /**
         * 获取主线程
         */
        lateinit var mainThread: Thread
        /**
         * 主线程Handler
         */
        /**
         * 获取主线程的handler
         */
        lateinit var mainThreadHandler: Handler
        /**
         * 主线程Looper
         */
        /**
         * 获取主线程的looper
         */
        lateinit var mainThreadLooper: Looper

        private val MAX_MEM = 30 * ByteConstants.MB

        /**
         * context 全局唯一的上下文
         */
        /**
         * @return 全局唯一的上下文
         * @author: 康海涛 QQ2541849981
         * @describe: 获取全局Application的上下文
         */

        lateinit var share: SharedPreferences
    }

}