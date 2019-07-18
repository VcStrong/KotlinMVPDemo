package com.dingtao.rrmmp.presenter

import com.dingtao.common.bean.Result
import com.dingtao.common.core.presenter.WDPresenter
import com.dingtao.common.core.DataCall
import com.dingtao.common.core.http.IAppRequest
import java.io.File

import io.reactivex.Observable
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody

/**
 * @author dingtao
 * @date 2018/12/28 11:23
 * qq:1940870847
 */
class PublishCirclePresenter(dataCall: DataCall<Any>) : WDPresenter<IAppRequest, Any>(dataCall) {

    override fun getModel(args: List<Any>): Observable<Result<Any>> {

        val builder = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
        builder.addFormDataPart("content", args[3] as String)
        builder.addFormDataPart("commodityId", "1")
        val list = args[4] as List<Any>
        if (list.size > 1) {
            for (i in 1 until list.size) {
                val file = File(list[i] as String)
                builder.addFormDataPart("image", file.name,
                        RequestBody.create(MediaType.parse("multipart/octet-stream"),
                                file))
            }
        }
        return iRequest.releaseCircle(args[0] as Long,
                args[1] as String, builder.build())
    }


}
