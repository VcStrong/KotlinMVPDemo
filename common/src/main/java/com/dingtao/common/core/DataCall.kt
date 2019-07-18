package com.dingtao.common.core

import com.dingtao.common.core.exception.ApiException

/**
 * @author dingtao
 * @date 2018/12/30 10:30
 * qq:1940870847
 */
interface DataCall<T> {

    fun success(data: T?, args: List<Any>)
    fun fail(e: ApiException, args: List<Any>)

}
