package com.dingtao.common.core.exception

import android.net.ParseException

import com.google.gson.JsonParseException

import org.json.JSONException

import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * @author dingtao
 * @date 2019/1/2 7:03 PM
 * 异常处理工具类：你把异常传给我，哪些异常需要页面Toast提示或者操作
 */
object CustomException {

    /**
     * 未知错误
     */
    val UNKNOWN = "1000"

    /**
     * 解析错误
     */
    val PARSE_ERROR = "1001"

    /**
     * 网络错误
     */
    val NETWORK_ERROR = "1002"

    /**
     * 协议错误
     */
    val HTTP_ERROR = "1003"

    /**
     * 处理系统异常，封装成ApiException
     * Throwable包含Error和Exception
     */
    fun handleException(e: Throwable): ApiException {

        e.printStackTrace()//打印异常

        val ex: ApiException
        if (e is JsonParseException
                || e is JSONException
                || e is ParseException) {
            //解析错误
            ex = ApiException(PARSE_ERROR, "解析异常")
            return ex
        } else if (e is ConnectException || e is UnknownHostException || e is SocketTimeoutException) {
            //网络错误
            ex = ApiException(NETWORK_ERROR, e.message)
            return ex
        } else {
            //未知错误
            ex = ApiException(UNKNOWN, e.message)
            return ex
        }
    }
}
